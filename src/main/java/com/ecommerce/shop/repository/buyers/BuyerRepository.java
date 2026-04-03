package com.ecommerce.shop.repository.buyers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.shop.models.entitys.buyer.Buyer;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {

    List<Buyer> findAllByUser_id(Long userId);
}
