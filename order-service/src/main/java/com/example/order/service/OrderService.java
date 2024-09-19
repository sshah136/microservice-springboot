package com.example.order.service;

import com.example.order.client.InventoryClient;
import com.example.order.dto.OrderRequest;
import com.example.order.dto.OrderResponse;
import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Data
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public OrderResponse placeOrder(OrderRequest orderRequest){

        var inStock = inventoryClient.isInStock(orderRequest.sku(), orderRequest.quantity());

        if (inStock)
        {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSku(orderRequest.sku());
            order.setQuantity(orderRequest.quantity());
            orderRepository.save(order);
            log.info("Order Created");
            return new OrderResponse(order.getId(), order.getOrderNumber(),
                    order.getSku(), order.getPrice(), order.getQuantity());
        } else {
            throw new RuntimeException(orderRequest.sku()+"not in stock");
        }

    }
}
