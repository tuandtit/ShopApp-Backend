package com.project.shopapp.controllers;

import com.project.shopapp.dtos.requests.OrderDetailRequest;
import com.project.shopapp.dtos.responses.ApiResponse;
import com.project.shopapp.dtos.responses.OrderDetailResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/order_details")
public interface OrderDetailController {
    @PostMapping("")
    ApiResponse<OrderDetailResponse> createOrderDetail(
            @Valid @RequestBody OrderDetailRequest newOrderDetail
    );

    @GetMapping("/{id}")
    ApiResponse<OrderDetailResponse> getOrderDetail(
            @Valid @PathVariable("id") long id
    );

    @GetMapping("/order/{orderId}")
    ApiResponse<List<OrderDetailResponse>> findByOrderId(
            @Valid @PathVariable("orderId") Long orderId
    );

    @PutMapping("/{id}")
    ApiResponse<OrderDetailResponse> updateOrderDetail(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailRequest orderDetailRequest
    );

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteOrderDetail(
            @Valid @PathVariable("id") long id
    );
}
