package com.example.saleCampaign.Controller;

import com.example.saleCampaign.Model.Product;
import com.example.saleCampaign.Repository.ProductRepo;
import com.example.saleCampaign.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepo productRepo;
    @PostMapping("/saveAll")
    public ResponseEntity<?> saveAll(@RequestBody List<Product> products)
    {
        return productService.saveAll(products);
    }

    @GetMapping()
    public Page<Product> getProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size)
    {
        Pageable pageable= PageRequest.of(page,size);
        return productRepo.findAll(pageable);
    }
}
