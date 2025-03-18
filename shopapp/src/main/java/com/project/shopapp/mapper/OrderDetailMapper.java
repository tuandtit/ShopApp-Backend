package com.project.shopapp.mapper;

import com.project.shopapp.dtos.requests.OrderDetailRequest;
import com.project.shopapp.dtos.responses.OrderDetailResponse;
import com.project.shopapp.models.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = MapperConfig.class,
        uses = {ProductMapper.class, OrderMapper.class}
)
public interface OrderDetailMapper extends EntityMapper<OrderDetail, OrderDetailRequest, OrderDetailResponse> {
    @Override
    @Mapping(target = "orderId", expression = "java(entity.getOrder().getId())")
    OrderDetailResponse toDto(OrderDetail entity);
}
