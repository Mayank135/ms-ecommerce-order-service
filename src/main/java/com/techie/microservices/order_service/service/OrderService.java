package com.techie.microservices.order_service.service;


import com.techie.microservices.order_service.dto.OrderRequest;

import com.techie.microservices.order_service.client.InventoryClient;
import com.techie.microservices.order_service.model.Order;
import com.techie.microservices.order_service.repository.OrderRepository;
import com.techie.microservices.order_service.utils.Constants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    @CircuitBreaker(name = "inventory",fallbackMethod = "inventoryServiceFallback")
    @Retry(name = "inventory", fallbackMethod = "inventoryServiceFallback")
    public String placeOrder(OrderRequest orderRequest) {
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if (isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            orderRepository.save(order);
            log.info(Constants.ORDER_SUCCESSFUL_MESSAGE);
            return Constants.ORDER_SUCCESSFUL_MESSAGE;
        }
        else throw new RuntimeException("Product with SkuCode: " + orderRequest.skuCode() + " is not in stock.");
    }

    public String inventoryServiceFallback(OrderRequest orderRequest, Throwable throwable){
        log.warn("Inventory service not responding with skuCode:" +
                " {}. Error : {}",orderRequest.skuCode(), throwable.getMessage());
        return Constants.INVENTORY_SERVICE_FAILING_MESSAGE;
    }


}
