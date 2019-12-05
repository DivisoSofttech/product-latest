package com.diviso.graeshoppe.product.service.impl;

import com.diviso.graeshoppe.product.service.ReasonService;
import com.diviso.graeshoppe.product.domain.Reason;
import com.diviso.graeshoppe.product.repository.ReasonRepository;
import com.diviso.graeshoppe.product.repository.search.ReasonSearchRepository;
import com.diviso.graeshoppe.product.service.dto.ReasonDTO;
import com.diviso.graeshoppe.product.service.mapper.ReasonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Reason.
 */
@Service
@Transactional
public class ReasonServiceImpl implements ReasonService {

    private final Logger log = LoggerFactory.getLogger(ReasonServiceImpl.class);

    private final ReasonRepository reasonRepository;

    private final ReasonMapper reasonMapper;

    private final ReasonSearchRepository reasonSearchRepository;

    public ReasonServiceImpl(ReasonRepository reasonRepository, ReasonMapper reasonMapper, ReasonSearchRepository reasonSearchRepository) {
        this.reasonRepository = reasonRepository;
        this.reasonMapper = reasonMapper;
        this.reasonSearchRepository = reasonSearchRepository;
    }

    /**
     * Save a reason.
     *
     * @param reasonDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReasonDTO save(ReasonDTO reasonDTO) {
        log.debug("Request to save Reason : {}", reasonDTO);
        Reason reason = reasonMapper.toEntity(reasonDTO);
        reason = reasonRepository.save(reason);
        ReasonDTO result = reasonMapper.toDto(reason);
        reasonSearchRepository.save(reason);
        return updateToEs(result);
    }
    
    private ReasonDTO updateToEs(ReasonDTO reasonDTO) {
        log.debug("Request to save Reason : {}", reasonDTO);
        Reason reason = reasonMapper.toEntity(reasonDTO);
        reason = reasonRepository.save(reason);
        ReasonDTO result = reasonMapper.toDto(reason);
        reasonSearchRepository.save(reason);
        return result;
    }

    /**
     * Get all the reasons.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReasonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reasons");
        return reasonRepository.findAll(pageable)
            .map(reasonMapper::toDto);
    }


    /**
     * Get one reason by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReasonDTO> findOne(Long id) {
        log.debug("Request to get Reason : {}", id);
        return reasonRepository.findById(id)
            .map(reasonMapper::toDto);
    }

    /**
     * Delete the reason by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reason : {}", id);
        reasonRepository.deleteById(id);
        reasonSearchRepository.deleteById(id);
    }

    /**
     * Search for the reason corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReasonDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Reasons for query {}", query);
        return reasonSearchRepository.search(queryStringQuery(query), pageable)
            .map(reasonMapper::toDto);
    }
}
