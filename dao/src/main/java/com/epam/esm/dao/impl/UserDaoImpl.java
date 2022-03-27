package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.UserEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {
    private EntityManager entityManager;

    @Override
    public List<UserEntity> readAll(long page, long pageSize) {
        Query query = entityManager.createNativeQuery("select * from \"user\"",
                UserEntity.class);
        query.setFirstResult((int) ((page - 1) * pageSize));
        query.setMaxResults((int) pageSize);
        return query.getResultList();
    }

    @Override
    public Optional<UserEntity> readById(long id) {
        return Optional.ofNullable(entityManager.find(UserEntity.class, id));
    }

    @Override
    public Optional<UserEntity> readByNickname(String nickname) {
        Query query = createQueryForUserEntity("nickname", nickname);
        return Optional.ofNullable((UserEntity) query.getSingleResult());
    }

    @Override
    public Optional<UserEntity> readByMail(String mail) {
        Query query = createQueryForUserEntity("mail", mail);
        return Optional.ofNullable((UserEntity) query.getSingleResult());
    }
    private Query createQueryForUserEntity(String searchParameter, String value) {
        return entityManager.createNativeQuery("select * from \"user\" where \"user\"."+ searchParameter + " = " + value,
                UserEntity.class);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
