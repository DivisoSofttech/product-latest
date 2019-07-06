package com.diviso.graeshoppe.product.service;

import com.diviso.graeshoppe.product.service.dto.ComboLineItemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ComboLineItem.
 */
public interface ComboLineItemService {

    /**
     * Save a comboLineItem.
     *
     * @param comboLineItemDTO the entity to save
     * @return the persisted entity
     */
    ComboLineItemDTO save(ComboLineItemDTO comboLineItemDTO);

    /**
     * Get all the comboLineItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ComboLineItemDTO> findAll(Pageable pageable);


    /**
     * Get the "id" comboLineItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ComboLineItemDTO> findOne(Long id);

    /**
     * Delete the "id" comboLineItem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the comboLineItem corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ComboLineItemDTO> search(String query, Pageable pageable);
}
