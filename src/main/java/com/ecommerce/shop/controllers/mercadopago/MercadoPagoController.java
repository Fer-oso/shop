package com.ecommerce.shop.controllers.mercadopago;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shop.controllers.responsesModels.ResponseSuccessModel;
import com.ecommerce.shop.models.DTO.mercadopago.PaymentOrderDTO;
import com.ecommerce.shop.services.mercadopago.MercadoPagoService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.PreferenceItem;

import java.time.LocalDateTime;
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
    public ResponseEntity<?> createPreference(@RequestBody PaymentOrderDTO paymentOrder) {

        return ResponseEntity.status(HttpStatus.OK).body(mercadoPagoService.createPreference(paymentOrder));
    }

    @PostMapping("/payments/notifications")
    public ResponseEntity<?> paymentStatus(@RequestBody String payment) throws Exception {

        ObjectNode paymentJson = new ObjectMapper().readValue(payment, ObjectNode.class);

        return ResponseEntity.ok().body(ResponseSuccessModel.builder()
                .status("OK")
                .code("200")
                .response(mercadoPagoService.paymentStatus(paymentJson))
                .timestamp(LocalDateTime.now())
                .build());
    }

}
