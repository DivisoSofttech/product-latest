package com.diviso.graeshoppe.product.web.rest;

import com.diviso.graeshoppe.product.ProductApp;
import com.diviso.graeshoppe.product.config.TestSecurityConfiguration;
import com.diviso.graeshoppe.product.domain.ComboLineItem;
import com.diviso.graeshoppe.product.repository.ComboLineItemRepository;
import com.diviso.graeshoppe.product.repository.search.ComboLineItemSearchRepository;
import com.diviso.graeshoppe.product.service.ComboLineItemService;
import com.diviso.graeshoppe.product.service.dto.ComboLineItemDTO;
import com.diviso.graeshoppe.product.service.mapper.ComboLineItemMapper;
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
 * Integration tests for the {@link ComboLineItemResource} REST controller.
 */
@SpringBootTest(classes = {ProductApp.class, TestSecurityConfiguration.class})
public class ComboLineItemResourceIT {

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ComboLineItemRepository comboLineItemRepository;

    @Autowired
    private ComboLineItemMapper comboLineItemMapper;

    @Autowired
    private ComboLineItemService comboLineItemService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.product.repository.search test package.
     *
     * @see com.diviso.graeshoppe.product.repository.search.ComboLineItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private ComboLineItemSearchRepository mockComboLineItemSearchRepository;

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

    private MockMvc restComboLineItemMockMvc;

    private ComboLineItem comboLineItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComboLineItemResource comboLineItemResource = new ComboLineItemResource(comboLineItemService);
        this.restComboLineItemMockMvc = MockMvcBuilders.standaloneSetup(comboLineItemResource)
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
    public static ComboLineItem createEntity(EntityManager em) {
        ComboLineItem comboLineItem = new ComboLineItem()
            .quantity(DEFAULT_QUANTITY)
            .description(DEFAULT_DESCRIPTION);
        return comboLineItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComboLineItem createUpdatedEntity(EntityManager em) {
        ComboLineItem comboLineItem = new ComboLineItem()
            .quantity(UPDATED_QUANTITY)
            .description(UPDATED_DESCRIPTION);
        return comboLineItem;
    }

    @BeforeEach
    public void initTest() {
        comboLineItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createComboLineItem() throws Exception {
        int databaseSizeBeforeCreate = comboLineItemRepository.findAll().size();

        // Create the ComboLineItem
        ComboLineItemDTO comboLineItemDTO = comboLineItemMapper.toDto(comboLineItem);
        restComboLineItemMockMvc.perform(post("/api/combo-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comboLineItemDTO)))
            .andExpect(status().isCreated());

        // Validate the ComboLineItem in the database
        List<ComboLineItem> comboLineItemList = comboLineItemRepository.findAll();
        assertThat(comboLineItemList).hasSize(databaseSizeBeforeCreate + 1);
        ComboLineItem testComboLineItem = comboLineItemList.get(comboLineItemList.size() - 1);
        assertThat(testComboLineItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testComboLineItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the ComboLineItem in Elasticsearch
        verify(mockComboLineItemSearchRepository, times(1)).save(testComboLineItem);
    }

    @Test
    @Transactional
    public void createComboLineItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comboLineItemRepository.findAll().size();

        // Create the ComboLineItem with an existing ID
        comboLineItem.setId(1L);
        ComboLineItemDTO comboLineItemDTO = comboLineItemMapper.toDto(comboLineItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComboLineItemMockMvc.perform(post("/api/combo-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comboLineItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ComboLineItem in the database
        List<ComboLineItem> comboLineItemList = comboLineItemRepository.findAll();
        assertThat(comboLineItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the ComboLineItem in Elasticsearch
        verify(mockComboLineItemSearchRepository, times(0)).save(comboLineItem);
    }


    @Test
    @Transactional
    public void getAllComboLineItems() throws Exception {
        // Initialize the database
        comboLineItemRepository.saveAndFlush(comboLineItem);

        // Get all the comboLineItemList
        restComboLineItemMockMvc.perform(get("/api/combo-line-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comboLineItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getComboLineItem() throws Exception {
        // Initialize the database
        comboLineItemRepository.saveAndFlush(comboLineItem);

        // Get the comboLineItem
        restComboLineItemMockMvc.perform(get("/api/combo-line-items/{id}", comboLineItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comboLineItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingComboLineItem() throws Exception {
        // Get the comboLineItem
        restComboLineItemMockMvc.perform(get("/api/combo-line-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComboLineItem() throws Exception {
        // Initialize the database
        comboLineItemRepository.saveAndFlush(comboLineItem);

        int databaseSizeBeforeUpdate = comboLineItemRepository.findAll().size();

        // Update the comboLineItem
        ComboLineItem updatedComboLineItem = comboLineItemRepository.findById(comboLineItem.getId()).get();
        // Disconnect from session so that the updates on updatedComboLineItem are not directly saved in db
        em.detach(updatedComboLineItem);
        updatedComboLineItem
            .quantity(UPDATED_QUANTITY)
            .description(UPDATED_DESCRIPTION);
        ComboLineItemDTO comboLineItemDTO = comboLineItemMapper.toDto(updatedComboLineItem);

        restComboLineItemMockMvc.perform(put("/api/combo-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comboLineItemDTO)))
            .andExpect(status().isOk());

        // Validate the ComboLineItem in the database
        List<ComboLineItem> comboLineItemList = comboLineItemRepository.findAll();
        assertThat(comboLineItemList).hasSize(databaseSizeBeforeUpdate);
        ComboLineItem testComboLineItem = comboLineItemList.get(comboLineItemList.size() - 1);
        assertThat(testComboLineItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testComboLineItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ComboLineItem in Elasticsearch
        verify(mockComboLineItemSearchRepository, times(1)).save(testComboLineItem);
    }

    @Test
    @Transactional
    public void updateNonExistingComboLineItem() throws Exception {
        int databaseSizeBeforeUpdate = comboLineItemRepository.findAll().size();

        // Create the ComboLineItem
        ComboLineItemDTO comboLineItemDTO = comboLineItemMapper.toDto(comboLineItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComboLineItemMockMvc.perform(put("/api/combo-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comboLineItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ComboLineItem in the database
        List<ComboLineItem> comboLineItemList = comboLineItemRepository.findAll();
        assertThat(comboLineItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ComboLineItem in Elasticsearch
        verify(mockComboLineItemSearchRepository, times(0)).save(comboLineItem);
    }

    @Test
    @Transactional
    public void deleteComboLineItem() throws Exception {
        // Initialize the database
        comboLineItemRepository.saveAndFlush(comboLineItem);

        int databaseSizeBeforeDelete = comboLineItemRepository.findAll().size();

        // Delete the comboLineItem
        restComboLineItemMockMvc.perform(delete("/api/combo-line-items/{id}", comboLineItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ComboLineItem> comboLineItemList = comboLineItemRepository.findAll();
        assertThat(comboLineItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ComboLineItem in Elasticsearch
        verify(mockComboLineItemSearchRepository, times(1)).deleteById(comboLineItem.getId());
    }

    @Test
    @Transactional
    public void searchComboLineItem() throws Exception {
        // Initialize the database
        comboLineItemRepository.saveAndFlush(comboLineItem);
        when(mockComboLineItemSearchRepository.search(queryStringQuery("id:" + comboLineItem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(comboLineItem), PageRequest.of(0, 1), 1));
        // Search the comboLineItem
        restComboLineItemMockMvc.perform(get("/api/_search/combo-line-items?query=id:" + comboLineItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comboLineItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
