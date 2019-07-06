package com.diviso.graeshoppe.product.web.rest;

import com.diviso.graeshoppe.product.ProductApp;

import com.diviso.graeshoppe.product.domain.Product;
import com.diviso.graeshoppe.product.repository.ProductRepository;
import com.diviso.graeshoppe.product.repository.search.ProductSearchRepository;
import com.diviso.graeshoppe.product.service.ProductService;
import com.diviso.graeshoppe.product.service.dto.ProductDTO;
import com.diviso.graeshoppe.product.service.mapper.ProductMapper;
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
import org.springframework.util.Base64Utils;
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
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductApp.class)
public class ProductResourceIntTest {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SHOW_IN_CATALOGUE = false;
    private static final Boolean UPDATED_SHOW_IN_CATALOGUE = true;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_SKU = "AAAAAAAAAA";
    private static final String UPDATED_SKU = "BBBBBBBBBB";

    private static final String DEFAULT_I_D_PCODE = "AAAAAAAAAA";
    private static final String UPDATED_I_D_PCODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SERVICE_ITEM = false;
    private static final Boolean UPDATED_IS_SERVICE_ITEM = true;

    private static final Boolean DEFAULT_IS_AUXILARY_ITEM = false;
    private static final Boolean UPDATED_IS_AUXILARY_ITEM = true;

    private static final Double DEFAULT_MIN_QUANTITY_LEVEL = 1D;
    private static final Double UPDATED_MIN_QUANTITY_LEVEL = 2D;

    private static final Double DEFAULT_MAX_QUANTITY_LEVEL = 1D;
    private static final Double UPDATED_MAX_QUANTITY_LEVEL = 2D;

    private static final Double DEFAULT_STORAGE_COST = 1D;
    private static final Double UPDATED_STORAGE_COST = 2D;

    private static final Double DEFAULT_SELLING_PRICE = 1D;
    private static final Double UPDATED_SELLING_PRICE = 2D;

    private static final Double DEFAULT_BUY_PRICE = 1D;
    private static final Double UPDATED_BUY_PRICE = 2D;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.product.repository.search test package.
     *
     * @see com.diviso.graeshoppe.product.repository.search.ProductSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductSearchRepository mockProductSearchRepository;

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

    private MockMvc restProductMockMvc;

    private Product product;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductResource productResource = new ProductResource(productService);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
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
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .reference(DEFAULT_REFERENCE)
            .name(DEFAULT_NAME)
            .showInCatalogue(DEFAULT_SHOW_IN_CATALOGUE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .isActive(DEFAULT_IS_ACTIVE)
            .sku(DEFAULT_SKU)
            .iDPcode(DEFAULT_I_D_PCODE)
            .isServiceItem(DEFAULT_IS_SERVICE_ITEM)
            .isAuxilaryItem(DEFAULT_IS_AUXILARY_ITEM)
            .minQuantityLevel(DEFAULT_MIN_QUANTITY_LEVEL)
            .maxQuantityLevel(DEFAULT_MAX_QUANTITY_LEVEL)
            .storageCost(DEFAULT_STORAGE_COST)
            .sellingPrice(DEFAULT_SELLING_PRICE)
            .buyPrice(DEFAULT_BUY_PRICE);
        return product;
    }

