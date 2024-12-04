package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.requests.OrderDTO;
import com.project.shopapp.dtos.responses.OrderResponse;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderStatus;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepo;
    private final OrderRepository orderRepo;
    private final ModelMapper mapper;

    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        //check su ton tai cua userId
        User user = existingUser(orderDTO.getUserId());
        //convert orderDTO => Order
        //dung thu vien model mapper
        mapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));

        Order order = mapper.map(orderDTO, Order.class);
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);

        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at least today");
        }
        order.setActive(true);
        orderRepo.save(order);
        return mapper.map(order, OrderResponse.class);
    }

    @Override
    public OrderResponse getOrder(Long id) throws DataNotFoundException {
        return mapper.map(existingOrder(id), OrderResponse.class);
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        Order existingOrder = existingOrder(id);
        User existingUser = existingUser(orderDTO.getUserId());
        mapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        mapper.map(orderDTO, existingOrder);
        existingOrder.setUser(existingUser);
        orderRepo.save(existingOrder);
        return mapper.map(existingOrder, OrderResponse.class);
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
                .map(order -> mapper.map(order, OrderResponse.class))
                .toList();
    }

    private User existingUser(Long userId) throws DataNotFoundException {
        return userRepo
                .findById(userId)
                .orElseThrow(() -> new DataNotFoundException("Can not find user with id: " + userId));
    }

    private Order existingOrder(Long id) throws DataNotFoundException {
        return orderRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Can not find order with id: " + id));
    }
}
