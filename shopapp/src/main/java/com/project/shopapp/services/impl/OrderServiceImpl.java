package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.requests.OrderRequest;
import com.project.shopapp.dtos.responses.OrderResponse;
import com.project.shopapp.exceptions.AppException;
import com.project.shopapp.exceptions.ErrorCode;
import com.project.shopapp.mapper.OrderMapper;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderStatus;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {
    UserRepository userRepo;
    OrderRepository orderRepo;
    OrderMapper orderMapper;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        //check su ton tai cua userId
        User user = existingUser(orderRequest.getUserId());

        //convert orderDTO => Order
        Order order = orderMapper.toEntity(orderRequest);
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);

        LocalDate shippingDate = orderRequest.getShippingDate() == null ? LocalDate.now() : orderRequest.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new AppException(ErrorCode.SHIPPING_DATE_INVALID);
        }

        order.setActive(true);
        return orderMapper.toResponseDto(orderRepo.save(order));
    }

    @Override
    public OrderResponse getOrder(Long id) {
        return orderMapper.toResponseDto(existingOrder(id));
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderRequest orderRequest) {
        Order existingOrder = existingOrder(id);
        User existingUser = existingUser(orderRequest.getUserId());
        orderMapper.update(orderRequest, existingOrder);
        existingOrder.setUser(existingUser);

        return orderMapper.toResponseDto(orderRepo.save(existingOrder));
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepo.findById(id).orElse(null);
        if (order != null) {
            order.setActive(false);
            orderRepo.save(order);
        }
    }

    @Override
    public List<OrderResponse> findByUserId(Long userId) {
        return orderRepo.findByUserId(userId)
                .stream()
                .map(orderMapper::toResponseDto)
                .toList();
    }

    private User existingUser(Long userId) {
        return userRepo
                .findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    private Order existingOrder(Long id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
    }
}
