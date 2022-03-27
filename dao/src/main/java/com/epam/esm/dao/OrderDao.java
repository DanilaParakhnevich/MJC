package com.epam.esm.dao;

import com.epam.esm.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    void create (OrderEntity order);

    List<OrderEntity> readByUserId(long userId, long page, long pageSize);

    List<OrderEntity> readAll(long page, long pageSize);

    Optional<OrderEntity> readById(long id);

    Optional<OrderEntity> readLastByUserId(long userId);
}
