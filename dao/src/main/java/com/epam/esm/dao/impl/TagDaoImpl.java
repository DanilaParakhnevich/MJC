package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.TagEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Component
public class TagDaoImpl implements TagDao {
    private EntityManager entityManager;

    @Override
    public TagEntity create(TagEntity tag) {
        entityManager.persist(tag);
        return readByName(tag.getName()).get();
    }

    @Override
    public List<TagEntity> readAll(long page, long pageSize) {
        CriteriaQuery<TagEntity> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(TagEntity.class);
        Root<TagEntity> root = criteriaQuery.from(TagEntity.class);
        TypedQuery<TagEntity> allQuery = entityManager.createQuery(criteriaQuery.select(root));
        allQuery.setFirstResult((int) ((page - 1) * pageSize));
        allQuery.setMaxResults((int) pageSize);
        return allQuery.getResultList();
    }

    @Override
    public Optional<TagEntity> readByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> criteriaQuery = criteriaBuilder.createQuery(TagEntity.class);
        Root<TagEntity> rootEntry = criteriaQuery.from(TagEntity.class);
        criteriaQuery.select(rootEntry).where(criteriaBuilder.equal(rootEntry.get("name"), name));
        TypedQuery<TagEntity> nameQuery = entityManager.createQuery(criteriaQuery);
        return nameQuery.getResultList().stream()
                .findFirst();
    }

    @Override
    public Optional<TagEntity> readById(long id) {
        return Optional.ofNullable(entityManager.find(TagEntity.class, id));
    }

    @Override
    public void deleteById(long id) {
        entityManager.remove(readById(id));
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
