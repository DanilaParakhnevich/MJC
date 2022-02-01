package com.epam.esm;

import com.epam.esm.entity.TagEntity;

import java.util.List;
import java.util.Optional;

public interface TagDAO extends DAO {
    Optional<TagEntity> add(TagEntity tag);

    Optional<TagEntity> findById(long id);

    List<TagEntity> findByCertificateId(long id);

    Optional<TagEntity> findByName(String name);

    List<TagEntity> findAll();

    boolean delete (long id);
}
