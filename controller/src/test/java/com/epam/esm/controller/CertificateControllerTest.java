package com.epam.esm.controller;

import com.epam.esm.CertificateService;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.mapper.CertificateClientModelMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CertificateControllerTest {
    MockMvc mockMvc;
    CertificateEntity certificateEntity;
    @Mock
    CertificateService certificateService;
    CertificateController controller;
    LocalDateTime time;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        controller = new CertificateController();
        controller.setCertificateService(certificateService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @BeforeEach
    void configuration() {
        time = LocalDateTime.now();
        certificateEntity = new CertificateEntity();
        certificateEntity.setId(1);
        certificateEntity.setDuration(1);
        certificateEntity.setDescription("1");
        certificateEntity.setCreateDate(time);
        certificateEntity.setLastUpdateDate(time);
        certificateEntity.setPrice(new BigDecimal(1));
        certificateEntity.setName("1");
    }

    @Test
    void findAll() throws Exception {
        Mockito.when(certificateService.findAll(null))
                .thenReturn(Arrays
                        .asList(CertificateClientModelMapper.INSTANCE
                                .certificateToCertificateClientModel(certificateEntity)));
        mockMvc.perform(get("/certificates").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].price", is(1)))
                .andExpect(jsonPath("$[0].name", is("1")))
                .andExpect(jsonPath("$[0].duration", is(1)))
                .andExpect(jsonPath("$[0].description", is("1")))
                .andExpect(jsonPath("$[0].createTime", is(time)))
                .andExpect(jsonPath("$[0].lastUpdateTime", is(time)));
    }

    @Test
    void findById() throws Exception {
        Mockito.when(certificateService.findCertificateById(1))
                .thenReturn(CertificateClientModelMapper.INSTANCE
                                .certificateToCertificateClientModel(certificateEntity));
        mockMvc.perform(get("/certificates/id/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.price", is(1)))
                .andExpect(jsonPath("$.name", is("1")))
                .andExpect(jsonPath("$.duration", is(1)))
                .andExpect(jsonPath("$.description", is("1")));
    }

    @Test
    void findByTagName() throws Exception {
        Mockito.when(certificateService.findByTagName("1", null))
                .thenReturn(Arrays.asList(CertificateClientModelMapper.INSTANCE
                        .certificateToCertificateClientModel(certificateEntity)));
        mockMvc.perform(get("/certificates/tag/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].price", is(1)))
                .andExpect(jsonPath("$[0].name", is("1")))
                .andExpect(jsonPath("$[0].duration", is(1)))
                .andExpect(jsonPath("$[0].description", is("1")));
    }

    @Test
    void findByName() throws Exception {
        Mockito.when(certificateService.findByName("1", null))
                .thenReturn(Arrays.asList(CertificateClientModelMapper.INSTANCE
                        .certificateToCertificateClientModel(certificateEntity)));
        mockMvc.perform(get("/certificates/name/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].price", is(1)))
                .andExpect(jsonPath("$[0].name", is("1")))
                .andExpect(jsonPath("$[0].duration", is(1)))
                .andExpect(jsonPath("$[0].description", is("1")));
    }

    @Test
    void delete() throws Exception {
        Mockito.when(certificateService.deleteById(1))
                .thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/certificates/id/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string("true"));
    }

    private LocalDateTime dateToLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}