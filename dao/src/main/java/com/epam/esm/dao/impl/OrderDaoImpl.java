package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.OrderEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
public class OrderDaoImpl implements OrderDao {
    private EntityManager entityManager;

    @Override
    public void create(OrderEntity order) {
        entityManager.persist(order);
    }

    @Override
    public List<OrderEntity> readByUserId(long userId, long page, long pageSize) {
        Query query = createQueryForOrderEntity("id_user", String.valueOf(userId));
        query.setFirstResult((int) ((page - 1) * pageSize));
        query.setMaxResults((int) pageSize);
        return query.getResultList();
    }

    @Override
    public List<OrderEntity> readAll(long page, long pageSize) {
        Query query = entityManager.createNativeQuery("select * from \"order\"",
                OrderEntity.class);
        query.setFirstResult((int) ((page - 1) * pageSize));
        query.setMaxResults((int) pageSize);
        return query.getResultList();
    }

    @Override
    public Optional<OrderEntity> readById(long id) {
        Query query = createQueryForOrderEntity("id", String.valueOf(id));
        return Optional.ofNullable((OrderEntity) query.getSingleResult());
    }

    @Override
    public Optional<OrderEntity> readLastByUserId(long userId) {
        Query query = entityManager.createNativeQuery("select * from \"order\" o where o.id_user = :id " +
                        "and o.id = (select max(o.id) from \"order\" o)",
                OrderEntity.class);
        query.setParameter("id", userId);
        return Optional.ofNullable((OrderEntity) query.getSingleResult());
    }

    private Query createQueryForOrderEntity(String searchParameter, String value) {
        return entityManager.createNativeQuery("select * from \"order\" where \"order\"."+ searchParameter + " = " + value,
                OrderEntity.class);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
