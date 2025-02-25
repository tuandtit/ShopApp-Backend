package com.project.shopapp.controllers;

import com.project.shopapp.dtos.requests.OrderRequest;
import com.project.shopapp.dtos.responses.ApiResponse;
import com.project.shopapp.dtos.responses.OrderResponse;
import com.project.shopapp.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("")
    public ApiResponse<OrderResponse> createOrder(
            @Valid @RequestBody OrderRequest orderRequest
    ) {
        return ApiResponse.<OrderResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Create order successfully")
                .result(orderService.createOrder(orderRequest))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getOrder(@Valid @PathVariable("id") Long id) {
        return ApiResponse.<OrderResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Get order successfully")
                .result(orderService.getOrder(id))
                .build();
    }

    @GetMapping("/user/{user_id}")
    public ApiResponse<List<OrderResponse>> getOrdersByUserId(@Valid @PathVariable("user_id") Long userId) {
        return ApiResponse.<List<OrderResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Get list order response by user id successfully")
                .result(orderService.findByUserId(userId))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<OrderResponse> updateOrder(
            @Valid @PathVariable long id,
            @Valid @RequestBody OrderRequest orderRequest
    ) {
        return ApiResponse.<OrderResponse>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Update order successfully")
                .result(orderService.updateOrder(id, orderRequest))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteOrder(@Valid @PathVariable Long id) {
        //Xoa mem => cap nhat truong active => false
        orderService.deleteOrder(id);
        return ApiResponse.<String>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Order deleted successfully")
                .build();
    }
}
