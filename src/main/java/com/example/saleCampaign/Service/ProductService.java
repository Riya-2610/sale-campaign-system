package com.example.saleCampaign.Service;

import com.example.saleCampaign.Model.Product;
import com.example.saleCampaign.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;

    public ResponseEntity<?> saveAll(List<Product> products) {

        try {
            for (Product p : products) {
                double mrp = p.getMrp();
                double discount = p.getDiscount();

                double currentPrice = mrp - (mrp * discount / 100);

                p.setCurrect_price(currentPrice);
            }

            productRepo.saveAll(products);
            return new ResponseEntity<>("Products Inserted Successfully", HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}
