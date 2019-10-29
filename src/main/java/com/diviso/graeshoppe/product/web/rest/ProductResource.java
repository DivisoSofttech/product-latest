package com.diviso.graeshoppe.product.web.rest;

import com.diviso.graeshoppe.product.domain.Product;
import com.diviso.graeshoppe.product.domain.StockCurrent;
import com.diviso.graeshoppe.product.service.ProductService;
import com.diviso.graeshoppe.product.service.StockCurrentService;
import com.diviso.graeshoppe.product.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.product.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.product.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.product.service.dto.ProductDTO;
import com.diviso.graeshoppe.product.service.dto.StockCurrentDTO;
import com.diviso.graeshoppe.product.service.mapper.ProductMapper;

import io.github.jhipster.web.util.ResponseUtil;
import net.sf.jasperreports.engine.JRException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Product.
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

	private final Logger log = LoggerFactory.getLogger(ProductResource.class);

	private static final String ENTITY_NAME = "productProduct";

	private final ProductService productService;

	@Autowired
	private StockCurrentService stockCurrentService;

	@Autowired
	private ProductMapper productMapper;

	public ProductResource(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * POST /products : Create a new product.
	 *
	 * @param productDTO
	 *            the productDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new productDTO, or with status 400 (Bad Request) if the product
	 *         has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/products")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) throws URISyntaxException {
		log.debug("REST request to save Product : {}", productDTO);

		StockCurrentDTO stockCurrent = new StockCurrentDTO();

		
		stockCurrent.setSellPrice(productDTO.getSellingPrice());

		if (productDTO.getId() != null) {
			throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
		}

		ProductDTO result1 = productService.save(productDTO);
		
		stockCurrent.setiDPcode(result1.getiDPcode());
		stockCurrent.setQuantity(0.0);
		stockCurrent.setProductId(result1.getId());
		
		stockCurrent= stockCurrentService.save(stockCurrent);

		if (result1.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		ProductDTO result = productService.save(result1);

		stockCurrentService.save(stockCurrent);

		return ResponseEntity.created(new URI("/api/products/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /products : Updates an existing product.
	 *
	 * @param productDTO
	 *            the productDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         productDTO, or with status 400 (Bad Request) if the productDTO is
	 *         not valid, or with status 500 (Internal Server Error) if the
	 *         productDTO couldn't be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/products")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) throws URISyntaxException {
		log.debug("REST request to update Product : {}", productDTO);
		if (productDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		ProductDTO result = productService.save(productDTO);
		
		
		Optional<StockCurrentDTO> stockCurrentDTO=stockCurrentService.findByProductId(result.getId());
		
		
		stockCurrentService.updateStockCurrent(stockCurrentDTO.get());
		
		
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productDTO.getId().toString())).body(result);
	}

	/**
	 * GET /products : get all the products.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of products
	 *         in body
	 */
	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> getAllProducts(Pageable pageable) {
		log.debug("REST request to get a page of Products");
		Page<ProductDTO> page = productService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/products");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * GET /products/:id : get the "id" product.
	 *
	 * @param id
	 *            the id of the productDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         productDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/products/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
		log.debug("REST request to get Product : {}", id);
		Optional<ProductDTO> productDTO = productService.findOne(id);
		return ResponseUtil.wrapOrNotFound(productDTO);
	}

	/**
	 * DELETE /products/:id : delete the "id" product.
	 *
	 * @param id
	 *            the id of the productDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		log.debug("REST request to delete Product : {}", id);
		productService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	/**
	 * SEARCH /_search/products?query=:query : search for the product
	 * corresponding to the query.
	 *
	 * @param query
	 *            the query of the product search
	 * @param pageable
	 *            the pagination information
	 * @return the result of the search
	 */
	@GetMapping("/_search/products")
	public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String query, Pageable pageable) {
		log.debug("REST request to search for a page of Products for query {}", query);
		Page<ProductDTO> page = productService.search(query, pageable);
		HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/products");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@PostMapping("/products/toDto")
	public ResponseEntity<List<ProductDTO>> listToDto(@RequestBody List<Product> products) {
		log.debug("REST request to convert to DTO");
		List<ProductDTO> dtos = new ArrayList<>();
		products.forEach(a -> {
			dtos.add(productMapper.toDto(a));
		});
		return ResponseEntity.ok().body(dtos);
	}

	@GetMapping("/pdf/products-report")
	public ResponseEntity<byte[]> getProductsPriceAsPdf() {

		log.debug("REST request to get a pdf of products");

		byte[] pdfContents = null;

		try {
			pdfContents = productService.getProductsPriceAsPdf();
		} catch (JRException e) {
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		String fileName = "stock.pdf";
		headers.add("content-disposition", "attachment; filename=" + fileName);
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(pdfContents, headers, HttpStatus.OK);
		return response;
	}

	
	@GetMapping("/pdf/products-report/{idpcode}")
	public ResponseEntity<byte[]> exportProductListAsPdf(@PathVariable String idpcode) {

		log.debug("REST request to get a pdf of products");

		byte[] pdfContents = null;

		try {
			pdfContents = productService.exportProductListAsPdf(idpcode);
		} catch (JRException e) {
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		String fileName = "product.pdf";
		headers.add("content-disposition", "attachment; filename=" + fileName);
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(pdfContents, headers, HttpStatus.OK);
		return response;
	}
	
	
	
	
}
