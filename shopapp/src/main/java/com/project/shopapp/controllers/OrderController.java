package com.project.shopapp.controllers;

import com.project.shopapp.dtos.requests.OrderRequest;
import com.project.shopapp.dtos.responses.ApiResponse;
import com.project.shopapp.dtos.responses.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/orders")
public interface OrderController {
    @PostMapping("")
    ApiResponse<OrderResponse> createOrder(
            @Valid @RequestBody OrderRequest orderRequest
    );

    @GetMapping("/{id}")
    ApiResponse<OrderResponse> getOrder(@Valid @PathVariable("id") Long id);

    @GetMapping("/user/{user_id}")
    ApiResponse<List<OrderResponse>> getOrdersByUserId(@Valid @PathVariable("user_id") Long userId);

    @PutMapping("/{id}")
    ApiResponse<OrderResponse> updateOrder(
            @Valid @PathVariable long id,
            @Valid @RequestBody OrderRequest orderRequest
    );

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteOrder(@Valid @PathVariable Long id);
}
