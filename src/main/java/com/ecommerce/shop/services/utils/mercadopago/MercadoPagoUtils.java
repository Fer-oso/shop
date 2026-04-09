package com.ecommerce.shop.services.utils.mercadopago;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import com.fasterxml.jackson.databind.JsonNode;

public class MercadoPagoUtils {

    public static Long extractPaymentId(JsonNode paymentNotification) {

        System.out.println("Webhook recibido: " + paymentNotification.toPrettyString());

        String resource = paymentNotification.path("resource").asText(null);
        String topic = Optional.ofNullable(paymentNotification.path("topic").asText(null))
                .filter(t -> !t.isBlank())
                .orElse("unknown");

        Map<String, Supplier<Long>> topicHandlers = Map.of(
                "payment", () -> Long.parseLong(resource),
                "merchant_order", () -> {
                    System.out.println("Es una merchant_order, no un payment directo");
                    return null;
                });

        Supplier<Long> handler = topicHandlers.get(topic);

        if (handler == null) {
            System.out.println("Topic no soportado o ausente: " + topic);
            return null; // Sin lanzar error
        }

        Long paymentId = handler.get();

        return paymentId;

    }

}
