package com.epam.esm.controller;

import com.epam.esm.TagService;
import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.mapper.TagClientModelMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagControllerTest {
    MockMvc mockMvc;
    TagEntity tagEntity;
    TagClientModel tagClientModel;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Mock
    TagService tagService;
    TagController controller;


    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        controller = new TagController();
        controller.setTagService(tagService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @BeforeEach
    void configuration() {
        tagEntity = new TagEntity();
        tagEntity.setId(1);
        tagEntity.setName("1");
    }

    @Test
    void add() throws Exception {
    }

    @Test
    void findAll() throws Exception {
        Mockito.when(tagService.findAll())
                .thenReturn(Arrays
                        .asList(TagClientModelMapper.INSTANCE.tagToTagClientModel(tagEntity)));
        mockMvc.perform(get("/tags"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("tag", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("name", is("1"))
                        )
                )));
        verify(controller, times(1)).findAll();
        verifyNoMoreInteractions(controller);
    }

    @Test
    void findById() {
    }

    @Test
    void deleteTag() {
    }

    @Test
    void setTagService() {
    }
}