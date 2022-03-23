package com.epam.esm.validator;

import com.epam.esm.TagService;
import com.epam.esm.dto.TagClientModel;
import com.epam.esm.mapper.TagModelMapper;
import com.epam.esm.mapper.TagModelMapperImpl;
import com.epam.esm.validator.exception.ValidatorException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagValidatorTest {
    TagValidator validator;
    TagClientModel tag;
    TagModelMapper mapper;

    @Mock
    TagService tagService;


    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        validator = new TagValidator();
        mapper = new TagModelMapperImpl();
    }

    @BeforeEach
    void configuration() {
        tag = new TagClientModel(1, "a");
    }

    @Test
    void validateTest1() {
        tag.setName(null);
        assertThrows(ValidatorException.class,
                () -> validator.validate(tag));
    }

    @Test
    void validateTest2() {
        tag.setName("");
        assertThrows(ValidatorException.class,
                () -> validator.validate(tag));
    }

    @Test
    void validateTest3() {
        when(tagService.readByName(tag.getName()))
                .thenReturn(tag);
        assertThrows(ValidatorException.class,
                () -> validator.validate(tag));
    }
}