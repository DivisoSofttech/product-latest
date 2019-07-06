package com.diviso.graeshoppe.product.web.rest;

import com.diviso.graeshoppe.product.ProductApp;

import com.diviso.graeshoppe.product.domain.Discount;
import com.diviso.graeshoppe.product.repository.DiscountRepository;
import com.diviso.graeshoppe.product.repository.search.DiscountSearchRepository;
import com.diviso.graeshoppe.product.service.DiscountService;
import com.diviso.graeshoppe.product.service.dto.DiscountDTO;
import com.diviso.graeshoppe.product.service.mapper.DiscountMapper;
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
 * Test class for the DiscountResource REST controller.
 *
 * @see DiscountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductApp.class)
public class DiscountResourceIntTest {

    private static final Double DEFAULT_RATE = 1D;
    private static final Double UPDATED_RATE = 2D;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountMapper discountMapper;

    @Autowired
    private DiscountService discountService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.product.repository.search test package.
     *
     * @see com.diviso.graeshoppe.product.repository.search.DiscountSearchRepositoryMockConfiguration
     */
    @Autowired
    private DiscountSearchRepository mockDiscountSearchRepository;

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

    private MockMvc restDiscountMockMvc;

    private Discount discount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiscountResource discountResource = new DiscountResource(discountService);
        this.restDiscountMockMvc = MockMvcBuilders.standaloneSetup(discountResource)
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
    public static Discount createEntity(EntityManager em) {
        Discount discount = new Discount()
            .rate(DEFAULT_RATE);
        return discount;
    }

    @Before
    public void initTest() {
        discount = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiscount() throws Exception {
        int databaseSizeBeforeCreate = discountRepository.findAll().size();

        // Create the Discount
        DiscountDTO discountDTO = discountMapper.toDto(discount);
        restDiscountMockMvc.perform(post("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountDTO)))
            .andExpect(status().isCreated());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeCreate + 1);
        Discount testDiscount = discountList.get(discountList.size() - 1);
        assertThat(testDiscount.getRate()).isEqualTo(DEFAULT_RATE);

        // Validate the Discount in Elasticsearch
        verify(mockDiscountSearchRepository, times(1)).save(testDiscount);
    }

    @Test
    @Transactional
    public void createDiscountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = discountRepository.findAll().size();

        // Create the Discount with an existing ID
        discount.setId(1L);
        DiscountDTO discountDTO = discountMapper.toDto(discount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscountMockMvc.perform(post("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeCreate);

        // Validate the Discount in Elasticsearch
        verify(mockDiscountSearchRepository, times(0)).save(discount);
    }

    @Test
    @Transactional
    public void getAllDiscounts() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList
        restDiscountMockMvc.perform(get("/api/discounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discount.getId().intValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getDiscount() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get the discount
        restDiscountMockMvc.perform(get("/api/discounts/{id}", discount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(discount.getId().intValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDiscount() throws Exception {
        // Get the discount
        restDiscountMockMvc.perform(get("/api/discounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscount() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        int databaseSizeBeforeUpdate = discountRepository.findAll().size();

        // Update the discount
        Discount updatedDiscount = discountRepository.findById(discount.getId()).get();
        // Disconnect from session so that the updates on updatedDiscount are not directly saved in db
        em.detach(updatedDiscount);
        updatedDiscount
            .rate(UPDATED_RATE);
        DiscountDTO discountDTO = discountMapper.toDto(updatedDiscount);

        restDiscountMockMvc.perform(put("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountDTO)))
            .andExpect(status().isOk());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeUpdate);
        Discount testDiscount = discountList.get(discountList.size() - 1);
        assertThat(testDiscount.getRate()).isEqualTo(UPDATED_RATE);

        // Validate the Discount in Elasticsearch
        verify(mockDiscountSearchRepository, times(1)).save(testDiscount);
    }

    @Test
    @Transactional
    public void updateNonExistingDiscount() throws Exception {
        int databaseSizeBeforeUpdate = discountRepository.findAll().size();

        // Create the Discount
        DiscountDTO discountDTO = discountMapper.toDto(discount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiscountMockMvc.perform(put("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Discount in Elasticsearch
        verify(mockDiscountSearchRepository, times(0)).save(discount);
    }

    @Test
    @Transactional
    public void deleteDiscount() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        int databaseSizeBeforeDelete = discountRepository.findAll().size();

        // Delete the discount
        restDiscountMockMvc.perform(delete("/api/discounts/{id}", discount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Discount in Elasticsearch
        verify(mockDiscountSearchRepository, times(1)).deleteById(discount.getId());
    }

    @Test
    @Transactional
    public void searchDiscount() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);
        when(mockDiscountSearchRepository.search(queryStringQuery("id:" + discount.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(discount), PageRequest.of(0, 1), 1));
        // Search the discount
        restDiscountMockMvc.perform(get("/api/_search/discounts?query=id:" + discount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discount.getId().intValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Discount.class);
        Discount discount1 = new Discount();
        discount1.setId(1L);
        Discount discount2 = new Discount();
        discount2.setId(discount1.getId());
        assertThat(discount1).isEqualTo(discount2);
        discount2.setId(2L);
        assertThat(discount1).isNotEqualTo(discount2);
        discount1.setId(null);
        assertThat(discount1).isNotEqualTo(discount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiscountDTO.class);
        DiscountDTO discountDTO1 = new DiscountDTO();
        discountDTO1.setId(1L);
        DiscountDTO discountDTO2 = new DiscountDTO();
        assertThat(discountDTO1).isNotEqualTo(discountDTO2);
        discountDTO2.setId(discountDTO1.getId());
        assertThat(discountDTO1).isEqualTo(discountDTO2);
        discountDTO2.setId(2L);
        assertThat(discountDTO1).isNotEqualTo(discountDTO2);
        discountDTO1.setId(null);
        assertThat(discountDTO1).isNotEqualTo(discountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(discountMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(discountMapper.fromId(null)).isNull();
    }
}
