package com.ecommerce.shop.services.mercadopago;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecommerce.shop.models.DTO.mercadopago.PaymentOrderDTO;
import com.ecommerce.shop.models.DTO.shoppingcart.OrderDTO;
import com.ecommerce.shop.models.entitys.mercadopago.PaymentEntity;
import com.ecommerce.shop.models.entitys.orders.Order;
import com.ecommerce.shop.models.mappers.OrderMapper;
import com.ecommerce.shop.models.mappers.mercadopago.MPResponseMapper;
import com.ecommerce.shop.services.order.IOrderService;
import com.ecommerce.shop.services.utils.mercadopago.MercadoPagoUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePayerRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;

@Service
public class MercadoPagoService {

        IOrderService orderService;
        OrderMapper orderMapper;

        String succesUrl = "https://restaurant-interest-acdbentity-converted.trycloudflare.com/shopping-cart/payment-status?status=success";
        String pendingUrl = "https://restaurant-interest-acdbentity-converted.trycloudflare.com/shopping-cart/payment-status?status=pending";
        String failureUrl = "https://restaurant-interest-acdbentity-converted.trycloudflare.com/shopping-cart/payment-status?status=failure";
        String notificationUrl = "https://945f-2800-810-748-86f9-d4b8-1d9d-d532-12b7.ngrok-free.app/api/shop/mercadopago/webhooks/notifications";

        public MercadoPagoService(IOrderService orderService, OrderMapper orderMapper) {

                this.orderService = orderService;
                this.orderMapper = orderMapper;
        }

        @Value("${mercadopago.accesstoken}")
        private String accesToken;

        private Long paymentId;

        private OrderDTO orderDTO;

        public Long getPaymentId() {
                return paymentId;
        }

        public String createPreference(PaymentOrderDTO paymentOrder) {

                System.out.println("esto es paymentOrder para verificar datos: " + paymentOrder);

                MercadoPagoConfig.setAccessToken(accesToken);
                try {

                        List<PreferenceItemRequest> items = MercadoPagoUtils.extractItemsFromPaymentOrder(paymentOrder);

                        PreferenceBackUrlsRequest backUrls = MercadoPagoUtils.createBackUrls(succesUrl, pendingUrl,
                                        failureUrl);

                        PreferencePayerRequest payer = MercadoPagoUtils.createPayer(paymentOrder.getBuyer());

                        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                                        .items(items)
                                        .payer(payer)
                                        .externalReference(paymentOrder.getShoppingCartId())
                                        .backUrls(backUrls)
                                        .notificationUrl(notificationUrl)
                                        .purpose("wallet_purchase")
                                        .autoReturn("approved")
                                        .build();

                        PreferenceClient client = new PreferenceClient();

                        Preference preference = client.create(preferenceRequest);

                        System.out.println("Preference creada con ID: " + preference.getId() + " y link: "
                                        + preference.getInitPoint());

                        String preferenceId = preference.getId();

                        orderDTO = orderService.save(paymentOrder);

                        System.out.println("OrderDTO guardada: " + orderDTO);

                        return preferenceId;

                } catch (MPException | MPApiException e) {
                        return (e.getMessage());
                }
        }

        public void processWebHook(JsonNode notificationPayment) throws Exception {

                System.out.println("Webhook recibido: " + notificationPayment.toPrettyString());

                String resource = notificationPayment.path("resource").asText(null);
                String topic = Optional.ofNullable(notificationPayment.path("topic").asText(null))
                                .filter(t -> !t.isBlank()).orElse("unknown");

                paymentId = MercadoPagoUtils.extractPaymentId(resource, topic);

                if (paymentId == null)
                        return;

                try {
                        MercadoPagoConfig.setAccessToken(accesToken);

                        System.out.println("Obteniendo detalles del pago con ID: " + paymentId);

                        PaymentClient client = new PaymentClient();

                        Payment response = client.get(paymentId);

                        Order order = orderMapper
                                        .mapDTOToEntity(orderService.findByOrderNumber(orderDTO.getOrderNumber()));

                        PaymentEntity paymentEntity = PaymentEntity.builder()
                                        .paymentId(response.getId())
                                        .order(order)
                                        .status(response.getStatus())
                                        .externalReference(response.getExternalReference())
                                        .merchantOrderId(String.valueOf(response.getOrder().getId()))
                                        .build();

                        System.out.println("esta es la respuesta: "
                                        + paymentEntity);

                } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                }
        }

}