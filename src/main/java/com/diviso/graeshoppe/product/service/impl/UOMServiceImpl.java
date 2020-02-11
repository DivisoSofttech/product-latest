package com.diviso.graeshoppe.product.service.impl;

import com.diviso.graeshoppe.product.service.UOMService;
import com.diviso.graeshoppe.product.domain.UOM;
import com.diviso.graeshoppe.product.repository.UOMRepository;
import com.diviso.graeshoppe.product.repository.search.UOMSearchRepository;
import com.diviso.graeshoppe.product.service.dto.UOMDTO;
import com.diviso.graeshoppe.product.service.mapper.UOMMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UOM.
 */
@Service
@Transactional
public class UOMServiceImpl implements UOMService {

    private final Logger log = LoggerFactory.getLogger(UOMServiceImpl.class);

    private final UOMRepository uOMRepository;

    private final UOMMapper uOMMapper;

    private final UOMSearchRepository uOMSearchRepository;

    public UOMServiceImpl(UOMRepository uOMRepository, UOMMapper uOMMapper, UOMSearchRepository uOMSearchRepository) {
        this.uOMRepository = uOMRepository;
        this.uOMMapper = uOMMapper;
        this.uOMSearchRepository = uOMSearchRepository;
    }

    /**
     * Save a uOM.
     *
     * @param uOMDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UOMDTO save(UOMDTO uOMDTO) {
        log.debug("Request to save UOM : {}", uOMDTO);
        UOM uOM = uOMMapper.toEntity(uOMDTO);
        uOM = uOMRepository.save(uOM);
        UOMDTO result = uOMMapper.toDto(uOM);
        uOMSearchRepository.save(uOM);
        return updateToEs(result);
    }
    

    private UOMDTO updateToEs(UOMDTO uOMDTO) {
        log.debug("Request to save UOM : {}", uOMDTO);
        UOM uOM = uOMMapper.toEntity(uOMDTO);
        UOMDTO result = uOMMapper.toDto(uOM);
        uOMSearchRepository.save(uOM);
        return result;
    }

    /**
     * Get all the uOMS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UOMDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UOMS");
        return uOMRepository.findAll(pageable)
            .map(uOMMapper::toDto);
    }


    /**
     * Get one uOM by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UOMDTO> findOne(Long id) {
        log.debug("Request to get UOM : {}", id);
        return uOMRepository.findById(id)
            .map(uOMMapper::toDto);
    }

    /**
     * Delete the uOM by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UOM : {}", id);
        uOMRepository.deleteById(id);
        uOMSearchRepository.deleteById(id);
    }

    /**
     * Search for the uOM corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UOMDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UOMS for query {}", query);
        return uOMSearchRepository.search(queryStringQuery(query), pageable)
            .map(uOMMapper::toDto);
    }
}
