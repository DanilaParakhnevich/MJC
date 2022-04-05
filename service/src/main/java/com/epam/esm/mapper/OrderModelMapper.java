package com.epam.esm.mapper;

import com.epam.esm.dto.OrderClientModel;
import com.epam.esm.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface OrderModelMapper {
    OrderClientModel toClientModel(OrderEntity order);
    OrderEntity toEntity (OrderClientModel order);
}
