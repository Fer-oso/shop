package com.ecommerce.shop.controllers.buyer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shop.models.buyer.Buyer;
import com.ecommerce.shop.services.buyer.IBuyerService;

@RestController
@RequestMapping("${api.prefix}/buyer")
public class BuyerController {
    
    IBuyerService buyerService;

    public BuyerController(IBuyerService buyerService) {
        this.buyerService = buyerService;
    }


   @PostMapping
   public ResponseEntity<?> save(@RequestBody Buyer buyer){
    return ResponseEntity.status(HttpStatus.CREATED).body(buyerService.save(buyer));
   }

   @GetMapping("/{id}")
   public ResponseEntity<?> findById(@PathVariable Long id){
    return ResponseEntity.status(HttpStatus.OK).body(buyerService.findById(id));
   }
}
