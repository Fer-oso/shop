package com.ecommerce.shop.services.mercadopago;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecommerce.shop.models.DTO.mercadopago.PaymentOrderDTO;
import com.ecommerce.shop.models.mappers.mercadopago.MPResponseMapper;
import com.ecommerce.shop.services.utils.mercadopago.MercadoPagoUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePayerRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPRequest;
import com.mercadopago.resources.common.Phone;
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

        public String createPreference(PaymentOrderDTO paymentOrder) {

                System.out.println("esto es paymentOrder para verificar datos: " + paymentOrder);

                MercadoPagoConfig.setAccessToken(accesToken);
                try {

                        List<PreferenceItemRequest> items = paymentOrder.getProducts().stream().map(preferenceItem -> {

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

                        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                                        .success("https://restaurant-interest-acdbentity-converted.trycloudflare.com/shopping-cart/payment-status?status=success")
                                        .pending("https://restaurant-interest-acdbentity-converted.trycloudflare.com/shopping-cart/payment-status?status=pending")
                                        .failure("https://restaurant-interest-acdbentity-converted.trycloudflare.com/shopping-cart/payment-status?status=failure")
                                        .build();

                        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                                        .name(paymentOrder.getBuyer().getFirstname())
                                        .surname(paymentOrder.getBuyer().getLastname())
                                        .email(paymentOrder.getBuyer().getEmail())
                                        .phone(paymentOrder.getBuyer().getPhone())
                                        .build();

                        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                                        .externalReference(accesToken)
                                        .items(items)
                                        .payer(payer)
                                        .externalReference(paymentOrder.getShoppingCartId())
                                        .backUrls(backUrls)
                                        .notificationUrl(
                                                        "https://945f-2800-810-748-86f9-d4b8-1d9d-d532-12b7.ngrok-free.app/api/shop/mercadopago/payments/notifications")
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

        public Long paymentStatus(ObjectNode paymentNotification) throws Exception {

                Long paymentId = MercadoPagoUtils.extractPaymentId(paymentNotification);

                if (paymentId == null)
                        return null; // merchant_order u otro caso sin ID

                try {

                        System.out.println("Obteniendo detalles del pago con ID: " + paymentId);
                        MercadoPagoConfig.setAccessToken(accesToken);

                        PaymentClient client = new PaymentClient();
                        Payment response = client.get(paymentId, MPRequestOptions.createDefault());

                        // System.out.println("esta es la response: " +
                        // response.getResponse().getContent());

                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(response.getResponse().getContent());

                        System.out.println("esta es la respuesta: "
                                        + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode));

                        return paymentId;

                } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                }
        }

}