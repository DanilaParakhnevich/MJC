package com.epam.esm.dao;

import com.epam.esm.entity.TagEntity;

import java.util.List;
import java.util.Optional;


public interface TagDao {
    TagEntity create(TagEntity tag);

    List<TagEntity> readAll(long page, long pageSize);

    Optional<TagEntity> readByName(String name);

    Optional<TagEntity> readById(long id);

    void deleteById(long id);
}
