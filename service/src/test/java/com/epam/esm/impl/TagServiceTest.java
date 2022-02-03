package com.epam.esm.impl;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.mapper.TagClientModelMapper;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.exception.DuplicateTagException;
import com.epam.esm.validator.exception.UnknownTagException;
import config.TestConfig;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagServiceTest {
    AnnotationConfigApplicationContext context
            = new AnnotationConfigApplicationContext(TestConfig.class);
    TagValidator validator = new TagValidator();
    TagServiceImpl tagService = new TagServiceImpl();
    TagEntity tag;
    TagEntity addedTag;

    List<TagEntity> tags;
    TagEntity firstTag;
    TagEntity secondTag;

    @Mock
    TagDAOImpl tagDAO;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        tagDAO.setJdbcTemplate((JdbcTemplate) context.getBean("testJdbcTemplate"));
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
        tags = Arrays.asList(firstTag, secondTag);
    }
    @Test
    void addTest() {
        Mockito.when(tagDAO.findByName("s"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(addedTag));
        Mockito.when(tagDAO.add(tag))
                .thenReturn(Optional.of(addedTag));
        Assertions.assertEquals(TagClientModelMapper.INSTANCE.tagToTagClientModel(addedTag),
                tagService.add(tag));
    }

    @Test
    void addTestForThrowing() {
        Mockito.when(tagDAO.findByName("s"))
                .thenReturn(Optional.of(addedTag));
        Mockito.when(tagDAO.add(tag))
                .thenReturn(Optional.of(addedTag));
        Assertions.assertThrows(DuplicateTagException.class, () -> tagService.add(tag));
    }

    @Test
    void addIfNotExistTest() {
        Mockito.when(tagDAO.findByName("s"))
                .thenReturn(Optional.of(addedTag));
        Mockito.when(tagDAO.add(tag)).
                thenReturn(Optional.of(addedTag));
        Assertions.assertEquals(TagClientModelMapper.INSTANCE.tagToTagClientModel(addedTag),
                tagService.addIfNotExist(tag));
    }

    @Test
    void findAllTest() {
        Mockito.when(tagDAO.findAll())
                .thenReturn(tags);
        Assertions.assertEquals(tagService.findAll(),
                tags.stream()
                        .map(TagClientModelMapper.INSTANCE::tagToTagClientModel)
                        .collect(Collectors.toList()));
    }

    @Test
    void findTagByIdTest() {
        Mockito.when(tagDAO.findById(5))
                .thenReturn(Optional.ofNullable(addedTag));
        Assertions.assertEquals(TagClientModelMapper.INSTANCE.tagToTagClientModel(addedTag),
                tagService.findTagById(5));
    }

    @Test
    void findTagByIdTestForThrowing() {
        Mockito.when(tagDAO.findById(5))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(UnknownTagException.class, () -> tagService.findTagById(5));
    }

    @Test
    void findTagByNameTest() {
        Mockito.when(tagDAO.findByName("s")).thenReturn(Optional.ofNullable(addedTag));
        Assertions.assertEquals(TagClientModelMapper.INSTANCE.tagToTagClientModel(addedTag),
                tagService.findTagByName("s"));
    }

    @Test
    void findTagByNameTestForThrowing() {
        Mockito.when(tagDAO.findByName("s")).thenReturn(Optional.empty());
        Assertions.assertThrows(UnknownTagException.class,
                () -> tagService.findTagByName("s"));
    }

    @Test
    void deleteByIdTest() {
        Mockito.when(tagDAO.findById(5)).thenReturn(Optional.of(addedTag));
        Mockito.when(tagDAO.delete(5)).thenReturn(true);
        Assertions.assertEquals(tagService.deleteById(5), true);
    }

    @Test
    void deleteByIdTestForThrowing() {
        Mockito.when(tagDAO.findById(5)).thenReturn(Optional.empty());
        Assertions.assertThrows(UnknownTagException.class, () -> tagService.findTagById(5));
    }
}