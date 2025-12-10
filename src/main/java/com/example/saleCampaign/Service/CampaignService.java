package com.example.saleCampaign.Service;

import com.example.saleCampaign.Model.Campaign;
import com.example.saleCampaign.Model.CampaignDiscount;
import com.example.saleCampaign.Model.PriceHistory;
import com.example.saleCampaign.Model.Product;
import com.example.saleCampaign.Repository.CampaignRepo;
import com.example.saleCampaign.Repository.PriceHistoryRepo;
import com.example.saleCampaign.Repository.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CampaignService {
    @Autowired
    CampaignRepo campaignRepo;

    @Autowired
    ProductRepo productRepo;
    @Autowired
    PriceHistoryRepo priceHistoryRepo;

    public ResponseEntity<?> addCampaign(Campaign campaign)
    {
        try
        {
            for (CampaignDiscount item : campaign.getCompaignDiscount())
            {
                long product_id=item.getProduct_id();
                Optional<Product> product=productRepo.findById(product_id);
                if (product.isEmpty())
                {
                    throw new RuntimeException("Product Not Found.. for this Product id "+product_id);
                }
            }
            Campaign campaign1=campaignRepo.save(campaign);
            return new ResponseEntity<>(campaign1, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @Transactional
    @Scheduled(cron = "0 02 11 * * *")
    public ResponseEntity<?> startCampaign() {
        try {
            LocalDate startDate=LocalDate.now();
            List<Campaign> campaigns = campaignRepo.findByStartDate(startDate);
            for (Campaign c : campaigns) {
                List<CampaignDiscount> campaignDiscounts = c.getCompaignDiscount();
                for (CampaignDiscount campaignDiscount : campaignDiscounts) {
                    long product_id = campaignDiscount.getProduct_id();
                    Product product = productRepo.findById(product_id).orElse(null);
                    if (product == null) {
                        return new ResponseEntity<>("Product not found..", HttpStatus.NOT_FOUND);
                    }
                    PriceHistory priceHistory=new PriceHistory();
                    priceHistory.setDiscount(campaignDiscount.getDiscount());
                    priceHistory.setPrice(product.getCurrect_price());
                    priceHistory.setMrp(product.getMrp());
                    priceHistory.setProduct_id(product_id);
                    priceHistory.setStartDate(c.getStartDate());
                    priceHistory.setEndDate(c.getEndDate());
                    priceHistoryRepo.save(priceHistory);

                    double oldDiscount=product.getDiscount();
                    double newDiscount=oldDiscount+campaignDiscount.getDiscount();


                    double mrp=product.getMrp();
                    double finalPrice=mrp-(mrp*newDiscount/100);
                    product.setDiscount(newDiscount);
                    product.setCurrect_price(finalPrice);
                    productRepo.save(product);
                    System.out.println("product saved is "+product.getCurrect_price());

                }
            }
            return new ResponseEntity<>("Done..Check Your Offer..",HttpStatus.ACCEPTED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    @Transactional
    @Scheduled(cron = "0 05 11 * * *")

    public ResponseEntity<?> endCampaign() {

        LocalDate endDate=LocalDate.now();
//        find records of endDate in price_history
        List<PriceHistory> priceHistories=priceHistoryRepo.findByEndDate(endDate);

        for (PriceHistory ph:priceHistories)
        {
            long product_id=ph.getProduct_id();
            double discount=ph.getDiscount();

            Product product=productRepo.findById(product_id).orElse(null);
            if (product==null)
            {
                throw new RuntimeException("Product not FOund for this id "+product_id);
            }
            double total_discount=product.getDiscount();
            double new_discount=total_discount-discount;
            product.setDiscount(new_discount);
            double mrp=product.getMrp();
            double finalPrice=mrp-(mrp*new_discount/100);
            product.setCurrect_price(finalPrice);
            productRepo.save(product);

        }
        //            once it saves delete from price_history

        priceHistoryRepo.deleteByEndDate(endDate);
        campaignRepo.deleteCampaignDiscountByEndDate(endDate);

//            delete from campaign tbl
        campaignRepo.deleteByEndDate(endDate);

        return new ResponseEntity<>("Okk Undo all Offers ",HttpStatus.ACCEPTED);
    }


}
