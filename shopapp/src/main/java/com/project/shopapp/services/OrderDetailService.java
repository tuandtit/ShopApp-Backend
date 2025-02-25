package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.OrderDetailRequest;
import com.project.shopapp.dtos.responses.OrderDetailResponse;

import java.util.List;

public interface OrderDetailService {
    OrderDetailResponse createOrderDetail(OrderDetailRequest newOrderDetail);
    OrderDetailResponse getOrderDetail(Long id);
    OrderDetailResponse updateOrderDetail(Long id, OrderDetailRequest orderDetailRequest);
    void deleteById(Long id);
    List<OrderDetailResponse> findByOrderId(Long orderId);
}
