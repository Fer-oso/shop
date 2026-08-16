package com.ecommerce.shop.repository.buyers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.shop.models.entitys.buyer.Buyer;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {

    List<Buyer> findAllByUser_id(Long userId);
}
