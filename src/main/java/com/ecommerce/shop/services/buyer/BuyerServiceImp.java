package com.ecommerce.shop.services.buyer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.shop.models.buyer.Buyer;
import com.ecommerce.shop.repository.buyers.BuyerRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class BuyerServiceImp implements IBuyerService{

    BuyerRepository buyerRepository;
    
    public BuyerServiceImp(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    @Override
    public Buyer save(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    @Override
    public Buyer findById(Long id) {
        return buyerRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Buyer not found"));
    }

    @Override
    public Buyer update(Buyer buyer, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public String deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public List<Buyer> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}
