package com.techie.microservices.order.config;



import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI orderServiceApi() {
        return new OpenAPI()
                .info(new Info().title("Order service API")
                        .description("This is the Rest API for order-service")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("You can refer to the Order Service Wiki Documentation")
                        .url("http://order-service-dummy-url/docs"));
    }
}
