package com.epam.esm.impl;

import com.epam.esm.OrderService;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.OrderClientModel;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.handler.exception.BadParameterException;
import com.epam.esm.mapper.OrderModelMapper;
import com.epam.esm.validator.PaginationParametersValidator;
import com.epam.esm.validator.exception.UnknownOrderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.handler.RequestParameter.*;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;
    private OrderModelMapper orderModelMapper;
    private PaginationParametersValidator paginationParametersValidator;
    private static final String BAD_PARAM = "bad_param";

    @Override
    public List<OrderClientModel> search(Map<String, String> parameters) {
        validateSearchParameters(parameters);
        for (Map.Entry<String, String> parameter: parameters.entrySet()) {
            switch (parameter.getKey()) {
                case ID: return Collections.singletonList(readById(Long.parseLong(parameter.getValue())));
                case USER_ID: return readByUserId(parameters);
            }
        }
        throw new BadParameterException(BAD_PARAM);
    }

    @Override
    public List<OrderClientModel> readAll(Map<String, String> parameters) {
        paginationParametersValidator.validate(parameters);
        List<OrderEntity> orders = orderDao.readAll(Long.parseLong(parameters.remove(PAGE)),
                Long.parseLong(parameters.remove(PAGE_SIZE)));
        return collect(orders);
    }

    @Override
    public OrderClientModel readById(long id) {
        Optional<OrderEntity> order = orderDao.readById(id);
        if (order.isPresent()) {
            return collect(Collections.singletonList(order.get())).get(0);
        }
        throw new UnknownOrderException("unknown.order/id=" + id);
    }

    @Override
    public List<OrderClientModel> readByUserId(Map<String, String> parameters) {
        paginationParametersValidator.validate(parameters);
        if (parameters.containsKey("user_id")) {
            List<OrderEntity> orders = orderDao.readByUserId(
                    Long.parseLong(parameters.remove("user_id")),
                    Long.parseLong(parameters.remove(PAGE)),
                    Long.parseLong(parameters.remove(PAGE_SIZE)));
            return collect(orders);
        }
        throw new BadParameterException(BAD_PARAM);
    }

    @Override
    public OrderClientModel create(OrderClientModel order, long userId) {
        order.setPurchaseDate(LocalDateTime.now());
        OrderEntity orderEntity = orderModelMapper.toEntity(order);
        orderEntity.setUserId(userId);
        orderDao.create(orderEntity);
        Optional<OrderEntity> resultOrder = orderDao.readLastByUserId(userId);
        if (resultOrder.isPresent()) {
            resultOrder.get().setUserId(userId);
            return collect(Collections.singletonList(resultOrder.get())).get(0);
        }
        throw new UnknownOrderException("unknown.order/user_id=" + userId);
    }

    private List<OrderClientModel> collect(List<OrderEntity> orders) {
        return orders.stream()
                .map(a -> orderModelMapper.toClientModel(a))
                .collect(Collectors.toList());
    }

    private void validateSearchParameters(Map<String, String> parameters) {
        if (!parameters.containsKey(ID) && !parameters.containsKey(USER_ID)) {
            throw new BadParameterException(BAD_PARAM);
        }
    }

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Autowired
    public void setOrderModelMapper(OrderModelMapper orderModelMapper) {
        this.orderModelMapper = orderModelMapper;
    }

    @Autowired
    public void setPaginationParametersValidator(PaginationParametersValidator paginationParametersValidator) {
        this.paginationParametersValidator = paginationParametersValidator;
    }
}
