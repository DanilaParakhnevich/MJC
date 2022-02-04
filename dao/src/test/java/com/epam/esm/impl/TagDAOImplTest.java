package com.epam.esm.impl;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.config.TestConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagDAOImplTest {
    @Autowired
    TagDAOImpl tagDAO;
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
        Assertions.assertEquals(tagDAO.findById(2), Optional.of(secondTestTag));
    }

    @Test
    void findByCertificateId() {
        tagList = Arrays.asList(secondTestTag);
        Assertions.assertEquals(tagDAO.findByCertificateId(1), tagList);
    }

    @Test
    void findByName() {
        Assertions.assertEquals(tagDAO.findByName("jumping"), Optional.of(thirdTestTag));
    }

    @Test
    void findAll() {
        tagList =  Arrays.asList(thirdTestTag, secondTestTag, fourthTestTag, fifthTestTag);
        Assertions.assertEquals(tagDAO.findAll(), tagList);
    }

    @Test
    void addAndAddTest() {
        Assertions.assertEquals(tagDAO.add(firstTestTag), Optional.of(firstTestTag));
        Assertions.assertTrue(tagDAO.delete(5));
    }
}