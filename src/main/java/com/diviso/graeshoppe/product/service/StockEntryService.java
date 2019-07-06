package com.diviso.graeshoppe.product.service;

import com.diviso.graeshoppe.product.service.dto.StockEntryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing StockEntry.
 */
public interface StockEntryService {

    /**
     * Save a stockEntry.
     *
     * @param stockEntryDTO the entity to save
     * @return the persisted entity
     */
    StockEntryDTO save(StockEntryDTO stockEntryDTO);

    /**
     * Get all the stockEntries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StockEntryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" stockEntry.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StockEntryDTO> findOne(Long id);

    /**
     * Delete the "id" stockEntry.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the stockEntry corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StockEntryDTO> search(String query, Pageable pageable);
}
