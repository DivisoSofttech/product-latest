package com.diviso.graeshoppe.product.web.rest;

import com.diviso.graeshoppe.product.ProductApp;
import com.diviso.graeshoppe.product.config.TestSecurityConfiguration;
import com.diviso.graeshoppe.product.domain.StockCurrent;
import com.diviso.graeshoppe.product.repository.StockCurrentRepository;
import com.diviso.graeshoppe.product.repository.search.StockCurrentSearchRepository;
import com.diviso.graeshoppe.product.service.StockCurrentService;
import com.diviso.graeshoppe.product.service.dto.StockCurrentDTO;
import com.diviso.graeshoppe.product.service.mapper.StockCurrentMapper;
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
 * Integration tests for the {@link StockCurrentResource} REST controller.
 */
@SpringBootTest(classes = {ProductApp.class, TestSecurityConfiguration.class})
public class StockCurrentResourceIT {

    private static final String DEFAULT_I_D_PCODE = "AAAAAAAAAA";
    private static final String UPDATED_I_D_PCODE = "BBBBBBBBBB";

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final Double DEFAULT_SELL_PRICE = 1D;
    private static final Double UPDATED_SELL_PRICE = 2D;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private StockCurrentRepository stockCurrentRepository;

    @Autowired
    private StockCurrentMapper stockCurrentMapper;

    @Autowired
    private StockCurrentService stockCurrentService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.product.repository.search test package.
     *
     * @see com.diviso.graeshoppe.product.repository.search.StockCurrentSearchRepositoryMockConfiguration
     */
    @Autowired
    private StockCurrentSearchRepository mockStockCurrentSearchRepository;

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

    private MockMvc restStockCurrentMockMvc;

