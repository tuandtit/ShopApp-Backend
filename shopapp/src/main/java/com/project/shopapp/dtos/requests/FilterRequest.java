package com.project.shopapp.dtos.requests;

import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public abstract class FilterRequest<T> {
    private PagingRequest paging = new PagingRequest();

    public abstract Specification<T> specification();
}