    @Before
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.isShowInCatalogue()).isEqualTo(DEFAULT_SHOW_IN_CATALOGUE);
        assertThat(testProduct.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testProduct.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testProduct.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testProduct.getSku()).isEqualTo(DEFAULT_SKU);
        assertThat(testProduct.getiDPcode()).isEqualTo(DEFAULT_I_D_PCODE);
        assertThat(testProduct.isIsServiceItem()).isEqualTo(DEFAULT_IS_SERVICE_ITEM);
        assertThat(testProduct.isIsAuxilaryItem()).isEqualTo(DEFAULT_IS_AUXILARY_ITEM);
        assertThat(testProduct.getMinQuantityLevel()).isEqualTo(DEFAULT_MIN_QUANTITY_LEVEL);
        assertThat(testProduct.getMaxQuantityLevel()).isEqualTo(DEFAULT_MAX_QUANTITY_LEVEL);
        assertThat(testProduct.getStorageCost()).isEqualTo(DEFAULT_STORAGE_COST);
        assertThat(testProduct.getSellingPrice()).isEqualTo(DEFAULT_SELLING_PRICE);
        assertThat(testProduct.getBuyPrice()).isEqualTo(DEFAULT_BUY_PRICE);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).save(testProduct);
    }

    @Test
    @Transactional
    public void createProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product with an existing ID
        product.setId(1L);
        ProductDTO productDTO = productMapper.toDto(product);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].showInCatalogue").value(hasItem(DEFAULT_SHOW_IN_CATALOGUE.booleanValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].sku").value(hasItem(DEFAULT_SKU.toString())))
            .andExpect(jsonPath("$.[*].iDPcode").value(hasItem(DEFAULT_I_D_PCODE.toString())))
            .andExpect(jsonPath("$.[*].isServiceItem").value(hasItem(DEFAULT_IS_SERVICE_ITEM.booleanValue())))
            .andExpect(jsonPath("$.[*].isAuxilaryItem").value(hasItem(DEFAULT_IS_AUXILARY_ITEM.booleanValue())))
            .andExpect(jsonPath("$.[*].minQuantityLevel").value(hasItem(DEFAULT_MIN_QUANTITY_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].maxQuantityLevel").value(hasItem(DEFAULT_MAX_QUANTITY_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].storageCost").value(hasItem(DEFAULT_STORAGE_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].sellingPrice").value(hasItem(DEFAULT_SELLING_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].buyPrice").value(hasItem(DEFAULT_BUY_PRICE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.showInCatalogue").value(DEFAULT_SHOW_IN_CATALOGUE.booleanValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.sku").value(DEFAULT_SKU.toString()))
            .andExpect(jsonPath("$.iDPcode").value(DEFAULT_I_D_PCODE.toString()))
            .andExpect(jsonPath("$.isServiceItem").value(DEFAULT_IS_SERVICE_ITEM.booleanValue()))
            .andExpect(jsonPath("$.isAuxilaryItem").value(DEFAULT_IS_AUXILARY_ITEM.booleanValue()))
            .andExpect(jsonPath("$.minQuantityLevel").value(DEFAULT_MIN_QUANTITY_LEVEL.doubleValue()))
            .andExpect(jsonPath("$.maxQuantityLevel").value(DEFAULT_MAX_QUANTITY_LEVEL.doubleValue()))
            .andExpect(jsonPath("$.storageCost").value(DEFAULT_STORAGE_COST.doubleValue()))
            .andExpect(jsonPath("$.sellingPrice").value(DEFAULT_SELLING_PRICE.doubleValue()))
            .andExpect(jsonPath("$.buyPrice").value(DEFAULT_BUY_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .reference(UPDATED_REFERENCE)
            .name(UPDATED_NAME)
            .showInCatalogue(UPDATED_SHOW_IN_CATALOGUE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .sku(UPDATED_SKU)
            .iDPcode(UPDATED_I_D_PCODE)
            .isServiceItem(UPDATED_IS_SERVICE_ITEM)
            .isAuxilaryItem(UPDATED_IS_AUXILARY_ITEM)
            .minQuantityLevel(UPDATED_MIN_QUANTITY_LEVEL)
            .maxQuantityLevel(UPDATED_MAX_QUANTITY_LEVEL)
            .storageCost(UPDATED_STORAGE_COST)
            .sellingPrice(UPDATED_SELLING_PRICE)
            .buyPrice(UPDATED_BUY_PRICE);
        ProductDTO productDTO = productMapper.toDto(updatedProduct);

        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.isShowInCatalogue()).isEqualTo(UPDATED_SHOW_IN_CATALOGUE);
        assertThat(testProduct.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProduct.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testProduct.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testProduct.getSku()).isEqualTo(UPDATED_SKU);
        assertThat(testProduct.getiDPcode()).isEqualTo(UPDATED_I_D_PCODE);
        assertThat(testProduct.isIsServiceItem()).isEqualTo(UPDATED_IS_SERVICE_ITEM);
        assertThat(testProduct.isIsAuxilaryItem()).isEqualTo(UPDATED_IS_AUXILARY_ITEM);
        assertThat(testProduct.getMinQuantityLevel()).isEqualTo(UPDATED_MIN_QUANTITY_LEVEL);
        assertThat(testProduct.getMaxQuantityLevel()).isEqualTo(UPDATED_MAX_QUANTITY_LEVEL);
        assertThat(testProduct.getStorageCost()).isEqualTo(UPDATED_STORAGE_COST);
        assertThat(testProduct.getSellingPrice()).isEqualTo(UPDATED_SELLING_PRICE);
        assertThat(testProduct.getBuyPrice()).isEqualTo(UPDATED_BUY_PRICE);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).save(testProduct);
    }

    @Test
    @Transactional
    public void updateNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).deleteById(product.getId());
    }

    @Test
    @Transactional
    public void searchProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        when(mockProductSearchRepository.search(queryStringQuery("id:" + product.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(product), PageRequest.of(0, 1), 1));
        // Search the product
        restProductMockMvc.perform(get("/api/_search/products?query=id:" + product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].showInCatalogue").value(hasItem(DEFAULT_SHOW_IN_CATALOGUE.booleanValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].sku").value(hasItem(DEFAULT_SKU)))
            .andExpect(jsonPath("$.[*].iDPcode").value(hasItem(DEFAULT_I_D_PCODE)))
            .andExpect(jsonPath("$.[*].isServiceItem").value(hasItem(DEFAULT_IS_SERVICE_ITEM.booleanValue())))
            .andExpect(jsonPath("$.[*].isAuxilaryItem").value(hasItem(DEFAULT_IS_AUXILARY_ITEM.booleanValue())))
            .andExpect(jsonPath("$.[*].minQuantityLevel").value(hasItem(DEFAULT_MIN_QUANTITY_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].maxQuantityLevel").value(hasItem(DEFAULT_MAX_QUANTITY_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].storageCost").value(hasItem(DEFAULT_STORAGE_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].sellingPrice").value(hasItem(DEFAULT_SELLING_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].buyPrice").value(hasItem(DEFAULT_BUY_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);
        product2.setId(2L);
        assertThat(product1).isNotEqualTo(product2);
        product1.setId(null);
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDTO.class);
        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setId(1L);
        ProductDTO productDTO2 = new ProductDTO();
        assertThat(productDTO1).isNotEqualTo(productDTO2);
        productDTO2.setId(productDTO1.getId());
        assertThat(productDTO1).isEqualTo(productDTO2);
        productDTO2.setId(2L);
        assertThat(productDTO1).isNotEqualTo(productDTO2);
        productDTO1.setId(null);
        assertThat(productDTO1).isNotEqualTo(productDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productMapper.fromId(null)).isNull();
    }
}
