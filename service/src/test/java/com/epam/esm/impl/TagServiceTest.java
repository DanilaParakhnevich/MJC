package com.epam.esm.impl;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.mapper.TagModelMapper;
import com.epam.esm.dao.TagDao;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.exception.DuplicateTagException;
import com.epam.esm.validator.exception.UnknownTagException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;


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
    TagDao tagRepository;

    TagModelMapper mapper;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        tagService = new TagServiceImpl();
        validator = new TagValidator();
        tagService.setValidator(validator);
        tagService.setTagDao(tagRepository);
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
        when(tagRepository.findByName("s"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(addedTag));
        when(tagRepository.save(tag))
                .thenReturn(addedTag);
        assertEquals((mapper.toClientModel(addedTag)),
                tagService.add(mapper.toClientModel(tag)));
    }

    @Test
    void addTestForThrowing() {
        when(tagRepository.findByName("s"))
                .thenReturn(Optional.of(addedTag));
        when(tagRepository.save(tag))
                .thenReturn(addedTag);
        assertThrows(DuplicateTagException.class, ()
                -> tagService.add(mapper.toClientModel(tag)));
    }

    @Test
    void addIfNotExistTest() {
        when(tagRepository.findByName("s"))
                .thenReturn(Optional.of(addedTag));
        when(tagRepository.save(tag)).
                thenReturn(addedTag);
        assertEquals(mapper.toClientModel(addedTag),
                tagService.addIfNotExist(mapper.toClientModel(tag)));
    }

    @Test
    void findAllTest() {
        List<TagEntity> tags = Arrays.asList(firstTag, secondTag);
        when(tagRepository.findAll())
                .thenReturn(tags);
        assertEquals(tagService.readAll(),
                tags.stream()
                        .map(mapper::toClientModel)
                        .collect(Collectors.toList()));
    }

    @Test
    void findTagByIdTest() {
        when(tagRepository.findById(5L))
                .thenReturn(Optional.ofNullable(addedTag));
        assertEquals(mapper.toClientModel(addedTag),
                tagService.readById(5));
    }

    @Test
    void findTagByIdTestForThrowing() {
        when(tagRepository.findById(5L))
                .thenReturn(Optional.empty());
        assertThrows(UnknownTagException.class, () -> tagService.readById(5));
    }

    @Test
    void findTagByNameTest() {
        when(tagRepository.findByName("s")).thenReturn(Optional.ofNullable(addedTag));
        assertEquals(mapper.toClientModel(addedTag),
                tagService.readByName("s"));
    }

    @Test
    void findTagByNameTestForThrowing() {
        when(tagRepository.findByName("s")).thenReturn(Optional.empty());
        assertThrows(UnknownTagException.class,
                () -> tagService.readByName("s"));
    }

    @Test
    void deleteByIdTestForThrowing() {
        when(tagRepository.findById(5L)).thenReturn(Optional.empty());
        assertThrows(UnknownTagException.class, () -> tagService.readById(5));
    }

    @Autowired
    public void setMapper (TagModelMapper mapper) {
        this.mapper = mapper;
    }
}