package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.requests.OrderDetailDTO;
import com.project.shopapp.dtos.responses.OrderDetailResponse;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.OrderDetailRepository;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.services.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderRepository orderRepo;
    private final OrderDetailRepository orderDetailRepo;
    private final ProductRepository productRepo;
    private final ModelMapper mapper;

    @Override
    public OrderDetailResponse createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        Order order = existOrder(orderDetailDTO.getOrderId());
        Product product = existProduct(orderDetailDTO.getProductId());

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .price(orderDetailDTO.getPrice())
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .color(orderDetailDTO.getColor())
                .totalMoneyOD(orderDetailDTO.getTotalMoneyOD())
                .build();
        orderDetailRepo.save(orderDetail);
        return mapper.map(orderDetail, OrderDetailResponse.class);
    }

    @Override
    public OrderDetailResponse getOrderDetail(Long id) throws DataNotFoundException {
        OrderDetail orderDetail = existOrderDetail(id);
        return mapper.map(orderDetail, OrderDetailResponse.class);
    }

    @Override
    public OrderDetailResponse updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        OrderDetail orderDetail = existOrderDetail(id);
        Order order = existOrder(orderDetailDTO.getOrderId());
        Product product = existProduct(orderDetailDTO.getProductId());

        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setPrice(orderDetailDTO.getPrice());
        orderDetail.setNumberOfProducts(orderDetail.getNumberOfProducts());
        orderDetail.setTotalMoneyOD(orderDetail.getTotalMoneyOD());
        orderDetail.setColor(orderDetailDTO.getColor());
        orderDetailRepo.save(orderDetail);
        return mapper.map(orderDetail, OrderDetailResponse.class);
    }

    @Override
    public void deleteById(Long id) {
        orderDetailRepo.deleteById(id);
    }

    @Override
    public List<OrderDetailResponse> findByOrderId(Long orderId) {
        return orderDetailRepo.findByOrderId(orderId).stream()
                .map(orderDetail -> mapper.map(orderDetail, OrderDetailResponse.class))
                .toList();
    }

    private Product existProduct(Long productId) throws DataNotFoundException {
        return productRepo.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Can not found product with id: " + productId));
    }

    private Order existOrder(Long orderId) throws DataNotFoundException {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Can not found order with id: " + orderId));
    }

    private OrderDetail existOrderDetail(Long orderDetailId) throws DataNotFoundException {
        return orderDetailRepo.findById(orderDetailId)
                .orElseThrow(() -> new DataNotFoundException("Can not find order detail with id: " + orderDetailId));
    }
}
