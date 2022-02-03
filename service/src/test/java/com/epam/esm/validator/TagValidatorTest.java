package com.epam.esm.validator;

import com.epam.esm.TagDAO;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.validator.exception.BadNameException;
import com.epam.esm.validator.exception.DuplicateTagException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagValidatorTest {
    TagValidator validator;
    TagEntity tag;

    @Mock
    TagDAO tagDAO;


    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        validator = new TagValidator();
        validator.setTagDAO(tagDAO);
    }

    @BeforeEach
    void configuration() {
        tag = new TagEntity(1, "a");
    }

    @Test
    void validateTest1() {
        tag.setName(null);
        Assertions.assertThrows(BadNameException.class,
                () -> validator.validate(tag));
    }

    @Test
    void validateTest2() {
        tag.setName("");
        Assertions.assertThrows(BadNameException.class,
                () -> validator.validate(tag));
    }

    @Test
    void validateTest3() {
        Mockito.when(tagDAO.findByName(tag.getName()))
                .thenReturn(Optional.of(tag));
        Assertions.assertThrows(DuplicateTagException.class,
                () -> validator.validate(tag));
    }
}