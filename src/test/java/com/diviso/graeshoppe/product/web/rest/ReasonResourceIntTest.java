package com.diviso.graeshoppe.product.web.rest;

import com.diviso.graeshoppe.product.ProductApp;

import com.diviso.graeshoppe.product.domain.Reason;
import com.diviso.graeshoppe.product.repository.ReasonRepository;
import com.diviso.graeshoppe.product.repository.search.ReasonSearchRepository;
import com.diviso.graeshoppe.product.service.ReasonService;
import com.diviso.graeshoppe.product.service.dto.ReasonDTO;
import com.diviso.graeshoppe.product.service.mapper.ReasonMapper;
import com.diviso.graeshoppe.product.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
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
 * Test class for the ReasonResource REST controller.
 *
 * @see ReasonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductApp.class)
public class ReasonResourceIntTest {

    private static final String DEFAULT_I_D_PCODE = "AAAAAAAAAA";
    private static final String UPDATED_I_D_PCODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ReasonRepository reasonRepository;

    @Autowired
    private ReasonMapper reasonMapper;

    @Autowired
    private ReasonService reasonService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.product.repository.search test package.
     *
     * @see com.diviso.graeshoppe.product.repository.search.ReasonSearchRepositoryMockConfiguration
     */
    @Autowired
    private ReasonSearchRepository mockReasonSearchRepository;

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

    private MockMvc restReasonMockMvc;

    private Reason reason;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReasonResource reasonResource = new ReasonResource(reasonService);
        this.restReasonMockMvc = MockMvcBuilders.standaloneSetup(reasonResource)
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
    public static Reason createEntity(EntityManager em) {
        Reason reason = new Reason()
            .iDPcode(DEFAULT_I_D_PCODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return reason;
    }

    @Before
    public void initTest() {
        reason = createEntity(em);
    }

    @Test
    @Transactional
    public void createReason() throws Exception {
        int databaseSizeBeforeCreate = reasonRepository.findAll().size();

        // Create the Reason
        ReasonDTO reasonDTO = reasonMapper.toDto(reason);
        restReasonMockMvc.perform(post("/api/reasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasonDTO)))
            .andExpect(status().isCreated());

        // Validate the Reason in the database
        List<Reason> reasonList = reasonRepository.findAll();
        assertThat(reasonList).hasSize(databaseSizeBeforeCreate + 1);
        Reason testReason = reasonList.get(reasonList.size() - 1);
        assertThat(testReason.getiDPcode()).isEqualTo(DEFAULT_I_D_PCODE);
        assertThat(testReason.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReason.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Reason in Elasticsearch
        verify(mockReasonSearchRepository, times(1)).save(testReason);
    }

    @Test
    @Transactional
    public void createReasonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reasonRepository.findAll().size();

        // Create the Reason with an existing ID
        reason.setId(1L);
        ReasonDTO reasonDTO = reasonMapper.toDto(reason);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReasonMockMvc.perform(post("/api/reasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reason in the database
        List<Reason> reasonList = reasonRepository.findAll();
        assertThat(reasonList).hasSize(databaseSizeBeforeCreate);

        // Validate the Reason in Elasticsearch
        verify(mockReasonSearchRepository, times(0)).save(reason);
    }

    @Test
    @Transactional
    public void getAllReasons() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get all the reasonList
        restReasonMockMvc.perform(get("/api/reasons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reason.getId().intValue())))
            .andExpect(jsonPath("$.[*].iDPcode").value(hasItem(DEFAULT_I_D_PCODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getReason() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get the reason
        restReasonMockMvc.perform(get("/api/reasons/{id}", reason.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reason.getId().intValue()))
            .andExpect(jsonPath("$.iDPcode").value(DEFAULT_I_D_PCODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReason() throws Exception {
        // Get the reason
        restReasonMockMvc.perform(get("/api/reasons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReason() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        int databaseSizeBeforeUpdate = reasonRepository.findAll().size();

        // Update the reason
        Reason updatedReason = reasonRepository.findById(reason.getId()).get();
        // Disconnect from session so that the updates on updatedReason are not directly saved in db
        em.detach(updatedReason);
        updatedReason
            .iDPcode(UPDATED_I_D_PCODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        ReasonDTO reasonDTO = reasonMapper.toDto(updatedReason);

        restReasonMockMvc.perform(put("/api/reasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasonDTO)))
            .andExpect(status().isOk());

        // Validate the Reason in the database
        List<Reason> reasonList = reasonRepository.findAll();
        assertThat(reasonList).hasSize(databaseSizeBeforeUpdate);
        Reason testReason = reasonList.get(reasonList.size() - 1);
        assertThat(testReason.getiDPcode()).isEqualTo(UPDATED_I_D_PCODE);
        assertThat(testReason.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReason.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Reason in Elasticsearch
        verify(mockReasonSearchRepository, times(1)).save(testReason);
    }

    @Test
    @Transactional
    public void updateNonExistingReason() throws Exception {
        int databaseSizeBeforeUpdate = reasonRepository.findAll().size();

        // Create the Reason
        ReasonDTO reasonDTO = reasonMapper.toDto(reason);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReasonMockMvc.perform(put("/api/reasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reason in the database
        List<Reason> reasonList = reasonRepository.findAll();
        assertThat(reasonList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Reason in Elasticsearch
        verify(mockReasonSearchRepository, times(0)).save(reason);
    }

    @Test
    @Transactional
    public void deleteReason() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        int databaseSizeBeforeDelete = reasonRepository.findAll().size();

        // Delete the reason
        restReasonMockMvc.perform(delete("/api/reasons/{id}", reason.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Reason> reasonList = reasonRepository.findAll();
        assertThat(reasonList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Reason in Elasticsearch
        verify(mockReasonSearchRepository, times(1)).deleteById(reason.getId());
    }

    @Test
    @Transactional
    public void searchReason() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);
        when(mockReasonSearchRepository.search(queryStringQuery("id:" + reason.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(reason), PageRequest.of(0, 1), 1));
        // Search the reason
        restReasonMockMvc.perform(get("/api/_search/reasons?query=id:" + reason.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reason.getId().intValue())))
            .andExpect(jsonPath("$.[*].iDPcode").value(hasItem(DEFAULT_I_D_PCODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reason.class);
        Reason reason1 = new Reason();
        reason1.setId(1L);
        Reason reason2 = new Reason();
        reason2.setId(reason1.getId());
        assertThat(reason1).isEqualTo(reason2);
        reason2.setId(2L);
        assertThat(reason1).isNotEqualTo(reason2);
        reason1.setId(null);
        assertThat(reason1).isNotEqualTo(reason2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReasonDTO.class);
        ReasonDTO reasonDTO1 = new ReasonDTO();
        reasonDTO1.setId(1L);
        ReasonDTO reasonDTO2 = new ReasonDTO();
        assertThat(reasonDTO1).isNotEqualTo(reasonDTO2);
        reasonDTO2.setId(reasonDTO1.getId());
        assertThat(reasonDTO1).isEqualTo(reasonDTO2);
        reasonDTO2.setId(2L);
        assertThat(reasonDTO1).isNotEqualTo(reasonDTO2);
        reasonDTO1.setId(null);
        assertThat(reasonDTO1).isNotEqualTo(reasonDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reasonMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reasonMapper.fromId(null)).isNull();
    }
}