    private StockCurrent stockCurrent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockCurrentResource stockCurrentResource = new StockCurrentResource(stockCurrentService);
        this.restStockCurrentMockMvc = MockMvcBuilders.standaloneSetup(stockCurrentResource)
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
    public static StockCurrent createEntity(EntityManager em) {
        StockCurrent stockCurrent = new StockCurrent()
            .iDPcode(DEFAULT_I_D_PCODE)
            .quantity(DEFAULT_QUANTITY)
            .sellPrice(DEFAULT_SELL_PRICE)
            .notes(DEFAULT_NOTES);
        return stockCurrent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockCurrent createUpdatedEntity(EntityManager em) {
        StockCurrent stockCurrent = new StockCurrent()
            .iDPcode(UPDATED_I_D_PCODE)
            .quantity(UPDATED_QUANTITY)
            .sellPrice(UPDATED_SELL_PRICE)
            .notes(UPDATED_NOTES);
        return stockCurrent;
    }

    @BeforeEach
    public void initTest() {
        stockCurrent = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockCurrent() throws Exception {
        int databaseSizeBeforeCreate = stockCurrentRepository.findAll().size();

        // Create the StockCurrent
        StockCurrentDTO stockCurrentDTO = stockCurrentMapper.toDto(stockCurrent);
        restStockCurrentMockMvc.perform(post("/api/stock-currents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockCurrentDTO)))
            .andExpect(status().isCreated());

        // Validate the StockCurrent in the database
        List<StockCurrent> stockCurrentList = stockCurrentRepository.findAll();
        assertThat(stockCurrentList).hasSize(databaseSizeBeforeCreate + 1);
        StockCurrent testStockCurrent = stockCurrentList.get(stockCurrentList.size() - 1);
        assertThat(testStockCurrent.getiDPcode()).isEqualTo(DEFAULT_I_D_PCODE);
        assertThat(testStockCurrent.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testStockCurrent.getSellPrice()).isEqualTo(DEFAULT_SELL_PRICE);
        assertThat(testStockCurrent.getNotes()).isEqualTo(DEFAULT_NOTES);

        // Validate the StockCurrent in Elasticsearch
        verify(mockStockCurrentSearchRepository, times(1)).save(testStockCurrent);
    }

    @Test
    @Transactional
    public void createStockCurrentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockCurrentRepository.findAll().size();

        // Create the StockCurrent with an existing ID
        stockCurrent.setId(1L);
        StockCurrentDTO stockCurrentDTO = stockCurrentMapper.toDto(stockCurrent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockCurrentMockMvc.perform(post("/api/stock-currents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockCurrentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockCurrent in the database
        List<StockCurrent> stockCurrentList = stockCurrentRepository.findAll();
        assertThat(stockCurrentList).hasSize(databaseSizeBeforeCreate);

        // Validate the StockCurrent in Elasticsearch
        verify(mockStockCurrentSearchRepository, times(0)).save(stockCurrent);
    }


    @Test
    @Transactional
    public void getAllStockCurrents() throws Exception {
        // Initialize the database
        stockCurrentRepository.saveAndFlush(stockCurrent);

        // Get all the stockCurrentList
        restStockCurrentMockMvc.perform(get("/api/stock-currents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockCurrent.getId().intValue())))
            .andExpect(jsonPath("$.[*].iDPcode").value(hasItem(DEFAULT_I_D_PCODE)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].sellPrice").value(hasItem(DEFAULT_SELL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }
    
    @Test
    @Transactional
    public void getStockCurrent() throws Exception {
        // Initialize the database
        stockCurrentRepository.saveAndFlush(stockCurrent);

        // Get the stockCurrent
        restStockCurrentMockMvc.perform(get("/api/stock-currents/{id}", stockCurrent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockCurrent.getId().intValue()))
            .andExpect(jsonPath("$.iDPcode").value(DEFAULT_I_D_PCODE))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.sellPrice").value(DEFAULT_SELL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    public void getNonExistingStockCurrent() throws Exception {
        // Get the stockCurrent
        restStockCurrentMockMvc.perform(get("/api/stock-currents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockCurrent() throws Exception {
        // Initialize the database
        stockCurrentRepository.saveAndFlush(stockCurrent);

        int databaseSizeBeforeUpdate = stockCurrentRepository.findAll().size();

        // Update the stockCurrent
        StockCurrent updatedStockCurrent = stockCurrentRepository.findById(stockCurrent.getId()).get();
        // Disconnect from session so that the updates on updatedStockCurrent are not directly saved in db
        em.detach(updatedStockCurrent);
        updatedStockCurrent
            .iDPcode(UPDATED_I_D_PCODE)
            .quantity(UPDATED_QUANTITY)
            .sellPrice(UPDATED_SELL_PRICE)
            .notes(UPDATED_NOTES);
        StockCurrentDTO stockCurrentDTO = stockCurrentMapper.toDto(updatedStockCurrent);

        restStockCurrentMockMvc.perform(put("/api/stock-currents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockCurrentDTO)))
            .andExpect(status().isOk());

        // Validate the StockCurrent in the database
        List<StockCurrent> stockCurrentList = stockCurrentRepository.findAll();
        assertThat(stockCurrentList).hasSize(databaseSizeBeforeUpdate);
        StockCurrent testStockCurrent = stockCurrentList.get(stockCurrentList.size() - 1);
        assertThat(testStockCurrent.getiDPcode()).isEqualTo(UPDATED_I_D_PCODE);
        assertThat(testStockCurrent.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testStockCurrent.getSellPrice()).isEqualTo(UPDATED_SELL_PRICE);
        assertThat(testStockCurrent.getNotes()).isEqualTo(UPDATED_NOTES);

        // Validate the StockCurrent in Elasticsearch
        verify(mockStockCurrentSearchRepository, times(1)).save(testStockCurrent);
    }

    @Test
    @Transactional
    public void updateNonExistingStockCurrent() throws Exception {
        int databaseSizeBeforeUpdate = stockCurrentRepository.findAll().size();

        // Create the StockCurrent
        StockCurrentDTO stockCurrentDTO = stockCurrentMapper.toDto(stockCurrent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockCurrentMockMvc.perform(put("/api/stock-currents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockCurrentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockCurrent in the database
        List<StockCurrent> stockCurrentList = stockCurrentRepository.findAll();
        assertThat(stockCurrentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StockCurrent in Elasticsearch
        verify(mockStockCurrentSearchRepository, times(0)).save(stockCurrent);
    }

    @Test
    @Transactional
    public void deleteStockCurrent() throws Exception {
        // Initialize the database
        stockCurrentRepository.saveAndFlush(stockCurrent);

        int databaseSizeBeforeDelete = stockCurrentRepository.findAll().size();

        // Delete the stockCurrent
        restStockCurrentMockMvc.perform(delete("/api/stock-currents/{id}", stockCurrent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockCurrent> stockCurrentList = stockCurrentRepository.findAll();
        assertThat(stockCurrentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StockCurrent in Elasticsearch
        verify(mockStockCurrentSearchRepository, times(1)).deleteById(stockCurrent.getId());
    }

    @Test
    @Transactional
    public void searchStockCurrent() throws Exception {
        // Initialize the database
        stockCurrentRepository.saveAndFlush(stockCurrent);
        when(mockStockCurrentSearchRepository.search(queryStringQuery("id:" + stockCurrent.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(stockCurrent), PageRequest.of(0, 1), 1));
        // Search the stockCurrent
        restStockCurrentMockMvc.perform(get("/api/_search/stock-currents?query=id:" + stockCurrent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockCurrent.getId().intValue())))
            .andExpect(jsonPath("$.[*].iDPcode").value(hasItem(DEFAULT_I_D_PCODE)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].sellPrice").value(hasItem(DEFAULT_SELL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }
}
