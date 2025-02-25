package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.OrderRequest;
import com.project.shopapp.dtos.responses.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse getOrder(Long id);
    OrderResponse updateOrder(Long id, OrderRequest orderRequest);
    void deleteOrder(Long id) ;
    List<OrderResponse> findByUserId(Long userId);
}
