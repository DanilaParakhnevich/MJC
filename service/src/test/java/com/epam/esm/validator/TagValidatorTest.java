package com.epam.esm.validator;

import com.epam.esm.TagDao;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.validator.exception.BadNameException;
import com.epam.esm.validator.exception.DuplicateTagException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagValidatorTest {
    TagValidator validator;
    TagEntity tag;

    @Mock
    TagDao tagDAO;


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
        assertThrows(BadNameException.class,
                () -> validator.validate(tag));
    }

    @Test
    void validateTest2() {
        tag.setName("");
        assertThrows(BadNameException.class,
                () -> validator.validate(tag));
    }

    @Test
    void validateTest3() {
        when(tagDAO.findByName(tag.getName()))
                .thenReturn(Optional.of(tag));
        assertThrows(DuplicateTagException.class,
                () -> validator.validate(tag));
    }
}