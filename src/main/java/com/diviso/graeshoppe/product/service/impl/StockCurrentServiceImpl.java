package com.diviso.graeshoppe.product.service.impl;

import com.diviso.graeshoppe.product.service.StockCurrentService;
import com.diviso.graeshoppe.product.domain.StockCurrent;
import com.diviso.graeshoppe.product.repository.StockCurrentRepository;
import com.diviso.graeshoppe.product.repository.search.StockCurrentSearchRepository;
import com.diviso.graeshoppe.product.service.dto.StockCurrentDTO;
import com.diviso.graeshoppe.product.service.mapper.StockCurrentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing StockCurrent.
 */
@Service
@Transactional
public class StockCurrentServiceImpl implements StockCurrentService {

	private final Logger log = LoggerFactory.getLogger(StockCurrentServiceImpl.class);

	private final StockCurrentRepository stockCurrentRepository;

	private final StockCurrentMapper stockCurrentMapper;

	private final StockCurrentSearchRepository stockCurrentSearchRepository;

	public StockCurrentServiceImpl(StockCurrentRepository stockCurrentRepository, StockCurrentMapper stockCurrentMapper,
			StockCurrentSearchRepository stockCurrentSearchRepository) {
		this.stockCurrentRepository = stockCurrentRepository;
		this.stockCurrentMapper = stockCurrentMapper;
		this.stockCurrentSearchRepository = stockCurrentSearchRepository;
	}

	/**
	 * Save a stockCurrent.
	 *
	 * @param stockCurrentDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public StockCurrentDTO save(StockCurrentDTO stockCurrentDTO) {
		log.debug("Request to save StockCurrent : {}", stockCurrentDTO);
		StockCurrent stockCurrent = stockCurrentMapper.toEntity(stockCurrentDTO);
		stockCurrent = stockCurrentRepository.save(stockCurrent);
		StockCurrentDTO result = stockCurrentMapper.toDto(stockCurrent);
		stockCurrentSearchRepository.save(stockCurrent);
		return result;
	}

	/**
	 * Get all the stockCurrents.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<StockCurrentDTO> findAll(Pageable pageable) {
		log.debug("Request to get all StockCurrents");
		return stockCurrentRepository.findAll(pageable).map(stockCurrentMapper::toDto);
	}

	/**
	 * Get one stockCurrent by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<StockCurrentDTO> findOne(Long id) {
		log.debug("Request to get StockCurrent : {}", id);
		return stockCurrentRepository.findById(id).map(stockCurrentMapper::toDto);
	}

	/**
	 * Delete the stockCurrent by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete StockCurrent : {}", id);
		stockCurrentRepository.deleteById(id);
		stockCurrentSearchRepository.deleteById(id);
	}

	/**
	 * Search for the stockCurrent corresponding to the query.
	 *
	 * @param query    the query of the search
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<StockCurrentDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of StockCurrents for query {}", query);
		return stockCurrentSearchRepository.search(queryStringQuery(query), pageable).map(stockCurrentMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<StockCurrentDTO> findByProductId(Long productId) {
		log.info("request to find the stockcurrent by productid " + productId);
		return stockCurrentRepository.findByProduct_Id(productId).map(stockCurrentMapper::toDto);
	}
}
