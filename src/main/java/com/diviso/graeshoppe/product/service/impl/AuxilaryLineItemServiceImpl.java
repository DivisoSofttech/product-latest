package com.diviso.graeshoppe.product.service.impl;

import com.diviso.graeshoppe.product.service.AuxilaryLineItemService;
import com.diviso.graeshoppe.product.domain.AuxilaryLineItem;
import com.diviso.graeshoppe.product.repository.AuxilaryLineItemRepository;
import com.diviso.graeshoppe.product.repository.search.AuxilaryLineItemSearchRepository;
import com.diviso.graeshoppe.product.service.dto.AuxilaryLineItemDTO;
import com.diviso.graeshoppe.product.service.mapper.AuxilaryLineItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AuxilaryLineItem.
 */
@Service
@Transactional
public class AuxilaryLineItemServiceImpl implements AuxilaryLineItemService {

    private final Logger log = LoggerFactory.getLogger(AuxilaryLineItemServiceImpl.class);

    private final AuxilaryLineItemRepository auxilaryLineItemRepository;

    private final AuxilaryLineItemMapper auxilaryLineItemMapper;

    private final AuxilaryLineItemSearchRepository auxilaryLineItemSearchRepository;

    public AuxilaryLineItemServiceImpl(AuxilaryLineItemRepository auxilaryLineItemRepository, AuxilaryLineItemMapper auxilaryLineItemMapper, AuxilaryLineItemSearchRepository auxilaryLineItemSearchRepository) {
        this.auxilaryLineItemRepository = auxilaryLineItemRepository;
        this.auxilaryLineItemMapper = auxilaryLineItemMapper;
        this.auxilaryLineItemSearchRepository = auxilaryLineItemSearchRepository;
    }

    /**
     * Save a auxilaryLineItem.
     *
     * @param auxilaryLineItemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AuxilaryLineItemDTO save(AuxilaryLineItemDTO auxilaryLineItemDTO) {
        log.debug("Request to save AuxilaryLineItem : {}", auxilaryLineItemDTO);
        AuxilaryLineItem auxilaryLineItem = auxilaryLineItemMapper.toEntity(auxilaryLineItemDTO);
        auxilaryLineItem = auxilaryLineItemRepository.save(auxilaryLineItem);
        AuxilaryLineItemDTO result = auxilaryLineItemMapper.toDto(auxilaryLineItem);
        auxilaryLineItemSearchRepository.save(auxilaryLineItem);
        return result;
    }

    /**
     * Get all the auxilaryLineItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AuxilaryLineItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AuxilaryLineItems");
        return auxilaryLineItemRepository.findAll(pageable)
            .map(auxilaryLineItemMapper::toDto);
    }


    /**
     * Get one auxilaryLineItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AuxilaryLineItemDTO> findOne(Long id) {
        log.debug("Request to get AuxilaryLineItem : {}", id);
        return auxilaryLineItemRepository.findById(id)
            .map(auxilaryLineItemMapper::toDto);
    }

    /**
     * Delete the auxilaryLineItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AuxilaryLineItem : {}", id);
        auxilaryLineItemRepository.deleteById(id);
        auxilaryLineItemSearchRepository.deleteById(id);
    }

    /**
     * Search for the auxilaryLineItem corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AuxilaryLineItemDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AuxilaryLineItems for query {}", query);
        return auxilaryLineItemSearchRepository.search(queryStringQuery(query), pageable)
            .map(auxilaryLineItemMapper::toDto);
    }
}
