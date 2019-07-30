package com.diviso.graeshoppe.product.service.impl;

import com.diviso.graeshoppe.product.service.StockEntryService;
import com.diviso.graeshoppe.product.domain.StockEntry;
import com.diviso.graeshoppe.product.repository.StockEntryRepository;
import com.diviso.graeshoppe.product.repository.search.StockEntrySearchRepository;
import com.diviso.graeshoppe.product.service.dto.StockEntryDTO;
import com.diviso.graeshoppe.product.service.mapper.StockEntryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing StockEntry.
 */
@Service
@Transactional
public class StockEntryServiceImpl implements StockEntryService {

    private final Logger log = LoggerFactory.getLogger(StockEntryServiceImpl.class);

    private final StockEntryRepository stockEntryRepository;

    private final StockEntryMapper stockEntryMapper;

    private final StockEntrySearchRepository stockEntrySearchRepository;

    public StockEntryServiceImpl(StockEntryRepository stockEntryRepository, StockEntryMapper stockEntryMapper, StockEntrySearchRepository stockEntrySearchRepository) {
        this.stockEntryRepository = stockEntryRepository;
        this.stockEntryMapper = stockEntryMapper;
        this.stockEntrySearchRepository = stockEntrySearchRepository;
    }

    /**
     * Save a stockEntry.
     *
     * @param stockEntryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StockEntryDTO save(StockEntryDTO stockEntryDTO) {
        log.debug("Request to save StockEntry : {}", stockEntryDTO);
        StockEntry stockEntry1 = stockEntryMapper.toEntity(stockEntryDTO);
        
        stockEntry1 = stockEntryRepository.save(stockEntry1);
        stockEntrySearchRepository.save(stockEntry1);
        
        StockEntry stockEntry = stockEntryRepository.save(stockEntry1);
        StockEntryDTO result = stockEntryMapper.toDto(stockEntry);
        stockEntrySearchRepository.save(stockEntry);
        
        return result;
    }

    /**
     * Get all the stockEntries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StockEntryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StockEntries");
        return stockEntryRepository.findAll(pageable)
            .map(stockEntryMapper::toDto);
    }


    /**
     * Get one stockEntry by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockEntryDTO> findOne(Long id) {
        log.debug("Request to get StockEntry : {}", id);
        return stockEntryRepository.findById(id)
            .map(stockEntryMapper::toDto);
    }

    /**
     * Delete the stockEntry by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockEntry : {}", id);
        stockEntryRepository.deleteById(id);
        stockEntrySearchRepository.deleteById(id);
    }

    /**
     * Search for the stockEntry corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StockEntryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StockEntries for query {}", query);
        return stockEntrySearchRepository.search(queryStringQuery(query), pageable)
            .map(stockEntryMapper::toDto);
    }
}
