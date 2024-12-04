package com.project.shopapp.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.OrderDetail;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
    private Long id;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("product_id")
    private Long productId;

    private Float price;

    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @JsonProperty("total_money_od")
    private Float totalMoneyOD;

    private String color;

//    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail) {
//        return OrderDetailResponse.builder()
//                .id(orderDetail.getId())
//                .orderId(orderDetail.getOrder().getId())
//                .productId(orderDetail.getProduct().getId())
//                .price(orderDetail.getPrice())
//                .numberOfProducts(orderDetail.getNumberOfProducts())
//                .color(orderDetail.getColor())
//                .totalMoneyOD(orderDetail.getTotalMoneyOD())
//                .build();
//    }
}
