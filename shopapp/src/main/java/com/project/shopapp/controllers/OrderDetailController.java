package com.project.shopapp.controllers;

import com.project.shopapp.dtos.requests.OrderDetailRequest;
import com.project.shopapp.dtos.responses.ApiResponse;
import com.project.shopapp.dtos.responses.OrderDetailResponse;
import com.project.shopapp.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order_details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @PostMapping("")
    public ApiResponse<OrderDetailResponse> createOrderDetail(
            @Valid @RequestBody OrderDetailRequest newOrderDetail
    ) {
        return ApiResponse.<OrderDetailResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Create order detail successfully")
                .result(orderDetailService.createOrderDetail(newOrderDetail))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderDetailResponse> getOrderDetail(
            @Valid @PathVariable("id") long id
    ) {
        return ApiResponse.<OrderDetailResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Get order detail successfully")
                .result(orderDetailService.getOrderDetail(id))
                .build();
    }

    @GetMapping("/order/{orderId}")
    public ApiResponse<List<OrderDetailResponse>> findByOrderId(
            @Valid @PathVariable("orderId") Long orderId
    ) {
        return ApiResponse.<List<OrderDetailResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("get list order detail by orderId successfully")
                .result(orderDetailService.findByOrderId(orderId))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<OrderDetailResponse> updateOrderDetail(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailRequest orderDetailRequest
    ) {
        return ApiResponse.<OrderDetailResponse>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("update order detail successfully")
                .result(orderDetailService.updateOrderDetail(id, orderDetailRequest))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteOrderDetail(
            @Valid @PathVariable("id") long id
    ) {
        orderDetailService.deleteById(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Delete order detail with id: " + id + "successfully")
                .build();
    }
}
