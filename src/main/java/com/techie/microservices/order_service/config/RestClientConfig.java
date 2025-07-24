package com.techie.microservices.order_service.config;


import com.techie.microservices.order_service.client.InventoryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.boot.http.client.HttpComponentsClientHttpRequestFactoryBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.time.Duration;


//Description: Uses Spring’s declarative interface style (via @GetExchange)
//.But registers it manually via HttpServiceProxyFactory


@Configuration
public class RestClientConfig {

    //Reading inventory service base url
    @Value("${inventory.url}")
    private String inventoryServiceUrl;

    @Bean
    public InventoryClient inventoryClient() {


        //building a rest client
        RestClient restClient = RestClient.builder()
                .requestFactory(requestFactory())
                .baseUrl(inventoryServiceUrl)
                .build();

        // Converts the RestClient into an adapter that Spring understands.
        var restClientAdapter = RestClientAdapter.create(restClient);

        // Creates a factory that will generate real REST client implementations using our adapter.
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();

        // Generates and returns a working instance of InventoryClient using the factory.
        return httpServiceProxyFactory.createClient(InventoryClient.class);
    }


    private ClientHttpRequestFactory requestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(3));
        factory.setReadTimeout(Duration.ofSeconds(3));
        return factory;
    }

}
