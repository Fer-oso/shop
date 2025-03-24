package com.ecommerce.shop.services.buyer;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.shop.models.DTO.buyer.BuyerDTO;
import com.ecommerce.shop.models.entitys.buyer.Buyer;
import com.ecommerce.shop.models.entitys.user.User;
import com.ecommerce.shop.models.mappers.UserMapper;
import com.ecommerce.shop.models.mappers.buyer.BuyerMapper;
import com.ecommerce.shop.repository.buyers.BuyerRepository;
import com.ecommerce.shop.services.users.IUserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class BuyerServiceImp implements IBuyerService {

    BuyerRepository buyerRepository;
    BuyerMapper buyerMapper;

    IUserService userService;
    UserMapper userMapper;;

    public BuyerServiceImp(BuyerRepository buyerRepository, BuyerMapper buyerMapper,
            IUserService userService, UserMapper userMapper) {
        this.buyerRepository = buyerRepository;
        this.buyerMapper = buyerMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public BuyerDTO save(BuyerDTO buyerDTO) {

        return Optional.of(buyerDTO).map(dto -> {

            User user = userMapper.mapDTOToEntity(userService.findById(dto.getUser().getId()));

            Buyer buyer = buyerMapper.mapDTOToEntity(dto);

            buyer.setUser(user);

            return buyerMapper.mapEntityToDTO(buyerRepository.save(buyer));

        }).orElseThrow(() -> new EntityNotFoundException("Buyer not found"));
    };

    @Override
    public BuyerDTO findById(Long id) {
        return buyerRepository.findById(id).map(buyer -> buyerMapper.mapEntityToDTO(buyer))
                .orElseThrow(() -> new EntityNotFoundException("Buyer not found"));
    }

    @Override
    public BuyerDTO update(BuyerDTO t, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public String deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public List<BuyerDTO> findAll() {

        List<Buyer> buyersList = buyerRepository.findAll();

        if (buyersList.isEmpty()) {
            throw new EntityNotFoundException("Buyers not found");
        }

        return buyersList.stream().map(buyerMapper::mapEntityToDTO).toList();
    }

    @Override
    public Buyer findBuyerById(Long id) {
        return buyerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Buyer not found"));
    }
}
