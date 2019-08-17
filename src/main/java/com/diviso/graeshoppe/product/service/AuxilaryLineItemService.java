package com.diviso.graeshoppe.product.service;

import com.diviso.graeshoppe.product.service.dto.AuxilaryLineItemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing AuxilaryLineItem.
 */
public interface AuxilaryLineItemService {

    /**
     * Save a auxilaryLineItem.
     *
     * @param auxilaryLineItemDTO the entity to save
     * @return the persisted entity
     */
    AuxilaryLineItemDTO save(AuxilaryLineItemDTO auxilaryLineItemDTO);

    /**
     * Get all the auxilaryLineItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AuxilaryLineItemDTO> findAll(Pageable pageable);

    List<AuxilaryLineItemDTO> findAll();

    /**
     * Get the "id" auxilaryLineItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AuxilaryLineItemDTO> findOne(Long id);

    /**
     * Delete the "id" auxilaryLineItem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the auxilaryLineItem corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AuxilaryLineItemDTO> search(String query, Pageable pageable);
}
