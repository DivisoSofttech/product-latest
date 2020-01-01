package com.diviso.graeshoppe.product.web.rest;

import com.diviso.graeshoppe.product.ProductApp;
import com.diviso.graeshoppe.product.config.TestSecurityConfiguration;
import com.diviso.graeshoppe.product.domain.Supplier;
import com.diviso.graeshoppe.product.repository.SupplierRepository;
import com.diviso.graeshoppe.product.repository.search.SupplierSearchRepository;
import com.diviso.graeshoppe.product.service.SupplierService;
import com.diviso.graeshoppe.product.service.dto.SupplierDTO;
import com.diviso.graeshoppe.product.service.mapper.SupplierMapper;
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
 * Integration tests for the {@link SupplierResource} REST controller.
 */
@SpringBootTest(classes = {ProductApp.class, TestSecurityConfiguration.class})
public class SupplierResourceIT {

    private static final String DEFAULT_I_D_PCODE = "AAAAAAAAAA";
    private static final String UPDATED_I_D_PCODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_CREDIT_LIMIT = 1D;
    private static final Double UPDATED_CREDIT_LIMIT = 2D;

    private static final Double DEFAULT_CURRENT_DEBT = 1D;
    private static final Double UPDATED_CURRENT_DEBT = 2D;

    private static final LocalDate DEFAULT_DEBT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEBT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_VISIBLE = false;
    private static final Boolean UPDATED_VISIBLE = true;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private SupplierService supplierService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.product.repository.search test package.
     *
     * @see com.diviso.graeshoppe.product.repository.search.SupplierSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupplierSearchRepository mockSupplierSearchRepository;

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

    private MockMvc restSupplierMockMvc;

