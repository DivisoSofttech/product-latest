package com.diviso.graeshoppe.product.web.rest;

import com.diviso.graeshoppe.product.ProductApp;
import com.diviso.graeshoppe.product.config.TestSecurityConfiguration;
import com.diviso.graeshoppe.product.domain.UOM;
import com.diviso.graeshoppe.product.repository.UOMRepository;
import com.diviso.graeshoppe.product.repository.search.UOMSearchRepository;
import com.diviso.graeshoppe.product.service.UOMService;
import com.diviso.graeshoppe.product.service.dto.UOMDTO;
import com.diviso.graeshoppe.product.service.mapper.UOMMapper;
import com.diviso.graeshoppe.product.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.diviso.graeshoppe.product.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UOMResource} REST controller.
 */
@SpringBootTest(classes = {ProductApp.class, TestSecurityConfiguration.class})
public class UOMResourceIT {

    private static final String DEFAULT_I_D_PCODE = "AAAAAAAAAA";
    private static final String UPDATED_I_D_PCODE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private UOMRepository uOMRepository;

    @Autowired
    private UOMMapper uOMMapper;

    @Autowired
    private UOMService uOMService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.product.repository.search test package.
     *
     * @see com.diviso.graeshoppe.product.repository.search.UOMSearchRepositoryMockConfiguration
     */
    @Autowired
    private UOMSearchRepository mockUOMSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restUOMMockMvc;

    private UOM uOM;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UOMResource uOMResource = new UOMResource(uOMService);
        this.restUOMMockMvc = MockMvcBuilders.standaloneSetup(uOMResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UOM createEntity(EntityManager em) {
        UOM uOM = new UOM()
            .iDPcode(DEFAULT_I_D_PCODE)
            .unit(DEFAULT_UNIT)
            .description(DEFAULT_DESCRIPTION);
        return uOM;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UOM createUpdatedEntity(EntityManager em) {
        UOM uOM = new UOM()
            .iDPcode(UPDATED_I_D_PCODE)
            .unit(UPDATED_UNIT)
            .description(UPDATED_DESCRIPTION);
        return uOM;
    }

    @BeforeEach
    public void initTest() {
        uOM = createEntity(em);
    }

    @Test
    @Transactional
    public void createUOM() throws Exception {
        int databaseSizeBeforeCreate = uOMRepository.findAll().size();

        // Create the UOM
        UOMDTO uOMDTO = uOMMapper.toDto(uOM);
        restUOMMockMvc.perform(post("/api/uoms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uOMDTO)))
            .andExpect(status().isCreated());

        // Validate the UOM in the database
        List<UOM> uOMList = uOMRepository.findAll();
        assertThat(uOMList).hasSize(databaseSizeBeforeCreate + 1);
        UOM testUOM = uOMList.get(uOMList.size() - 1);
        assertThat(testUOM.getiDPcode()).isEqualTo(DEFAULT_I_D_PCODE);
        assertThat(testUOM.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testUOM.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the UOM in Elasticsearch
        verify(mockUOMSearchRepository, times(1)).save(testUOM);
    }

    @Test
    @Transactional
    public void createUOMWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uOMRepository.findAll().size();

        // Create the UOM with an existing ID
        uOM.setId(1L);
        UOMDTO uOMDTO = uOMMapper.toDto(uOM);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUOMMockMvc.perform(post("/api/uoms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uOMDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UOM in the database
        List<UOM> uOMList = uOMRepository.findAll();
        assertThat(uOMList).hasSize(databaseSizeBeforeCreate);

        // Validate the UOM in Elasticsearch
        verify(mockUOMSearchRepository, times(0)).save(uOM);
    }


    @Test
    @Transactional
    public void getAllUOMS() throws Exception {
        // Initialize the database
        uOMRepository.saveAndFlush(uOM);

        // Get all the uOMList
        restUOMMockMvc.perform(get("/api/uoms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uOM.getId().intValue())))
            .andExpect(jsonPath("$.[*].iDPcode").value(hasItem(DEFAULT_I_D_PCODE)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getUOM() throws Exception {
        // Initialize the database
        uOMRepository.saveAndFlush(uOM);

        // Get the uOM
        restUOMMockMvc.perform(get("/api/uoms/{id}", uOM.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uOM.getId().intValue()))
            .andExpect(jsonPath("$.iDPcode").value(DEFAULT_I_D_PCODE))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingUOM() throws Exception {
        // Get the uOM
        restUOMMockMvc.perform(get("/api/uoms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUOM() throws Exception {
        // Initialize the database
        uOMRepository.saveAndFlush(uOM);

        int databaseSizeBeforeUpdate = uOMRepository.findAll().size();

        // Update the uOM
        UOM updatedUOM = uOMRepository.findById(uOM.getId()).get();
        // Disconnect from session so that the updates on updatedUOM are not directly saved in db
        em.detach(updatedUOM);
        updatedUOM
            .iDPcode(UPDATED_I_D_PCODE)
            .unit(UPDATED_UNIT)
            .description(UPDATED_DESCRIPTION);
        UOMDTO uOMDTO = uOMMapper.toDto(updatedUOM);

        restUOMMockMvc.perform(put("/api/uoms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uOMDTO)))
            .andExpect(status().isOk());

        // Validate the UOM in the database
        List<UOM> uOMList = uOMRepository.findAll();
        assertThat(uOMList).hasSize(databaseSizeBeforeUpdate);
        UOM testUOM = uOMList.get(uOMList.size() - 1);
        assertThat(testUOM.getiDPcode()).isEqualTo(UPDATED_I_D_PCODE);
        assertThat(testUOM.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testUOM.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the UOM in Elasticsearch
        verify(mockUOMSearchRepository, times(1)).save(testUOM);
    }

    @Test
    @Transactional
    public void updateNonExistingUOM() throws Exception {
        int databaseSizeBeforeUpdate = uOMRepository.findAll().size();

        // Create the UOM
        UOMDTO uOMDTO = uOMMapper.toDto(uOM);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUOMMockMvc.perform(put("/api/uoms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uOMDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UOM in the database
        List<UOM> uOMList = uOMRepository.findAll();
        assertThat(uOMList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UOM in Elasticsearch
        verify(mockUOMSearchRepository, times(0)).save(uOM);
    }

    @Test
    @Transactional
    public void deleteUOM() throws Exception {
        // Initialize the database
        uOMRepository.saveAndFlush(uOM);

        int databaseSizeBeforeDelete = uOMRepository.findAll().size();

        // Delete the uOM
        restUOMMockMvc.perform(delete("/api/uoms/{id}", uOM.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UOM> uOMList = uOMRepository.findAll();
        assertThat(uOMList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UOM in Elasticsearch
        verify(mockUOMSearchRepository, times(1)).deleteById(uOM.getId());
    }

    @Test
    @Transactional
    public void searchUOM() throws Exception {
        // Initialize the database
        uOMRepository.saveAndFlush(uOM);
        when(mockUOMSearchRepository.search(queryStringQuery("id:" + uOM.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(uOM), PageRequest.of(0, 1), 1));
        // Search the uOM
        restUOMMockMvc.perform(get("/api/_search/uoms?query=id:" + uOM.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uOM.getId().intValue())))
            .andExpect(jsonPath("$.[*].iDPcode").value(hasItem(DEFAULT_I_D_PCODE)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
