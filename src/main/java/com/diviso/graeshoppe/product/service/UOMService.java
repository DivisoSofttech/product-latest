package com.diviso.graeshoppe.product.service;

import com.diviso.graeshoppe.product.service.dto.UOMDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UOM.
 */
public interface UOMService {

    /**
     * Save a uOM.
     *
     * @param uOMDTO the entity to save
     * @return the persisted entity
     */
    UOMDTO save(UOMDTO uOMDTO);

    /**
     * Get all the uOMS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UOMDTO> findAll(Pageable pageable);


    /**
     * Get the "id" uOM.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UOMDTO> findOne(Long id);

    /**
     * Delete the "id" uOM.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the uOM corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UOMDTO> search(String query, Pageable pageable);
}
