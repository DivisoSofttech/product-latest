package com.diviso.graeshoppe.product.service;

import com.diviso.graeshoppe.product.service.dto.ProductDTO;

import net.sf.jasperreports.engine.JRException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

/**
 * Service Interface for managing Product.
 */
public interface ProductService {

    /**
     * Save a product.
     *
     * @param productDTO the entity to save
     * @return the persisted entity
     */
    ProductDTO save(ProductDTO productDTO);
    
	 ProductDTO update(ProductDTO productDTO);

    /**
     * Get all the products.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProductDTO> findAll(Pageable pageable);


    /**
     * Get the "id" product.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductDTO> findOne(Long id);

    /**
     * Delete the "id" product.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the product corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProductDTO> search(String query, Pageable pageable);
    
    byte[] getProductsPriceAsPdf() throws JRException;

	byte[] exportProductListAsPdf(String idpcode) throws JRException;

	/**
	 * @param productDTO
	 * @return
	 */
	ProductDTO saveForFileUpLoad(ProductDTO productDTO);


   
}
