package com.ecommerce.shop.services.buyer;

import com.ecommerce.shop.models.DTO.buyer.BuyerDTO;
import com.ecommerce.shop.models.entitys.buyer.Buyer;
import com.ecommerce.shop.services.interfaces.ICrudService;

public interface IBuyerService extends ICrudService<BuyerDTO,Long>{
    
    Buyer findBuyerById(Long id);
    
}
