package com.ecommerce.shop.controllers.mercadopago;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shop.controllers.responsesModels.ResponseSuccessModel;
import com.ecommerce.shop.models.DTO.mercadopago.PaymentOrderDTO;
import com.ecommerce.shop.services.mercadopago.MercadoPagoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDateTime;

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

    @PostMapping("/webhooks/notifications")
    public ResponseEntity<?> processWebHook(@RequestBody String notification) throws Exception {

        ObjectNode notificationPaymentJson = new ObjectMapper().readValue(notification, ObjectNode.class);

        mercadoPagoService.processWebHook(notificationPaymentJson);

        return ResponseEntity.ok().body(ResponseSuccessModel.builder()
                .status("OK")
                .code("200")
                .response("Webhook procesado exitosamente")
                .timestamp(LocalDateTime.now())
                .build());
    }

}
