package com.diviso.graeshoppe.product.web.rest;

import com.diviso.graeshoppe.product.ProductApp;
import com.diviso.graeshoppe.product.config.TestSecurityConfiguration;
import com.diviso.graeshoppe.product.domain.EntryLineItem;
import com.diviso.graeshoppe.product.repository.EntryLineItemRepository;
import com.diviso.graeshoppe.product.repository.search.EntryLineItemSearchRepository;
import com.diviso.graeshoppe.product.service.EntryLineItemService;
import com.diviso.graeshoppe.product.service.dto.EntryLineItemDTO;
import com.diviso.graeshoppe.product.service.mapper.EntryLineItemMapper;
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
 * Integration tests for the {@link EntryLineItemResource} REST controller.
 */
@SpringBootTest(classes = {ProductApp.class, TestSecurityConfiguration.class})
public class EntryLineItemResourceIT {

    private static final Double DEFAULT_QUANTITY_ADJUSTMENT = 1D;
    private static final Double UPDATED_QUANTITY_ADJUSTMENT = 2D;

    private static final Double DEFAULT_VALUE_ADJUSTMENT = 1D;
    private static final Double UPDATED_VALUE_ADJUSTMENT = 2D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private EntryLineItemRepository entryLineItemRepository;

    @Autowired
    private EntryLineItemMapper entryLineItemMapper;

    @Autowired
    private EntryLineItemService entryLineItemService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.product.repository.search test package.
     *
     * @see com.diviso.graeshoppe.product.repository.search.EntryLineItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private EntryLineItemSearchRepository mockEntryLineItemSearchRepository;

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

    private MockMvc restEntryLineItemMockMvc;

