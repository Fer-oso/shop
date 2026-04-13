package com.ecommerce.shop.services.utils.mercadopago;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.ecommerce.shop.models.DTO.mercadopago.BuyerOrderDTO;
import com.ecommerce.shop.models.DTO.mercadopago.PaymentOrderDTO;

import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePayerRequest;

public final class MercadoPagoUtils {

    private MercadoPagoUtils() {
    }

    Long paymentId;

    public static Long extractPaymentId(String resource, String topic) {

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

    public static List<PreferenceItemRequest> extractItemsFromPaymentOrder(PaymentOrderDTO paymentOrderDTO) {

        return paymentOrderDTO.getProducts().stream().map(preferenceItem -> {

            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()

                    .title(preferenceItem.getTitle())
                    .quantity(preferenceItem.getQuantity())
                    .unitPrice(preferenceItem.getUnitPrice())
                    .description(preferenceItem.getDescription())
                    .categoryId(preferenceItem.getCategoryId())
                    .pictureUrl(preferenceItem.getPictureUrl())
                    .currencyId("ARS")
                    .build();

            return itemRequest;

        }).collect(Collectors.toList());

    }

    public static PreferenceBackUrlsRequest createBackUrls(String success, String pending, String failure) {
        return PreferenceBackUrlsRequest.builder()
                .success(success)
                .pending(pending)
                .failure(failure)
                .build();
    }

    public static PreferencePayerRequest createPayer(BuyerOrderDTO buyerOrderDTO) {
        return PreferencePayerRequest.builder()
                .name(buyerOrderDTO.getFirstname())
                .surname(buyerOrderDTO.getLastname())
                .email(buyerOrderDTO.getEmail())
                .phone(PhoneRequest.builder().areaCode(buyerOrderDTO.getPhone().getAreaCode())
                        .number(buyerOrderDTO.getPhone().getNumber()).build())
                .build();
    }

}
