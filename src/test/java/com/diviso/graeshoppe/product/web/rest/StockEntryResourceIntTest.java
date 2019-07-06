package com.diviso.graeshoppe.product.web.rest;

import com.diviso.graeshoppe.product.ProductApp;

import com.diviso.graeshoppe.product.domain.StockEntry;
import com.diviso.graeshoppe.product.repository.StockEntryRepository;
import com.diviso.graeshoppe.product.repository.search.StockEntrySearchRepository;
import com.diviso.graeshoppe.product.service.StockEntryService;
import com.diviso.graeshoppe.product.service.dto.StockEntryDTO;
import com.diviso.graeshoppe.product.service.mapper.StockEntryMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Test class for the StockEntryResource REST controller.
 *
 * @see StockEntryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductApp.class)
public class StockEntryResourceIntTest {

    private static final String DEFAULT_I_D_PCODE = "AAAAAAAAAA";
    private static final String UPDATED_I_D_PCODE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private StockEntryRepository stockEntryRepository;

    @Autowired
    private StockEntryMapper stockEntryMapper;

    @Autowired
    private StockEntryService stockEntryService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.product.repository.search test package.
     *
     * @see com.diviso.graeshoppe.product.repository.search.StockEntrySearchRepositoryMockConfiguration
     */
    @Autowired
    private StockEntrySearchRepository mockStockEntrySearchRepository;

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

    private MockMvc restStockEntryMockMvc;

    private StockEntry stockEntry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockEntryResource stockEntryResource = new StockEntryResource(stockEntryService);
        this.restStockEntryMockMvc = MockMvcBuilders.standaloneSetup(stockEntryResource)
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
    public static StockEntry createEntity(EntityManager em) {
        StockEntry stockEntry = new StockEntry()
            .iDPcode(DEFAULT_I_D_PCODE)
            .reference(DEFAULT_REFERENCE)
            .date(DEFAULT_DATE)
            .description(DEFAULT_DESCRIPTION);
        return stockEntry;
    }

