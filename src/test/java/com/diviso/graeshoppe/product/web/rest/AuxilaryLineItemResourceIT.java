package com.diviso.graeshoppe.product.web.rest;

import com.diviso.graeshoppe.product.ProductApp;
import com.diviso.graeshoppe.product.config.TestSecurityConfiguration;
import com.diviso.graeshoppe.product.domain.AuxilaryLineItem;
import com.diviso.graeshoppe.product.repository.AuxilaryLineItemRepository;
import com.diviso.graeshoppe.product.repository.search.AuxilaryLineItemSearchRepository;
import com.diviso.graeshoppe.product.service.AuxilaryLineItemService;
import com.diviso.graeshoppe.product.service.dto.AuxilaryLineItemDTO;
import com.diviso.graeshoppe.product.service.mapper.AuxilaryLineItemMapper;
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
 * Integration tests for the {@link AuxilaryLineItemResource} REST controller.
 */
@SpringBootTest(classes = {ProductApp.class, TestSecurityConfiguration.class})
public class AuxilaryLineItemResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    @Autowired
    private AuxilaryLineItemRepository auxilaryLineItemRepository;

    @Autowired
    private AuxilaryLineItemMapper auxilaryLineItemMapper;

    @Autowired
    private AuxilaryLineItemService auxilaryLineItemService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.product.repository.search test package.
     *
     * @see com.diviso.graeshoppe.product.repository.search.AuxilaryLineItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private AuxilaryLineItemSearchRepository mockAuxilaryLineItemSearchRepository;

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

    private MockMvc restAuxilaryLineItemMockMvc;

    private AuxilaryLineItem auxilaryLineItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuxilaryLineItemResource auxilaryLineItemResource = new AuxilaryLineItemResource(auxilaryLineItemService);
        this.restAuxilaryLineItemMockMvc = MockMvcBuilders.standaloneSetup(auxilaryLineItemResource)
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
    public static AuxilaryLineItem createEntity(EntityManager em) {
        AuxilaryLineItem auxilaryLineItem = new AuxilaryLineItem()
            .description(DEFAULT_DESCRIPTION)
            .quantity(DEFAULT_QUANTITY);
        return auxilaryLineItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuxilaryLineItem createUpdatedEntity(EntityManager em) {
        AuxilaryLineItem auxilaryLineItem = new AuxilaryLineItem()
            .description(UPDATED_DESCRIPTION)
            .quantity(UPDATED_QUANTITY);
        return auxilaryLineItem;
    }

    @BeforeEach
    public void initTest() {
        auxilaryLineItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuxilaryLineItem() throws Exception {
        int databaseSizeBeforeCreate = auxilaryLineItemRepository.findAll().size();

        // Create the AuxilaryLineItem
        AuxilaryLineItemDTO auxilaryLineItemDTO = auxilaryLineItemMapper.toDto(auxilaryLineItem);
        restAuxilaryLineItemMockMvc.perform(post("/api/auxilary-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auxilaryLineItemDTO)))
            .andExpect(status().isCreated());

        // Validate the AuxilaryLineItem in the database
        List<AuxilaryLineItem> auxilaryLineItemList = auxilaryLineItemRepository.findAll();
        assertThat(auxilaryLineItemList).hasSize(databaseSizeBeforeCreate + 1);
        AuxilaryLineItem testAuxilaryLineItem = auxilaryLineItemList.get(auxilaryLineItemList.size() - 1);
        assertThat(testAuxilaryLineItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAuxilaryLineItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);

        // Validate the AuxilaryLineItem in Elasticsearch
        verify(mockAuxilaryLineItemSearchRepository, times(1)).save(testAuxilaryLineItem);
    }

    @Test
    @Transactional
    public void createAuxilaryLineItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auxilaryLineItemRepository.findAll().size();

        // Create the AuxilaryLineItem with an existing ID
        auxilaryLineItem.setId(1L);
        AuxilaryLineItemDTO auxilaryLineItemDTO = auxilaryLineItemMapper.toDto(auxilaryLineItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuxilaryLineItemMockMvc.perform(post("/api/auxilary-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auxilaryLineItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AuxilaryLineItem in the database
        List<AuxilaryLineItem> auxilaryLineItemList = auxilaryLineItemRepository.findAll();
        assertThat(auxilaryLineItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the AuxilaryLineItem in Elasticsearch
        verify(mockAuxilaryLineItemSearchRepository, times(0)).save(auxilaryLineItem);
    }


    @Test
    @Transactional
    public void getAllAuxilaryLineItems() throws Exception {
        // Initialize the database
        auxilaryLineItemRepository.saveAndFlush(auxilaryLineItem);

        // Get all the auxilaryLineItemList
        restAuxilaryLineItemMockMvc.perform(get("/api/auxilary-line-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auxilaryLineItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getAuxilaryLineItem() throws Exception {
        // Initialize the database
        auxilaryLineItemRepository.saveAndFlush(auxilaryLineItem);

        // Get the auxilaryLineItem
        restAuxilaryLineItemMockMvc.perform(get("/api/auxilary-line-items/{id}", auxilaryLineItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auxilaryLineItem.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAuxilaryLineItem() throws Exception {
        // Get the auxilaryLineItem
        restAuxilaryLineItemMockMvc.perform(get("/api/auxilary-line-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuxilaryLineItem() throws Exception {
        // Initialize the database
        auxilaryLineItemRepository.saveAndFlush(auxilaryLineItem);

        int databaseSizeBeforeUpdate = auxilaryLineItemRepository.findAll().size();

        // Update the auxilaryLineItem
        AuxilaryLineItem updatedAuxilaryLineItem = auxilaryLineItemRepository.findById(auxilaryLineItem.getId()).get();
        // Disconnect from session so that the updates on updatedAuxilaryLineItem are not directly saved in db
        em.detach(updatedAuxilaryLineItem);
        updatedAuxilaryLineItem
            .description(UPDATED_DESCRIPTION)
            .quantity(UPDATED_QUANTITY);
        AuxilaryLineItemDTO auxilaryLineItemDTO = auxilaryLineItemMapper.toDto(updatedAuxilaryLineItem);

        restAuxilaryLineItemMockMvc.perform(put("/api/auxilary-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auxilaryLineItemDTO)))
            .andExpect(status().isOk());

        // Validate the AuxilaryLineItem in the database
        List<AuxilaryLineItem> auxilaryLineItemList = auxilaryLineItemRepository.findAll();
        assertThat(auxilaryLineItemList).hasSize(databaseSizeBeforeUpdate);
        AuxilaryLineItem testAuxilaryLineItem = auxilaryLineItemList.get(auxilaryLineItemList.size() - 1);
        assertThat(testAuxilaryLineItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAuxilaryLineItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);

        // Validate the AuxilaryLineItem in Elasticsearch
        verify(mockAuxilaryLineItemSearchRepository, times(1)).save(testAuxilaryLineItem);
    }

    @Test
    @Transactional
    public void updateNonExistingAuxilaryLineItem() throws Exception {
        int databaseSizeBeforeUpdate = auxilaryLineItemRepository.findAll().size();

        // Create the AuxilaryLineItem
        AuxilaryLineItemDTO auxilaryLineItemDTO = auxilaryLineItemMapper.toDto(auxilaryLineItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuxilaryLineItemMockMvc.perform(put("/api/auxilary-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auxilaryLineItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AuxilaryLineItem in the database
        List<AuxilaryLineItem> auxilaryLineItemList = auxilaryLineItemRepository.findAll();
        assertThat(auxilaryLineItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AuxilaryLineItem in Elasticsearch
        verify(mockAuxilaryLineItemSearchRepository, times(0)).save(auxilaryLineItem);
    }

    @Test
    @Transactional
    public void deleteAuxilaryLineItem() throws Exception {
        // Initialize the database
        auxilaryLineItemRepository.saveAndFlush(auxilaryLineItem);

        int databaseSizeBeforeDelete = auxilaryLineItemRepository.findAll().size();

        // Delete the auxilaryLineItem
        restAuxilaryLineItemMockMvc.perform(delete("/api/auxilary-line-items/{id}", auxilaryLineItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AuxilaryLineItem> auxilaryLineItemList = auxilaryLineItemRepository.findAll();
        assertThat(auxilaryLineItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AuxilaryLineItem in Elasticsearch
        verify(mockAuxilaryLineItemSearchRepository, times(1)).deleteById(auxilaryLineItem.getId());
    }

    @Test
    @Transactional
    public void searchAuxilaryLineItem() throws Exception {
        // Initialize the database
        auxilaryLineItemRepository.saveAndFlush(auxilaryLineItem);
        when(mockAuxilaryLineItemSearchRepository.search(queryStringQuery("id:" + auxilaryLineItem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(auxilaryLineItem), PageRequest.of(0, 1), 1));
        // Search the auxilaryLineItem
        restAuxilaryLineItemMockMvc.perform(get("/api/_search/auxilary-line-items?query=id:" + auxilaryLineItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auxilaryLineItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));
    }
}
