package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.OrderDetailDTO;
import com.project.shopapp.dtos.responses.OrderDetailResponse;
import com.project.shopapp.exceptions.DataNotFoundException;

import java.util.List;

public interface OrderDetailService {
    OrderDetailResponse createOrderDetail(OrderDetailDTO newOrderDetail) throws DataNotFoundException;
    OrderDetailResponse getOrderDetail(Long id) throws DataNotFoundException;
    OrderDetailResponse updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException;
    void deleteById(Long id);
    List<OrderDetailResponse> findByOrderId(Long orderId);
}
