package com.diviso.graeshoppe.product.service;

import com.diviso.graeshoppe.product.service.dto.SupplierDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Supplier.
 */
public interface SupplierService {

    /**
     * Save a supplier.
     *
     * @param supplierDTO the entity to save
     * @return the persisted entity
     */
    SupplierDTO save(SupplierDTO supplierDTO);

    /**
     * Get all the suppliers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplierDTO> findAll(Pageable pageable);


    /**
     * Get the "id" supplier.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SupplierDTO> findOne(Long id);

    /**
     * Delete the "id" supplier.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the supplier corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplierDTO> search(String query, Pageable pageable);
}
