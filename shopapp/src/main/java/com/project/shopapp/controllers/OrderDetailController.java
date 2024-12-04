package com.project.shopapp.controllers;

import com.project.shopapp.dtos.requests.OrderDetailDTO;
import com.project.shopapp.dtos.responses.OrderDetailResponse;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/order_details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO newOrderDetail
    ) {
        try {
            OrderDetailResponse orderDetailResponse = orderDetailService.createOrderDetail(newOrderDetail);
            return ResponseEntity.ok(orderDetailResponse);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(
            @Valid @PathVariable("id") long id
    ) {
        OrderDetailResponse response = null;
        try {
            response = orderDetailService.getOrderDetail(id);
            return ResponseEntity.ok(response);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> findByOrderId(
            @Valid @PathVariable("orderId") Long orderId
    ) {
        return ResponseEntity.ok(orderDetailService.findByOrderId(orderId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDTO orderDetailDTO
    ) {
        try {
            return ResponseEntity.ok(orderDetailService.updateOrderDetail(id, orderDetailDTO));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(
            @Valid @PathVariable("id") long id
    ) {
        orderDetailService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
