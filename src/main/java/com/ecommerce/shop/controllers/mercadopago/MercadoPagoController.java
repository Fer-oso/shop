package com.ecommerce.shop.controllers.mercadopago;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shop.services.mercadopago.MercadoPagoService;
import com.mercadopago.resources.preference.PreferenceItem;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("${api.prefix}/mercadopago")
public class MercadoPagoController {

    private MercadoPagoService mercadoPagoService;

    public MercadoPagoController(MercadoPagoService mercadoPagoService) {
        this.mercadoPagoService = mercadoPagoService;
    }

    @PostMapping(value = "/create-preference")
    public ResponseEntity<?> postMethodName(@RequestBody List<PreferenceItem> preferenceItem) {

        return ResponseEntity.status(HttpStatus.OK).body(mercadoPagoService.createPreference(preferenceItem));
    }

}
