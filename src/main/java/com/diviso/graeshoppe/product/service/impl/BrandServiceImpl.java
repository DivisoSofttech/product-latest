package com.diviso.graeshoppe.product.service.impl;

import com.diviso.graeshoppe.product.service.BrandService;
import com.diviso.graeshoppe.product.domain.Brand;
import com.diviso.graeshoppe.product.repository.BrandRepository;
import com.diviso.graeshoppe.product.repository.search.BrandSearchRepository;
import com.diviso.graeshoppe.product.service.dto.BrandDTO;
import com.diviso.graeshoppe.product.service.mapper.BrandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Brand.
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    private final Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);

    private final BrandRepository brandRepository;

    private final BrandMapper brandMapper;

    private final BrandSearchRepository brandSearchRepository;

    public BrandServiceImpl(BrandRepository brandRepository, BrandMapper brandMapper, BrandSearchRepository brandSearchRepository) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
        this.brandSearchRepository = brandSearchRepository;
    }

    /**
     * Save a brand.
     *
     * @param brandDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BrandDTO save(BrandDTO brandDTO) {
        log.debug("Request to save Brand : {}", brandDTO);
        Brand brand = brandMapper.toEntity(brandDTO);
        brand = brandRepository.save(brand);
        BrandDTO result = brandMapper.toDto(brand);
        brandSearchRepository.save(brand);
        return updateToEs(result);
    }
    
    
    private BrandDTO updateToEs(BrandDTO brandDTO) {
        log.debug("Request to save Brand : {}", brandDTO);
        Brand brand = brandMapper.toEntity(brandDTO);
        BrandDTO result = brandMapper.toDto(brand);
        brandSearchRepository.save(brand);
        return result;
    }

    /**
     * Get all the brands.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BrandDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Brands");
        return brandRepository.findAll(pageable)
            .map(brandMapper::toDto);
    }


    /**
     * Get one brand by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BrandDTO> findOne(Long id) {
        log.debug("Request to get Brand : {}", id);
        return brandRepository.findById(id)
            .map(brandMapper::toDto);
    }

    /**
     * Delete the brand by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Brand : {}", id);
        brandRepository.deleteById(id);
        brandSearchRepository.deleteById(id);
    }

    /**
     * Search for the brand corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BrandDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Brands for query {}", query);
        return brandSearchRepository.search(queryStringQuery(query), pageable)
            .map(brandMapper::toDto);
    }
}
