package com.epam.esm.impl;

import com.epam.esm.OrderService;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.TagDao;
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
    private TagDao tagDao;
    private PaginationParametersValidator paginationParametersValidator;

    @Override
    public List<OrderClientModel> search(Map<String, String> parameters) {
        validateSearchParameters(parameters);
        for (Map.Entry<String, String> parameter: parameters.entrySet()) {
            switch (parameter.getKey()) {
                case ID: return Collections.singletonList(readById(Long.parseLong(parameter.getValue())));
                case USER_ID: return readByUserId(Long.parseLong(parameter.getValue()), parameters);
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<OrderClientModel> readAll(Map<String, String> parameters) {
        paginationParametersValidator.validate(parameters);
        List<OrderEntity> orders = orderDao.readAll(Long.parseLong(parameters.remove(PAGE)),
                Long.parseLong(parameters.remove(PAGE_SIZE)));
        return feelWithTagsAndCollect(orders);
    }

    @Override
    public OrderClientModel readById(long id) {
        Optional<OrderEntity> order = orderDao.readById(id);
        if (order.isPresent()) {
            return feelWithTagsAndCollect(Collections.singletonList(order.get())).get(0);
        }
        throw new UnknownOrderException("unknown.order/id=" + id);
    }

    @Override
    public List<OrderClientModel> readByUserId(long userId, Map<String, String> parameters) {
        paginationParametersValidator.validate(parameters);
        List<OrderEntity> orders = orderDao.readByUserId(userId,
                Long.parseLong(parameters.remove(PAGE)),
                Long.parseLong(parameters.remove(PAGE_SIZE)));
        return feelWithTagsAndCollect(orders);
    }

    @Override
    public OrderClientModel create(OrderClientModel order, long userId) {
        order.setPurchaseDate(LocalDateTime.now());
        orderDao.create(orderModelMapper.toEntity(order), userId);
        Optional<OrderEntity> resultOrder = orderDao.readLastByUserId(userId);
        if (resultOrder.isPresent()) {
            return feelWithTagsAndCollect(Collections.singletonList(resultOrder.get())).get(0);
        }
        throw new UnknownOrderException("unknown.order/user_id=" + userId);
    }

    private List<OrderClientModel> feelWithTagsAndCollect (List<OrderEntity> orders) {
        orders.forEach(a -> a.getCertificate().setTags(tagDao.readByCertificateId(a.getCertificate().getId())));
        return orders.stream()
                .map(a -> orderModelMapper.toClientModel(a))
                .collect(Collectors.toList());
    }

    private void validateSearchParameters(Map<String, String> parameters) {
        if (!parameters.containsKey(ID) && !parameters.containsKey(USER_ID)) {
            throw new BadParameterException("bad.param");
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
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Autowired
    public void setPaginationParametersValidator(PaginationParametersValidator paginationParametersValidator) {
        this.paginationParametersValidator = paginationParametersValidator;
    }
}
