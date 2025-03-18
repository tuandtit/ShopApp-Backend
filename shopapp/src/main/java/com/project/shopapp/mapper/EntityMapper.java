package com.project.shopapp.mapper;

import org.mapstruct.*;

import java.util.List;

public interface EntityMapper<E, Q, S> {
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    E toEntity(Q request);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    S toDto(E entity);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    List<E> toEntityList(List<Q> request);

    @Named(value = "update")
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void update(Q dto, @MappingTarget E entity);

    List<S> toDtoList(List<E> entities);
}

