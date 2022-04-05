package com.epam.esm.dao.impl;

import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.dao.CertificateDao;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class CertificateDaoImpl implements CertificateDao {
    private EntityManager entityManager;
    private static final String CREATE_LINK = "insert into certificate_by_tag (id_tag, id_certificate) " +
            "values (?, ?)";
    private static final String DELETE_LINK = "delete from certificate_by_tag where id_tag = ? " +
            "and id_certificate = ?";


    @Override
    public List<CertificateEntity> findAllByNameContainingIgnoreCase(String name, long page, long pageSize) {
        Query query = entityManager.createNativeQuery("select * from gift_certificate where gift_certificate.name like :name",
                CertificateEntity.class);
        query.setFirstResult((int) ((page - 1) * pageSize));
        query.setMaxResults((int) pageSize);
        query.setParameter("name", name + "%");
        return query.getResultList();
    }

    @Override
    public Optional<CertificateEntity> findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CertificateEntity> criteriaQuery = criteriaBuilder.createQuery(CertificateEntity.class);
        Root<CertificateEntity> certificateRoot = criteriaQuery.from(CertificateEntity.class);
        criteriaQuery.select(certificateRoot)
                .where(criteriaBuilder.equal(certificateRoot.get("name"), name));
        TypedQuery<CertificateEntity> query = entityManager.createQuery(criteriaQuery);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<CertificateEntity> findAllByTag(String tagName, long page, long pageSize) {
        Query query = entityManager.createNativeQuery("select * from gift_certificate join certificate_by_tag on " +
                        "certificate_by_tag.id_certificate = gift_certificate.id join tag on tag.id = certificate_by_tag.id_tag " +
                        "where tag.name like :name",
                CertificateEntity.class);
        query.setFirstResult((int) ((page - 1) * pageSize));
        query.setMaxResults((int) pageSize);
        query.setParameter("name", tagName + "%");
        return query.getResultList();
    }

    @Override
    public List<CertificateEntity> findAllByTags(List<String> tags) {
        Query query = entityManager.createNativeQuery(createQueryForReadByTags(tags),
                CertificateEntity.class);
        return query.getResultList();
    }

    @Override
    public List<CertificateEntity> findAll(long page, long pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CertificateEntity> criteriaQuery = criteriaBuilder.createQuery(CertificateEntity.class);
        criteriaQuery.select(criteriaQuery.from(CertificateEntity.class));
        TypedQuery<CertificateEntity> allQuery = entityManager.createQuery(criteriaQuery);
        allQuery.setFirstResult((int) ((page - 1) * pageSize));
        allQuery.setMaxResults((int) pageSize);
        return allQuery.getResultList();
    }

    @Override
    public Optional<CertificateEntity> findById(long id) {
        Session session = entityManager.unwrap(Session.class);
        CertificateEntity findingCertificate = entityManager.find(CertificateEntity.class, id);
        if (findingCertificate != null && session.contains(findingCertificate)) {
            entityManager.unwrap(Session.class).evict(findingCertificate);
        }
        return Optional.ofNullable(entityManager.find(CertificateEntity.class, id));
    }


    @Override
    public void create(CertificateEntity certificate) {
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(certificate.getCreateDate());
        entityManager.persist(certificate);
    }

    @Override
    public void update(CertificateEntity certificate) {
        certificate.setLastUpdateDate(LocalDateTime.now());
        entityManager.merge(certificate);
        entityManager.unwrap(Session.class).flush();
    }

    @Override
    public void delete(long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public void createLink(long tagId, long certificateId) {
        Query nativeQuery = entityManager.createNativeQuery(CREATE_LINK);
        nativeQuery.setParameter(1, tagId);
        nativeQuery.setParameter(2, certificateId);
        nativeQuery.executeUpdate();
    }

    @Override
    public void deleteLink(long tagId, long certificateId) {
        Query nativeQuery = entityManager.createNativeQuery(DELETE_LINK);
        nativeQuery.setParameter(1, tagId);
        nativeQuery.setParameter(2, certificateId);
        nativeQuery.executeUpdate();
    }

    private String createQueryForReadByTags(List<String> tags) {
        StringBuilder query = new StringBuilder().append("select DISTINCT * from gift_certificate where ");
        for (String tag : tags) {
            query.append("gift_certificate.id in (select gift_certificate.id from gift_certificate join certificate_by_tag on certificate_by_tag.id_certificate = gift_certificate.id " +
                    "join tag on tag.id = certificate_by_tag.id_tag where tag.name = ").append('\'').append(tag).append('\'').append(')');
            if (tags.indexOf(tag) != (tags.size() - 1)) {
                query.append(" and ");
            }
        }
        return query.toString();
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