    private Supplier supplier;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplierResource supplierResource = new SupplierResource(supplierService);
        this.restSupplierMockMvc = MockMvcBuilders.standaloneSetup(supplierResource)
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
    public static Supplier createEntity(EntityManager em) {
        Supplier supplier = new Supplier()
            .iDPcode(DEFAULT_I_D_PCODE)
            .companyName(DEFAULT_COMPANY_NAME)
            .creditLimit(DEFAULT_CREDIT_LIMIT)
            .currentDebt(DEFAULT_CURRENT_DEBT)
            .debtDate(DEFAULT_DEBT_DATE)
            .visible(DEFAULT_VISIBLE);
        return supplier;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supplier createUpdatedEntity(EntityManager em) {
        Supplier supplier = new Supplier()
            .iDPcode(UPDATED_I_D_PCODE)
            .companyName(UPDATED_COMPANY_NAME)
            .creditLimit(UPDATED_CREDIT_LIMIT)
            .currentDebt(UPDATED_CURRENT_DEBT)
            .debtDate(UPDATED_DEBT_DATE)
            .visible(UPDATED_VISIBLE);
        return supplier;
    }

    @BeforeEach
    public void initTest() {
        supplier = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplier() throws Exception {
        int databaseSizeBeforeCreate = supplierRepository.findAll().size();

        // Create the Supplier
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);
        restSupplierMockMvc.perform(post("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierDTO)))
            .andExpect(status().isCreated());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeCreate + 1);
        Supplier testSupplier = supplierList.get(supplierList.size() - 1);
        assertThat(testSupplier.getiDPcode()).isEqualTo(DEFAULT_I_D_PCODE);
        assertThat(testSupplier.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testSupplier.getCreditLimit()).isEqualTo(DEFAULT_CREDIT_LIMIT);
        assertThat(testSupplier.getCurrentDebt()).isEqualTo(DEFAULT_CURRENT_DEBT);
        assertThat(testSupplier.getDebtDate()).isEqualTo(DEFAULT_DEBT_DATE);
        assertThat(testSupplier.isVisible()).isEqualTo(DEFAULT_VISIBLE);

        // Validate the Supplier in Elasticsearch
        verify(mockSupplierSearchRepository, times(1)).save(testSupplier);
    }

    @Test
    @Transactional
    public void createSupplierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplierRepository.findAll().size();

        // Create the Supplier with an existing ID
        supplier.setId(1L);
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierMockMvc.perform(post("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeCreate);

        // Validate the Supplier in Elasticsearch
        verify(mockSupplierSearchRepository, times(0)).save(supplier);
    }


    @Test
    @Transactional
    public void getAllSuppliers() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList
        restSupplierMockMvc.perform(get("/api/suppliers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplier.getId().intValue())))
            .andExpect(jsonPath("$.[*].iDPcode").value(hasItem(DEFAULT_I_D_PCODE)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].creditLimit").value(hasItem(DEFAULT_CREDIT_LIMIT.doubleValue())))
            .andExpect(jsonPath("$.[*].currentDebt").value(hasItem(DEFAULT_CURRENT_DEBT.doubleValue())))
            .andExpect(jsonPath("$.[*].debtDate").value(hasItem(DEFAULT_DEBT_DATE.toString())))
            .andExpect(jsonPath("$.[*].visible").value(hasItem(DEFAULT_VISIBLE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSupplier() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get the supplier
        restSupplierMockMvc.perform(get("/api/suppliers/{id}", supplier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplier.getId().intValue()))
            .andExpect(jsonPath("$.iDPcode").value(DEFAULT_I_D_PCODE))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.creditLimit").value(DEFAULT_CREDIT_LIMIT.doubleValue()))
            .andExpect(jsonPath("$.currentDebt").value(DEFAULT_CURRENT_DEBT.doubleValue()))
            .andExpect(jsonPath("$.debtDate").value(DEFAULT_DEBT_DATE.toString()))
            .andExpect(jsonPath("$.visible").value(DEFAULT_VISIBLE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSupplier() throws Exception {
        // Get the supplier
        restSupplierMockMvc.perform(get("/api/suppliers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplier() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();

        // Update the supplier
        Supplier updatedSupplier = supplierRepository.findById(supplier.getId()).get();
        // Disconnect from session so that the updates on updatedSupplier are not directly saved in db
        em.detach(updatedSupplier);
        updatedSupplier
            .iDPcode(UPDATED_I_D_PCODE)
            .companyName(UPDATED_COMPANY_NAME)
            .creditLimit(UPDATED_CREDIT_LIMIT)
            .currentDebt(UPDATED_CURRENT_DEBT)
            .debtDate(UPDATED_DEBT_DATE)
            .visible(UPDATED_VISIBLE);
        SupplierDTO supplierDTO = supplierMapper.toDto(updatedSupplier);

        restSupplierMockMvc.perform(put("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierDTO)))
            .andExpect(status().isOk());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
        Supplier testSupplier = supplierList.get(supplierList.size() - 1);
        assertThat(testSupplier.getiDPcode()).isEqualTo(UPDATED_I_D_PCODE);
        assertThat(testSupplier.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testSupplier.getCreditLimit()).isEqualTo(UPDATED_CREDIT_LIMIT);
        assertThat(testSupplier.getCurrentDebt()).isEqualTo(UPDATED_CURRENT_DEBT);
        assertThat(testSupplier.getDebtDate()).isEqualTo(UPDATED_DEBT_DATE);
        assertThat(testSupplier.isVisible()).isEqualTo(UPDATED_VISIBLE);

        // Validate the Supplier in Elasticsearch
        verify(mockSupplierSearchRepository, times(1)).save(testSupplier);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplier() throws Exception {
        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();

        // Create the Supplier
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierMockMvc.perform(put("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Supplier in Elasticsearch
        verify(mockSupplierSearchRepository, times(0)).save(supplier);
    }

    @Test
    @Transactional
    public void deleteSupplier() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        int databaseSizeBeforeDelete = supplierRepository.findAll().size();

        // Delete the supplier
        restSupplierMockMvc.perform(delete("/api/suppliers/{id}", supplier.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Supplier in Elasticsearch
        verify(mockSupplierSearchRepository, times(1)).deleteById(supplier.getId());
    }

    @Test
    @Transactional
    public void searchSupplier() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);
        when(mockSupplierSearchRepository.search(queryStringQuery("id:" + supplier.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(supplier), PageRequest.of(0, 1), 1));
        // Search the supplier
        restSupplierMockMvc.perform(get("/api/_search/suppliers?query=id:" + supplier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplier.getId().intValue())))
            .andExpect(jsonPath("$.[*].iDPcode").value(hasItem(DEFAULT_I_D_PCODE)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].creditLimit").value(hasItem(DEFAULT_CREDIT_LIMIT.doubleValue())))
            .andExpect(jsonPath("$.[*].currentDebt").value(hasItem(DEFAULT_CURRENT_DEBT.doubleValue())))
            .andExpect(jsonPath("$.[*].debtDate").value(hasItem(DEFAULT_DEBT_DATE.toString())))
            .andExpect(jsonPath("$.[*].visible").value(hasItem(DEFAULT_VISIBLE.booleanValue())));
    }
}
