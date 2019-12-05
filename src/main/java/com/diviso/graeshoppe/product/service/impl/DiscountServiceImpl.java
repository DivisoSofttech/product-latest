package com.diviso.graeshoppe.product.service.impl;

import com.diviso.graeshoppe.product.service.DiscountService;
import com.diviso.graeshoppe.product.domain.Discount;
import com.diviso.graeshoppe.product.repository.DiscountRepository;
import com.diviso.graeshoppe.product.repository.search.DiscountSearchRepository;
import com.diviso.graeshoppe.product.service.dto.DiscountDTO;
import com.diviso.graeshoppe.product.service.mapper.DiscountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Discount.
 */
@Service
@Transactional
public class DiscountServiceImpl implements DiscountService {

    private final Logger log = LoggerFactory.getLogger(DiscountServiceImpl.class);

    private final DiscountRepository discountRepository;

    private final DiscountMapper discountMapper;

    private final DiscountSearchRepository discountSearchRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository, DiscountMapper discountMapper, DiscountSearchRepository discountSearchRepository) {
        this.discountRepository = discountRepository;
        this.discountMapper = discountMapper;
        this.discountSearchRepository = discountSearchRepository;
    }

    /**
     * Save a discount.
     *
     * @param discountDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DiscountDTO save(DiscountDTO discountDTO) {
        log.debug("Request to save Discount : {}", discountDTO);
        Discount discount = discountMapper.toEntity(discountDTO);
        discount = discountRepository.save(discount);
        DiscountDTO result = discountMapper.toDto(discount);
        discountSearchRepository.save(discount);
        return updateToEs(result);
    }
    
    private DiscountDTO updateToEs(DiscountDTO discountDTO) {
        log.debug("Request to save Discount : {}", discountDTO);
        Discount discount = discountMapper.toEntity(discountDTO);
        discount = discountRepository.save(discount);
        DiscountDTO result = discountMapper.toDto(discount);
        discountSearchRepository.save(discount);
        return result;
    }

    /**
     * Get all the discounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DiscountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Discounts");
        return discountRepository.findAll(pageable)
            .map(discountMapper::toDto);
    }


    /**
     * Get one discount by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DiscountDTO> findOne(Long id) {
        log.debug("Request to get Discount : {}", id);
        return discountRepository.findById(id)
            .map(discountMapper::toDto);
    }

    /**
     * Delete the discount by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Discount : {}", id);
        discountRepository.deleteById(id);
        discountSearchRepository.deleteById(id);
    }

    /**
     * Search for the discount corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DiscountDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Discounts for query {}", query);
        return discountSearchRepository.search(queryStringQuery(query), pageable)
            .map(discountMapper::toDto);
    }
}
