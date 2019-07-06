package com.diviso.graeshoppe.product.service;

import com.diviso.graeshoppe.product.service.dto.DiscountDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Discount.
 */
public interface DiscountService {

    /**
     * Save a discount.
     *
     * @param discountDTO the entity to save
     * @return the persisted entity
     */
    DiscountDTO save(DiscountDTO discountDTO);

    /**
     * Get all the discounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DiscountDTO> findAll(Pageable pageable);


    /**
     * Get the "id" discount.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DiscountDTO> findOne(Long id);

    /**
     * Delete the "id" discount.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the discount corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DiscountDTO> search(String query, Pageable pageable);
}
