package com.ecommerce.shop.services.mercadopago;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePayerRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferenceItem;

@Service
public class MercadoPagoService {

    @Value("${mercadopago.accesstoken}")
    private String accesToken;

    public String createPreference(List<PreferenceItem> preferenceItems) {

        MercadoPagoConfig.setAccessToken(accesToken);

        try {

            /*
             * PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
             * 
             * .title(preferenceItem.getTitle())
             * .quantity(preferenceItem.getQuantity())
             * .unitPrice(preferenceItem.getUnitPrice())
             * .description(preferenceItem.getDescription())
             * .pictureUrl(preferenceItem.getPictureUrl())
             * .currencyId("ARS")
             * .build();
             */

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
                    .success("https://youtube.com")
                    .pending("https://youtube.com")
                    .failure("https://youtube.com")
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
}