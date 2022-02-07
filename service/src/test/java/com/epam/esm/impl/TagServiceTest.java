package com.epam.esm.impl;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.mapper.TagClientModelMapper;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.exception.DuplicateTagException;
import com.epam.esm.validator.exception.UnknownTagException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagServiceTest {
    TagValidator validator;
    TagServiceImpl tagService;
    TagEntity tag;
    TagEntity addedTag;

    TagEntity firstTag;
    TagEntity secondTag;

    @Mock
    TagDaoImpl tagDAO;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        tagService = new TagServiceImpl();
        validator = new TagValidator();
        validator.setTagDAO(tagDAO);
        tagService.setValidator(validator);
        tagService.setTagDAO(tagDAO);
    }

    @BeforeEach
    void configuration() {
        tag = new TagEntity(0, "s");
        addedTag = new TagEntity(5, "s");

        firstTag = new TagEntity(3, "a");
        secondTag = new TagEntity(4, "b");
    }
    @Test
    void addTest() {
        when(tagDAO.findByName("s"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(addedTag));
        when(tagDAO.add(tag))
                .thenReturn(Optional.of(addedTag));
        assertEquals(TagClientModelMapper.INSTANCE.tagToTagClientModel(addedTag),
                tagService.add(tag));
    }

    @Test
    void addTestForThrowing() {
        when(tagDAO.findByName("s"))
                .thenReturn(Optional.of(addedTag));
        when(tagDAO.add(tag))
                .thenReturn(Optional.of(addedTag));
        assertThrows(DuplicateTagException.class, () -> tagService.add(tag));
    }

    @Test
    void addIfNotExistTest() {
        when(tagDAO.findByName("s"))
                .thenReturn(Optional.of(addedTag));
        when(tagDAO.add(tag)).
                thenReturn(Optional.of(addedTag));
        assertEquals(TagClientModelMapper.INSTANCE.tagToTagClientModel(addedTag),
                tagService.addIfNotExist(tag));
    }

    @Test
    void findAllTest() {
        List<TagEntity> tags = Arrays.asList(firstTag, secondTag);
        when(tagDAO.findAll())
                .thenReturn(tags);
        assertEquals(tagService.findAll(),
                tags.stream()
                        .map(TagClientModelMapper.INSTANCE::tagToTagClientModel)
                        .collect(Collectors.toList()));
    }

    @Test
    void findTagByIdTest() {
        when(tagDAO.findById(5))
                .thenReturn(Optional.ofNullable(addedTag));
        assertEquals(TagClientModelMapper.INSTANCE.tagToTagClientModel(addedTag),
                tagService.findTagById(5));
    }

    @Test
    void findTagByIdTestForThrowing() {
        when(tagDAO.findById(5))
                .thenReturn(Optional.empty());
        assertThrows(UnknownTagException.class, () -> tagService.findTagById(5));
    }

    @Test
    void findTagByNameTest() {
        when(tagDAO.findByName("s")).thenReturn(Optional.ofNullable(addedTag));
        assertEquals(TagClientModelMapper.INSTANCE.tagToTagClientModel(addedTag),
                tagService.findTagByName("s"));
    }

    @Test
    void findTagByNameTestForThrowing() {
        when(tagDAO.findByName("s")).thenReturn(Optional.empty());
        assertThrows(UnknownTagException.class,
                () -> tagService.findTagByName("s"));
    }

    @Test
    void deleteByIdTest() {
        when(tagDAO.findById(5)).thenReturn(Optional.of(addedTag));
        when(tagDAO.delete(5)).thenReturn(true);
        assertTrue(tagService.deleteById(5));
    }

    @Test
    void deleteByIdTestForThrowing() {
        when(tagDAO.findById(5)).thenReturn(Optional.empty());
        assertThrows(UnknownTagException.class, () -> tagService.findTagById(5));
    }
}