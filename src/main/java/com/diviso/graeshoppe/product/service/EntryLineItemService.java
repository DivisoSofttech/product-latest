package com.diviso.graeshoppe.product.service;

import com.diviso.graeshoppe.product.service.dto.EntryLineItemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing EntryLineItem.
 */
public interface EntryLineItemService {

    /**
     * Save a entryLineItem.
     *
     * @param entryLineItemDTO the entity to save
     * @return the persisted entity
     */
    EntryLineItemDTO save(EntryLineItemDTO entryLineItemDTO);

    /**
     * Get all the entryLineItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EntryLineItemDTO> findAll(Pageable pageable);


    /**
     * Get the "id" entryLineItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EntryLineItemDTO> findOne(Long id);

    /**
     * Delete the "id" entryLineItem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the entryLineItem corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EntryLineItemDTO> search(String query, Pageable pageable);
}