    @Before
    public void initTest() {
        stockEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockEntry() throws Exception {
        int databaseSizeBeforeCreate = stockEntryRepository.findAll().size();

        // Create the StockEntry
        StockEntryDTO stockEntryDTO = stockEntryMapper.toDto(stockEntry);
        restStockEntryMockMvc.perform(post("/api/stock-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockEntryDTO)))
            .andExpect(status().isCreated());

        // Validate the StockEntry in the database
        List<StockEntry> stockEntryList = stockEntryRepository.findAll();
        assertThat(stockEntryList).hasSize(databaseSizeBeforeCreate + 1);
        StockEntry testStockEntry = stockEntryList.get(stockEntryList.size() - 1);
        assertThat(testStockEntry.getiDPcode()).isEqualTo(DEFAULT_I_D_PCODE);
        assertThat(testStockEntry.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testStockEntry.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testStockEntry.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the StockEntry in Elasticsearch
        verify(mockStockEntrySearchRepository, times(1)).save(testStockEntry);
    }

    @Test
    @Transactional
    public void createStockEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockEntryRepository.findAll().size();

        // Create the StockEntry with an existing ID
        stockEntry.setId(1L);
        StockEntryDTO stockEntryDTO = stockEntryMapper.toDto(stockEntry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockEntryMockMvc.perform(post("/api/stock-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockEntryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockEntry in the database
        List<StockEntry> stockEntryList = stockEntryRepository.findAll();
        assertThat(stockEntryList).hasSize(databaseSizeBeforeCreate);

        // Validate the StockEntry in Elasticsearch
        verify(mockStockEntrySearchRepository, times(0)).save(stockEntry);
    }

    @Test
    @Transactional
    public void getAllStockEntries() throws Exception {
        // Initialize the database
        stockEntryRepository.saveAndFlush(stockEntry);

        // Get all the stockEntryList
        restStockEntryMockMvc.perform(get("/api/stock-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].iDPcode").value(hasItem(DEFAULT_I_D_PCODE.toString())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getStockEntry() throws Exception {
        // Initialize the database
        stockEntryRepository.saveAndFlush(stockEntry);

        // Get the stockEntry
        restStockEntryMockMvc.perform(get("/api/stock-entries/{id}", stockEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockEntry.getId().intValue()))
            .andExpect(jsonPath("$.iDPcode").value(DEFAULT_I_D_PCODE.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStockEntry() throws Exception {
        // Get the stockEntry
        restStockEntryMockMvc.perform(get("/api/stock-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockEntry() throws Exception {
        // Initialize the database
        stockEntryRepository.saveAndFlush(stockEntry);

        int databaseSizeBeforeUpdate = stockEntryRepository.findAll().size();

        // Update the stockEntry
        StockEntry updatedStockEntry = stockEntryRepository.findById(stockEntry.getId()).get();
        // Disconnect from session so that the updates on updatedStockEntry are not directly saved in db
        em.detach(updatedStockEntry);
        updatedStockEntry
            .iDPcode(UPDATED_I_D_PCODE)
            .reference(UPDATED_REFERENCE)
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION);
        StockEntryDTO stockEntryDTO = stockEntryMapper.toDto(updatedStockEntry);

        restStockEntryMockMvc.perform(put("/api/stock-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockEntryDTO)))
            .andExpect(status().isOk());

        // Validate the StockEntry in the database
        List<StockEntry> stockEntryList = stockEntryRepository.findAll();
        assertThat(stockEntryList).hasSize(databaseSizeBeforeUpdate);
        StockEntry testStockEntry = stockEntryList.get(stockEntryList.size() - 1);
        assertThat(testStockEntry.getiDPcode()).isEqualTo(UPDATED_I_D_PCODE);
        assertThat(testStockEntry.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testStockEntry.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testStockEntry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the StockEntry in Elasticsearch
        verify(mockStockEntrySearchRepository, times(1)).save(testStockEntry);
    }

    @Test
    @Transactional
    public void updateNonExistingStockEntry() throws Exception {
        int databaseSizeBeforeUpdate = stockEntryRepository.findAll().size();

        // Create the StockEntry
        StockEntryDTO stockEntryDTO = stockEntryMapper.toDto(stockEntry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockEntryMockMvc.perform(put("/api/stock-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockEntryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockEntry in the database
        List<StockEntry> stockEntryList = stockEntryRepository.findAll();
        assertThat(stockEntryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StockEntry in Elasticsearch
        verify(mockStockEntrySearchRepository, times(0)).save(stockEntry);
    }

    @Test
    @Transactional
    public void deleteStockEntry() throws Exception {
        // Initialize the database
        stockEntryRepository.saveAndFlush(stockEntry);

        int databaseSizeBeforeDelete = stockEntryRepository.findAll().size();

        // Delete the stockEntry
        restStockEntryMockMvc.perform(delete("/api/stock-entries/{id}", stockEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StockEntry> stockEntryList = stockEntryRepository.findAll();
        assertThat(stockEntryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StockEntry in Elasticsearch
        verify(mockStockEntrySearchRepository, times(1)).deleteById(stockEntry.getId());
    }

    @Test
    @Transactional
    public void searchStockEntry() throws Exception {
        // Initialize the database
        stockEntryRepository.saveAndFlush(stockEntry);
        when(mockStockEntrySearchRepository.search(queryStringQuery("id:" + stockEntry.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(stockEntry), PageRequest.of(0, 1), 1));
        // Search the stockEntry
        restStockEntryMockMvc.perform(get("/api/_search/stock-entries?query=id:" + stockEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].iDPcode").value(hasItem(DEFAULT_I_D_PCODE)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockEntry.class);
        StockEntry stockEntry1 = new StockEntry();
        stockEntry1.setId(1L);
        StockEntry stockEntry2 = new StockEntry();
        stockEntry2.setId(stockEntry1.getId());
        assertThat(stockEntry1).isEqualTo(stockEntry2);
        stockEntry2.setId(2L);
        assertThat(stockEntry1).isNotEqualTo(stockEntry2);
        stockEntry1.setId(null);
        assertThat(stockEntry1).isNotEqualTo(stockEntry2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockEntryDTO.class);
        StockEntryDTO stockEntryDTO1 = new StockEntryDTO();
        stockEntryDTO1.setId(1L);
        StockEntryDTO stockEntryDTO2 = new StockEntryDTO();
        assertThat(stockEntryDTO1).isNotEqualTo(stockEntryDTO2);
        stockEntryDTO2.setId(stockEntryDTO1.getId());
        assertThat(stockEntryDTO1).isEqualTo(stockEntryDTO2);
        stockEntryDTO2.setId(2L);
        assertThat(stockEntryDTO1).isNotEqualTo(stockEntryDTO2);
        stockEntryDTO1.setId(null);
        assertThat(stockEntryDTO1).isNotEqualTo(stockEntryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stockEntryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stockEntryMapper.fromId(null)).isNull();
    }
}
