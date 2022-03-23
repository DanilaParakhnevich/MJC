package com.epam.esm.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.dao.TagDao;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TagRepositoryTest {
    @Autowired
    private TagDao tagRepository;
    private TagEntity firstTestTag;
    private TagEntity secondTestTag;
    private TagEntity thirdTestTag;
    private TagEntity fourthTestTag;
    private TagEntity fifthTestTag;
    private List<TagEntity> tagList;


    @BeforeEach
    void setUp() {
        firstTestTag = new TagEntity(5, "FIFTH");
        secondTestTag = new TagEntity(2, "fun");
        thirdTestTag = new TagEntity(1, "jumping");
        fourthTestTag = new TagEntity(3, "sport");
        fifthTestTag = new TagEntity(4, "clear");
    }



    @Test
    void findById() {
        assertEquals(tagRepository.findById(2L), Optional.of(secondTestTag));
    }

    @Test
    void findByCertificateId() {
        tagList = Arrays.asList(fourthTestTag, fifthTestTag, secondTestTag);
        assertEquals(tagRepository.findById(2L), tagList);
    }

    @Test
    void findByName() {
        assertEquals(tagRepository.findByName("jumping"), Optional.of(thirdTestTag));
    }

    @Test
    void findAll() {
        tagList =  Arrays.asList(thirdTestTag, secondTestTag, fourthTestTag, fifthTestTag);
        assertEquals(tagRepository.findAll(), tagList);
    }

    @Test
    void addAndAddTest() {
        assertEquals(tagRepository.save(firstTestTag), Optional.of(firstTestTag));
    }

    @Autowired
    public void setTagRepository(TagDao tagRepository) {
        this.tagRepository = tagRepository;
    }
}