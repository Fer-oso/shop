package com.ecommerce.shop.services.mercadopago;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecommerce.shop.models.mappers.mercadopago.MPResponseMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePayerRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferenceItem;

@Service
public class MercadoPagoService {

        private final WebClient webClient;

        MPResponseMapper mpResponseMapper;

        public MercadoPagoService(WebClient webClient, MPResponseMapper mpResponseMapper) {
                this.webClient = webClient;
                this.mpResponseMapper = mpResponseMapper;
        }

        @Value("${mercadopago.accesstoken}")
        private String accesToken;

        public String createPreference(List<PreferenceItem> preferenceItems) {

                MercadoPagoConfig.setAccessToken(accesToken);

                try {

                        List<PreferenceItemRequest> items = preferenceItems.stream().map(preferenceItem -> {

                                PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()

                                                .title(preferenceItem.getTitle())
                                                .quantity(preferenceItem.getQuantity())
                                                .unitPrice(preferenceItem.getUnitPrice())
                                                .description(preferenceItem.getDescription())
                                                .pictureUrl(preferenceItem.getPictureUrl())
                                                .currencyId("ARS")
                                                .build();

                                return itemRequest;

                        }).collect(Collectors.toList());

                        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                                        .success(
                                                        "https://insert-nice-raise-occupations.trycloudflare.com/shopping-cart/payment-status?status=success")
                                        .pending(
                                                        "https://insert-nice-raise-occupations.trycloudflare.com/shopping-cart/payment-status?status=success")
                                        .failure(
                                                        "https://insert-nice-raise-occupations.trycloudflare.com/shopping-cart/payment-status?status=success")
                                        .build();

                        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                                        .name("Fernando Adrian")
                                        .surname("Osorio")
                                        .email("osorio.f@outlook.es")
                                        .build();

                        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                                        .items(items)
                                        .payer(payer)
                                        .backUrls(backUrls)
                                        // .notificationUrl("https://cicada-open-partly.ngrok-free.app/api/shop/mercadopago/payments/notifications")
                                        .autoReturn("approved")
                                        .purpose("wallet_purchase")
                                        .build();

                        PreferenceClient client = new PreferenceClient();

                        Preference preference = client.create(preferenceRequest);

                        return preference.getId();

                } catch (MPException | MPApiException e) {
                        return (e.getMessage());
                }
        }

        public JsonNode paymentStatus(JsonNode paymentNotification) throws Exception {

                try {

                        MercadoPagoConfig.setAccessToken(accesToken);

                        if (paymentNotification == null) {
                                throw new IllegalArgumentException("PaymentNotification is null");
                        }

                        if (paymentNotification.get("id") == null) {
                                throw new IllegalArgumentException("El ID del pago no es válido: " +
                                                paymentNotification.get("id"));
                        }

                        Long paymentId = Long.parseLong(paymentNotification.get("data").get("id").asText());

                        MercadoPagoConfig.setAccessToken(accesToken);

                        PaymentClient client = new PaymentClient();

                        Payment response = client.get(paymentId, MPRequestOptions.createDefault());

                        ObjectMapper objectMapper = new ObjectMapper();

                        JsonNode jsonNode = objectMapper.readTree(response.getResponse().getContent());

                        String formatedString = objectMapper.writerWithDefaultPrettyPrinter()
                                        .writeValueAsString(jsonNode);

                        System.out.println(formatedString);
                        return jsonNode;

                } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                }

        }
}