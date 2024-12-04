package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.OrderDTO;
import com.project.shopapp.dtos.responses.OrderResponse;
import com.project.shopapp.exceptions.DataNotFoundException;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderDTO orderDTO) throws DataNotFoundException;
    OrderResponse getOrder(Long id) throws DataNotFoundException;
    OrderResponse updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;
    void deleteOrder(Long id) ;
    List<OrderResponse> findByUserId(Long userId);
}
