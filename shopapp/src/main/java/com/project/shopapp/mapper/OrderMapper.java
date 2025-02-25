package com.project.shopapp.mapper;

import com.project.shopapp.dtos.requests.OrderRequest;
import com.project.shopapp.dtos.responses.OrderResponse;
import com.project.shopapp.models.Order;
import org.mapstruct.Mapper;

@Mapper(
        config = MapperConfig.class
)
public interface OrderMapper extends EntityMapper<Order, OrderRequest, OrderResponse>{
}
