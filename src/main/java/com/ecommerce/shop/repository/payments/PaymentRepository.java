package com.ecommerce.shop.repository.payments;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.shop.models.entitys.mercadopago.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

}
