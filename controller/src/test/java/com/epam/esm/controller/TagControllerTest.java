package com.epam.esm.controller;

import com.epam.esm.TagService;
import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.TagEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagControllerTest {
    MockMvc mockMvc;
    TagClientModel tag;
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
        tag = new TagClientModel(1, "1");
    }

    @Test
    void findAll() throws Exception {
        when(tagService.findAll())
                .thenReturn(Arrays
                        .asList(tag));
        mockMvc.perform(get("/tags").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("1")));
    }

    @Test
    void findById() throws Exception {
        when(tagService.findTagById(1))
                .thenReturn(tag);
        mockMvc.perform(get("/tags/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("1")));
    }

    @Test
    void deleteTag() throws Exception {
        when(tagService.deleteById(1))
                .thenReturn(true);
        mockMvc.perform(delete("/tags/1"))
                .andExpect(content().string("true"));
    }
}