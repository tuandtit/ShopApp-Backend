package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.requests.OrderDetailRequest;
import com.project.shopapp.dtos.responses.OrderDetailResponse;
import com.project.shopapp.exceptions.AppException;
import com.project.shopapp.exceptions.ErrorCode;
import com.project.shopapp.mapper.OrderDetailMapper;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.OrderDetailRepository;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.services.OrderDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailServiceImpl implements OrderDetailService {
    OrderRepository orderRepo;
    OrderDetailRepository orderDetailRepo;
    ProductRepository productRepo;
    OrderDetailMapper orderDetailMapper;


    @Override
    public OrderDetailResponse createOrderDetail(OrderDetailRequest orderDetailRequest) throws AppException {
        Order order = existOrder(orderDetailRequest.getOrderId());
        Product product = existProduct(orderDetailRequest.getProductId());

        OrderDetail orderDetail = orderDetailMapper.toEntity(orderDetailRequest);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);

        return orderDetailMapper.toDto(orderDetailRepo.save(orderDetail));
    }

    @Override
    public OrderDetailResponse getOrderDetail(Long id) {
        return orderDetailMapper.toDto(existOrderDetail(id));
    }

    @Override
    public OrderDetailResponse updateOrderDetail(Long id, OrderDetailRequest orderDetailRequest) {
        OrderDetail orderDetail = existOrderDetail(id);
        Order order = existOrder(orderDetailRequest.getOrderId());
        Product product = existProduct(orderDetailRequest.getProductId());

        orderDetailMapper.update(orderDetailRequest, orderDetail);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);

        return orderDetailMapper.toDto(orderDetailRepo.save(orderDetail));
    }

    @Override
    public void deleteById(Long id) {
        orderDetailRepo.deleteById(id);
    }

    @Override
    public List<OrderDetailResponse> findByOrderId(Long orderId) {
        return orderDetailRepo.findByOrderId(orderId).stream()
                .map(orderDetailMapper::toDto)
                .toList();
    }

    private Product existProduct(Long productId) {
        return productRepo.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    private Order existOrder(Long orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
    }

    private OrderDetail existOrderDetail(Long orderDetailId) {
        return orderDetailRepo.findById(orderDetailId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND));
    }
}
