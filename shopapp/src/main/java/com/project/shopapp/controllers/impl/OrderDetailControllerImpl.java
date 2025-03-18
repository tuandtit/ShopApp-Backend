package com.project.shopapp.controllers.impl;

import com.project.shopapp.controllers.OrderDetailController;
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
@RequiredArgsConstructor
public class OrderDetailControllerImpl implements OrderDetailController {
    private final OrderDetailService orderDetailService;

    @Override
    public ApiResponse<OrderDetailResponse> createOrderDetail(
            @Valid @RequestBody OrderDetailRequest newOrderDetail
    ) {
        return ApiResponse.<OrderDetailResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Create order detail successfully")
                .result(orderDetailService.createOrderDetail(newOrderDetail))
                .build();
    }

    @Override
    public ApiResponse<OrderDetailResponse> getOrderDetail(
            @Valid @PathVariable("id") long id
    ) {
        return ApiResponse.<OrderDetailResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Get order detail successfully")
                .result(orderDetailService.getOrderDetail(id))
                .build();
    }

    @Override
    public ApiResponse<List<OrderDetailResponse>> findByOrderId(
            @Valid @PathVariable("orderId") Long orderId
    ) {
        return ApiResponse.<List<OrderDetailResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("get list order detail by orderId successfully")
                .result(orderDetailService.findByOrderId(orderId))
                .build();
    }

    @Override
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

    @Override
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
