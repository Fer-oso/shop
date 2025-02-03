package com.ecommerce.shop.controllers.mercadopago;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shop.models.DTO.product.ProductShoppingCartDTO;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("${api.prefix}/mercadopago")
public class MercadoPagoController {
    
    final String ACCESS_TOKEN = "APP_USR-6466186508757279-020201-134d4870190481c29f95c880c78eb6c2-2243613633";

    @PostMapping(value = "/create-preference")
    public ResponseEntity<?> postMethodName(@RequestBody ProductShoppingCartDTO productShoppingCartDTO) {
        
        MercadoPagoConfig.setAccessToken(ACCESS_TOKEN);

        try {
        
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
            
            .title(productShoppingCartDTO.getProduct().getName())
            .quantity(productShoppingCartDTO.getQuantity())
            .unitPrice(BigDecimal.valueOf(productShoppingCartDTO.getProduct().getPrice()))
            .currencyId("ARS")
            .build();

            List<PreferenceItemRequest> items = new ArrayList<>();

            items.add(itemRequest);


            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
            .success("https://youtube.com")
            .pending("https://youtube.com")
            .failure("https://youtube.com")
            .build();


            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
            .items(items)
            .backUrls(backUrls)
            .autoReturn("approved")
            .purpose("wallet_purchase")
            .build();

            PreferenceClient client = new PreferenceClient();

            Preference preference = client.create(preferenceRequest);

            return ResponseEntity.status(HttpStatus.OK).body(preference.getId());

        } catch (MPException| MPApiException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
