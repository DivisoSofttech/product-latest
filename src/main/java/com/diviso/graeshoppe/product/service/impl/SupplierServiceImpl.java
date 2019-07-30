package com.diviso.graeshoppe.product.service.impl;

import com.diviso.graeshoppe.product.service.SupplierService;
import com.diviso.graeshoppe.product.domain.Supplier;
import com.diviso.graeshoppe.product.repository.SupplierRepository;
import com.diviso.graeshoppe.product.repository.search.SupplierSearchRepository;
import com.diviso.graeshoppe.product.service.dto.SupplierDTO;
import com.diviso.graeshoppe.product.service.mapper.SupplierMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Supplier.
 */
@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

    private final Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);

    private final SupplierRepository supplierRepository;

    private final SupplierMapper supplierMapper;

    private final SupplierSearchRepository supplierSearchRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierMapper supplierMapper, SupplierSearchRepository supplierSearchRepository) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
        this.supplierSearchRepository = supplierSearchRepository;
    }

    /**
     * Save a supplier.
     *
     * @param supplierDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SupplierDTO save(SupplierDTO supplierDTO) {
        log.debug("Request to save Supplier : {}", supplierDTO);
        Supplier supplier1 = supplierMapper.toEntity(supplierDTO);
        
        supplier1 = supplierRepository.save(supplier1);
        supplierSearchRepository.save(supplier1);
        
        Supplier supplier = supplierRepository.save(supplier1);
        SupplierDTO result = supplierMapper.toDto(supplier);
        supplierSearchRepository.save(supplier);
        return result;
    }

    /**
     * Get all the suppliers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SupplierDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Suppliers");
        return supplierRepository.findAll(pageable)
            .map(supplierMapper::toDto);
    }


    /**
     * Get one supplier by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SupplierDTO> findOne(Long id) {
        log.debug("Request to get Supplier : {}", id);
        return supplierRepository.findById(id)
            .map(supplierMapper::toDto);
    }

    /**
     * Delete the supplier by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Supplier : {}", id);
        supplierRepository.deleteById(id);
        supplierSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplier corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SupplierDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Suppliers for query {}", query);
        return supplierSearchRepository.search(queryStringQuery(query), pageable)
            .map(supplierMapper::toDto);
    }
}