    private EntryLineItem entryLineItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntryLineItemResource entryLineItemResource = new EntryLineItemResource(entryLineItemService);
        this.restEntryLineItemMockMvc = MockMvcBuilders.standaloneSetup(entryLineItemResource)
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
    public static EntryLineItem createEntity(EntityManager em) {
        EntryLineItem entryLineItem = new EntryLineItem()
            .quantityAdjustment(DEFAULT_QUANTITY_ADJUSTMENT)
            .valueAdjustment(DEFAULT_VALUE_ADJUSTMENT)
            .description(DEFAULT_DESCRIPTION);
        return entryLineItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntryLineItem createUpdatedEntity(EntityManager em) {
        EntryLineItem entryLineItem = new EntryLineItem()
            .quantityAdjustment(UPDATED_QUANTITY_ADJUSTMENT)
            .valueAdjustment(UPDATED_VALUE_ADJUSTMENT)
            .description(UPDATED_DESCRIPTION);
        return entryLineItem;
    }

    @BeforeEach
    public void initTest() {
        entryLineItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntryLineItem() throws Exception {
        int databaseSizeBeforeCreate = entryLineItemRepository.findAll().size();

        // Create the EntryLineItem
        EntryLineItemDTO entryLineItemDTO = entryLineItemMapper.toDto(entryLineItem);
        restEntryLineItemMockMvc.perform(post("/api/entry-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entryLineItemDTO)))
            .andExpect(status().isCreated());

        // Validate the EntryLineItem in the database
        List<EntryLineItem> entryLineItemList = entryLineItemRepository.findAll();
        assertThat(entryLineItemList).hasSize(databaseSizeBeforeCreate + 1);
        EntryLineItem testEntryLineItem = entryLineItemList.get(entryLineItemList.size() - 1);
        assertThat(testEntryLineItem.getQuantityAdjustment()).isEqualTo(DEFAULT_QUANTITY_ADJUSTMENT);
        assertThat(testEntryLineItem.getValueAdjustment()).isEqualTo(DEFAULT_VALUE_ADJUSTMENT);
        assertThat(testEntryLineItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the EntryLineItem in Elasticsearch
        verify(mockEntryLineItemSearchRepository, times(1)).save(testEntryLineItem);
    }

    @Test
    @Transactional
    public void createEntryLineItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entryLineItemRepository.findAll().size();

        // Create the EntryLineItem with an existing ID
        entryLineItem.setId(1L);
        EntryLineItemDTO entryLineItemDTO = entryLineItemMapper.toDto(entryLineItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntryLineItemMockMvc.perform(post("/api/entry-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entryLineItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EntryLineItem in the database
        List<EntryLineItem> entryLineItemList = entryLineItemRepository.findAll();
        assertThat(entryLineItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the EntryLineItem in Elasticsearch
        verify(mockEntryLineItemSearchRepository, times(0)).save(entryLineItem);
    }


    @Test
    @Transactional
    public void getAllEntryLineItems() throws Exception {
        // Initialize the database
        entryLineItemRepository.saveAndFlush(entryLineItem);

        // Get all the entryLineItemList
        restEntryLineItemMockMvc.perform(get("/api/entry-line-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entryLineItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantityAdjustment").value(hasItem(DEFAULT_QUANTITY_ADJUSTMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].valueAdjustment").value(hasItem(DEFAULT_VALUE_ADJUSTMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getEntryLineItem() throws Exception {
        // Initialize the database
        entryLineItemRepository.saveAndFlush(entryLineItem);

        // Get the entryLineItem
        restEntryLineItemMockMvc.perform(get("/api/entry-line-items/{id}", entryLineItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entryLineItem.getId().intValue()))
            .andExpect(jsonPath("$.quantityAdjustment").value(DEFAULT_QUANTITY_ADJUSTMENT.doubleValue()))
            .andExpect(jsonPath("$.valueAdjustment").value(DEFAULT_VALUE_ADJUSTMENT.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingEntryLineItem() throws Exception {
        // Get the entryLineItem
        restEntryLineItemMockMvc.perform(get("/api/entry-line-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntryLineItem() throws Exception {
        // Initialize the database
        entryLineItemRepository.saveAndFlush(entryLineItem);

        int databaseSizeBeforeUpdate = entryLineItemRepository.findAll().size();

        // Update the entryLineItem
        EntryLineItem updatedEntryLineItem = entryLineItemRepository.findById(entryLineItem.getId()).get();
        // Disconnect from session so that the updates on updatedEntryLineItem are not directly saved in db
        em.detach(updatedEntryLineItem);
        updatedEntryLineItem
            .quantityAdjustment(UPDATED_QUANTITY_ADJUSTMENT)
            .valueAdjustment(UPDATED_VALUE_ADJUSTMENT)
            .description(UPDATED_DESCRIPTION);
        EntryLineItemDTO entryLineItemDTO = entryLineItemMapper.toDto(updatedEntryLineItem);

        restEntryLineItemMockMvc.perform(put("/api/entry-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entryLineItemDTO)))
            .andExpect(status().isOk());

        // Validate the EntryLineItem in the database
        List<EntryLineItem> entryLineItemList = entryLineItemRepository.findAll();
        assertThat(entryLineItemList).hasSize(databaseSizeBeforeUpdate);
        EntryLineItem testEntryLineItem = entryLineItemList.get(entryLineItemList.size() - 1);
        assertThat(testEntryLineItem.getQuantityAdjustment()).isEqualTo(UPDATED_QUANTITY_ADJUSTMENT);
        assertThat(testEntryLineItem.getValueAdjustment()).isEqualTo(UPDATED_VALUE_ADJUSTMENT);
        assertThat(testEntryLineItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the EntryLineItem in Elasticsearch
        verify(mockEntryLineItemSearchRepository, times(1)).save(testEntryLineItem);
    }

    @Test
    @Transactional
    public void updateNonExistingEntryLineItem() throws Exception {
        int databaseSizeBeforeUpdate = entryLineItemRepository.findAll().size();

        // Create the EntryLineItem
        EntryLineItemDTO entryLineItemDTO = entryLineItemMapper.toDto(entryLineItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntryLineItemMockMvc.perform(put("/api/entry-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entryLineItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EntryLineItem in the database
        List<EntryLineItem> entryLineItemList = entryLineItemRepository.findAll();
        assertThat(entryLineItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EntryLineItem in Elasticsearch
        verify(mockEntryLineItemSearchRepository, times(0)).save(entryLineItem);
    }

    @Test
    @Transactional
    public void deleteEntryLineItem() throws Exception {
        // Initialize the database
        entryLineItemRepository.saveAndFlush(entryLineItem);

        int databaseSizeBeforeDelete = entryLineItemRepository.findAll().size();

        // Delete the entryLineItem
        restEntryLineItemMockMvc.perform(delete("/api/entry-line-items/{id}", entryLineItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EntryLineItem> entryLineItemList = entryLineItemRepository.findAll();
        assertThat(entryLineItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EntryLineItem in Elasticsearch
        verify(mockEntryLineItemSearchRepository, times(1)).deleteById(entryLineItem.getId());
    }

    @Test
    @Transactional
    public void searchEntryLineItem() throws Exception {
        // Initialize the database
        entryLineItemRepository.saveAndFlush(entryLineItem);
        when(mockEntryLineItemSearchRepository.search(queryStringQuery("id:" + entryLineItem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(entryLineItem), PageRequest.of(0, 1), 1));
        // Search the entryLineItem
        restEntryLineItemMockMvc.perform(get("/api/_search/entry-line-items?query=id:" + entryLineItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entryLineItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantityAdjustment").value(hasItem(DEFAULT_QUANTITY_ADJUSTMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].valueAdjustment").value(hasItem(DEFAULT_VALUE_ADJUSTMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
