package com.project.shopapp.services.impl;

import com.project.shopapp.common.constant.OrderStatus;
import com.project.shopapp.dtos.requests.OrderRequest;
import com.project.shopapp.dtos.responses.OrderResponse;
import com.project.shopapp.exceptions.AppException;
import com.project.shopapp.exceptions.ErrorCode;
import com.project.shopapp.mapper.OrderMapper;
import com.project.shopapp.models.Account;
import com.project.shopapp.models.Order;
import com.project.shopapp.repositories.AccountRepository;
import com.project.shopapp.repositories.OrderRepository;
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
    //Repo
    AccountRepository accountRepo;
    OrderRepository orderRepo;

    //Mapper
    OrderMapper orderMapper;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        //check su ton tai cua userId
        Account account = existingUser(orderRequest.getUserId());

        //convert orderDTO => Order
        Order order = orderMapper.toEntity(orderRequest);
        order.setAccount(account);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);

        LocalDate shippingDate = orderRequest.getShippingDate() == null ? LocalDate.now() : orderRequest.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new AppException(ErrorCode.SHIPPING_DATE_INVALID);
        }

        order.setActive(true);
        return orderMapper.toDto(orderRepo.save(order));
    }

    @Override
    public OrderResponse getOrder(Long id) {
        return orderMapper.toDto(existingOrder(id));
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderRequest orderRequest) {
        Order existingOrder = existingOrder(id);
        Account existingAccount = existingUser(orderRequest.getUserId());
        orderMapper.update(orderRequest, existingOrder);
        existingOrder.setAccount(existingAccount);

        return orderMapper.toDto(orderRepo.save(existingOrder));
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
        return orderRepo.findByAccountId(userId)
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    private Account existingUser(Long userId) {
        return accountRepo
                .findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    private Order existingOrder(Long id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
    }
}
