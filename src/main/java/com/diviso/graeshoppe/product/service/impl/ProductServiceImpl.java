package com.diviso.graeshoppe.product.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.completion.Completion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.diviso.graeshoppe.product.domain.Product;
import com.diviso.graeshoppe.product.domain.StockCurrent;
import com.diviso.graeshoppe.product.repository.ProductRepository;
import com.diviso.graeshoppe.product.repository.StockCurrentRepository;
import com.diviso.graeshoppe.product.repository.search.ProductSearchRepository;
import com.diviso.graeshoppe.product.repository.search.StockCurrentSearchRepository;
import com.diviso.graeshoppe.product.security.SecurityUtils;
import com.diviso.graeshoppe.product.service.ImageService;
import com.diviso.graeshoppe.product.service.ProductService;
import com.diviso.graeshoppe.product.service.dto.ProductDTO;
import com.diviso.graeshoppe.product.service.mapper.ProductMapper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import com.diviso.graeshoppe.product.repository.search.ProductSuggestionSearchRepository;
import com.diviso.graeshoppe.product.domain.search.ProductSuggestion;

/**
 * Service Implementation for managing Product.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	private final ProductRepository productRepository;

	private final ProductMapper productMapper;

	private final ProductSearchRepository productSearchRepository;

	@Autowired
	private ImageService imageService;
	
	@Autowired
	StockCurrentSearchRepository stockCurrentSearchRepository;

	@Autowired
	StockCurrentRepository stockCurrentRepository;

	@Autowired
	DataSource dataSource;

	@Autowired
	StockCurrentServiceImpl stockCurrentServiceImpl;
	
	@Autowired
	private  ProductSuggestionSearchRepository productSuggestionSearchRepository;
	
	
	public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper,
			ProductSearchRepository productSearchRepository) {
		this.productRepository = productRepository;
		this.productMapper = productMapper;
		this.productSearchRepository = productSearchRepository;
	}

	/**
	 * Save a product.
	 *
	 * @param productDTO the entity to save
	 * @return the persisted entity
	 */
	@SuppressWarnings("deprecation")
	@Override
	public ProductDTO save(ProductDTO productDTO) {
		log.debug("Request to save Product : {}", productDTO);
		Product product = productMapper.toEntity(productDTO);

		String imageLink  = imageService.saveFile("product", UUID.randomUUID().toString(), productDTO.getImage());
		product.setImageLink(imageLink);
		
		
		Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
		product.setiDPcode(currentUserLogin.get());

		product = productRepository.save(product);
		
		ProductSuggestion  productSuggestion = new ProductSuggestion();
		productSuggestion.setId(product.getId());
		Completion completion=new Completion(new String[]{product.getName()});
		completion.setWeight(3);
		productSuggestion.setSuggest(completion);
		
		ProductDTO result = productMapper.toDto(product);
		productSearchRepository.save(product);
		
		productSuggestionSearchRepository.save(productSuggestion);
		return updateToEs(result);
	}
	@Override
	public ProductDTO saveForFileUpLoad(ProductDTO productDTO) {
		log.debug("Request to save Product : {}", productDTO);
		Product product = productMapper.toEntity(productDTO);
		
		/*
		 * Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
		 * product.setiDPcode(currentUserLogin.get());
		 */

		product = productRepository.save(product);
		
		ProductSuggestion  productSuggestion = new ProductSuggestion();
		productSuggestion.setId(product.getId());
		Completion completion=new Completion(new String[]{product.getName()});
		completion.setWeight(3);
		productSuggestion.setSuggest(completion);
		
		ProductDTO result = productMapper.toDto(product);
		productSearchRepository.save(product);
		
		productSuggestionSearchRepository.save(productSuggestion);
		return updateToEs(result);
	}
	private ProductDTO updateToEs(ProductDTO productDTO) {
		log.debug("Request to save Product : {}", productDTO);
		Product product = productMapper.toEntity(productDTO);
		
		if(productDTO.getImage()!=null)  
		{
		String imageLink  = imageService.saveFile("product", UUID.randomUUID().toString(), productDTO.getImage());
		product.setImageLink(imageLink);

		}
		ProductDTO result = productMapper.toDto(product);
		productSearchRepository.save(product);

		return result;
	}
	
	@Override
	public ProductDTO update(ProductDTO productDTO) {
		log.debug("Request to update Product : {}", productDTO);
		Product product = productMapper.toEntity(productDTO);

		if(productDTO.getImage()!=null) {
		String imageLink  = imageService.saveFile("product", UUID.randomUUID().toString(), productDTO.getImage());
		product.setImageLink(imageLink);
		
		}
		Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
		product.setiDPcode(currentUserLogin.get());

		product = productRepository.save(product);
		ProductDTO result = productMapper.toDto(product);
		productSearchRepository.save(product);
		return updateToEs(result);
	}

	/**
	 * Get all the products.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Products");
		return productRepository.findAll(pageable).map(productMapper::toDto);
	}

	/**
	 * Get all the Product with eager load of many-to-many relationships.
	 *
	 * @return the list of entities
	 */
	/*
	 * public Page<ProductDTO> findAllWithEagerRelationships(Pageable pageable) {
	 * return
	 * productRepository.findAllWithEagerRelationships(pageable).map(productMapper::
	 * toDto); }
	 */

	/**
	 * Get one product by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = true) public Optional<ProductDTO> findOne(Long id)
	 * { log.debug("Request to get Product : {}", id); return
	 * productRepository.findOneWithEagerRelationships(id).map(productMapper::toDto)
	 * ; }
	 */

	@Override
	@Transactional(readOnly = true)
	public Optional<ProductDTO> findOne(Long id) {
		log.debug("Request to get Tax : {}", id);
		return productRepository.findById(id).map(productMapper::toDto);
	}

	/**
	 * Delete the product by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Product : {}", id);

		Optional<StockCurrent> stockcurrent = stockCurrentRepository.findByProductId(id);
		stockcurrent.ifPresent(stockcurrent1 -> {
			stockCurrentRepository.delete(stockcurrent1);
			stockCurrentSearchRepository.delete(stockcurrent1);
		});
		productRepository.deleteById(id);
		productSearchRepository.deleteById(id);
	}

	/**
	 * Search for the product corresponding to the query.
	 *
	 * @param query    the query of the search
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<ProductDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of Products for query {}", query);
		return productSearchRepository.search(queryStringQuery(query), pageable).map(productMapper::toDto);
	}

	/**
	 * Get productsReport.
	 * 
	 * @return the byte[]
	 * @throws JRException
	 */
	@Override
	public byte[] getProductsPriceAsPdf() throws JRException {

		log.debug("Request to pdf of all products");

		JasperReport jr = JasperCompileManager.compileReport("stock.jrxml");

		// Preparing parameters
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("product", jr);

		Connection conn = null;

		try {
			conn = dataSource.getConnection();

			// System.out.println(conn.getClientInfo()+"-----------------------"+conn.getMetaData().getURL()+"_________________________________");

		} catch (SQLException e) {
			e.printStackTrace();

		}
		JasperPrint jp = JasperFillManager.fillReport(jr, parameters, conn);

		return JasperExportManager.exportReportToPdf(jp);

	}

	@Override
	public byte[] exportProductListAsPdf(String idpcode) throws JRException {

		log.debug("Request to pdf of all products list");

		// JasperReport jr = JasperCompileManager.compileReport("product.jrxml");

		// Preparing parameters
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("i_d_pcode", idpcode);

		Connection conn = null;

		try {
			conn = dataSource.getConnection();

			// System.out.println(conn.getClientInfo()+"-----------------------"+conn.getMetaData().getURL()+"_________________________________");

		} catch (SQLException e) {
			e.printStackTrace();

		}
		JasperPrint jp = JasperFillManager.fillReport("src/main/resources/report/product.jasper", parameters, conn);

		return JasperExportManager.exportReportToPdf(jp);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.product.service.ProductService#findOne(java.lang.Long)
	 */

}
