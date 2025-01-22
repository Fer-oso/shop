package com.ecommerce.shop.repository.buyers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.shop.models.buyer.Buyer;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer,Long>{

}
