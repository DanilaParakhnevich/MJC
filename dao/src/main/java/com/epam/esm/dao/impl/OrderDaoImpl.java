package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.extractor.OrderExtractor;
import com.epam.esm.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderDaoImpl implements OrderDao {
    private static final String CREATE = "insert into \"order\" (id_user, id_certificate, price, purchase_date) " +
            "values (?, ?, ?, ?)";
    private static final String READ_BY_USER_ID = "select o.id as o_id,  o.price as o_price, o.purchase_date as o_purchase_date, " +
            "c.id as id, c.price as price, c.name as name, c.description as description, c.create_date as create_date, " +
            "c.last_update_date as last_update_date, c.duration as duration from \"order\" o " +
            "join \"user\" u on o.id_user = u.id join gift_certificate c on o.id_certificate = c.id " +
            "where u.id = ? order by o.id asc limit ? offset ?";
    private static final String READ_ALL = "select o.id as o_id,  o.price as o_price, o.purchase_date as o_purchase_date, " +
            "c.id as id, c.price as price, c.name as name, c.description as description, c.create_date as create_date, " +
            "c.last_update_date as last_update_date, c.duration as duration from \"order\" o " +
            "join \"user\" u on o.id_user = u.id join gift_certificate c on o.id_certificate = c.id " +
            "order by o.id asc limit ? offset ?";
    private static final String READ_LAST_BY_USER_ID = "select o.id as o_id,  o.price as o_price, o.purchase_date as o_purchase_date, " +
            "c.id as id, c.price as price, c.name as name, c.description as description, c.create_date as create_date, " +
            "c.last_update_date as last_update_date, c.duration as duration from \"order\" o " +
            "join \"user\" u on o.id_user = u.id join gift_certificate c on o.id_certificate = c.id " +
            "where o.id = (select max(o.id) from \"order\" o) and u.id = ?";
    private static final String READ_BY_ID = "select o.id as o_id,  o.price as o_price, o.purchase_date as o_purchase_date, " +
            "c.id as id, c.price as price, c.name as name, c.description as description, c.create_date as create_date, " +
            "c.last_update_date as last_update_date, c.duration as duration from \"order\" o " +
            "join \"user\" u on o.id_user = u.id join gift_certificate c on o.id_certificate = c.id " +
            "where o.id = ?";
    private JdbcTemplate jdbcTemplate;
    private OrderExtractor orderExtractor;

    @Override
    public void create(OrderEntity order, long userId) {
        jdbcTemplate.update(CREATE, userId, order.getCertificate().getId(),
                order.getCertificate().getPrice(), order.getPurchaseDate());
    }

    @Override
    public List<OrderEntity> readByUserId(long userId, long page, long pageSize) {
        return jdbcTemplate.query(READ_BY_USER_ID, orderExtractor, userId, pageSize, (page - 1) * pageSize);
    }

    @Override
    public List<OrderEntity> readAll(long page, long pageSize) {
        return jdbcTemplate.query(READ_ALL, orderExtractor, pageSize, (page - 1) * pageSize);
    }

    @Override
    public Optional<OrderEntity> readById(long id) {
        List<OrderEntity> orders = jdbcTemplate.query(READ_BY_ID, orderExtractor, id);
        if (orders.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(orders.get(0));
    }

    @Override
    public Optional<OrderEntity> readLastByUserId(long userId) {
        List<OrderEntity> orders = jdbcTemplate.query(READ_LAST_BY_USER_ID, orderExtractor, userId);
        if (orders.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(orders.get(0));
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setOrderExtractor(OrderExtractor orderExtractor) {
        this.orderExtractor = orderExtractor;
    }
}
