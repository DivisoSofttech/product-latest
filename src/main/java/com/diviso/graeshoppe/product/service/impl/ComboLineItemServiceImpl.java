package com.diviso.graeshoppe.product.service.impl;

import com.diviso.graeshoppe.product.service.ComboLineItemService;
import com.diviso.graeshoppe.product.domain.ComboLineItem;
import com.diviso.graeshoppe.product.repository.ComboLineItemRepository;
import com.diviso.graeshoppe.product.repository.search.ComboLineItemSearchRepository;
import com.diviso.graeshoppe.product.service.dto.ComboLineItemDTO;
import com.diviso.graeshoppe.product.service.mapper.ComboLineItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ComboLineItem.
 */
@Service
@Transactional
public class ComboLineItemServiceImpl implements ComboLineItemService {

	private final Logger log = LoggerFactory.getLogger(ComboLineItemServiceImpl.class);

	private final ComboLineItemRepository comboLineItemRepository;

	private final ComboLineItemMapper comboLineItemMapper;

	private final ComboLineItemSearchRepository comboLineItemSearchRepository;

	public ComboLineItemServiceImpl(ComboLineItemRepository comboLineItemRepository,
			ComboLineItemMapper comboLineItemMapper, ComboLineItemSearchRepository comboLineItemSearchRepository) {
		this.comboLineItemRepository = comboLineItemRepository;
		this.comboLineItemMapper = comboLineItemMapper;
		this.comboLineItemSearchRepository = comboLineItemSearchRepository;
	}

	/**
	 * Save a comboLineItem.
	 *
	 * @param comboLineItemDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	@Override
	public ComboLineItemDTO save(ComboLineItemDTO comboLineItemDTO) {
		log.debug("Request to save ComboLineItem : {}", comboLineItemDTO);

		ComboLineItem comboLineItem1 = comboLineItemMapper.toEntity(comboLineItemDTO);
		comboLineItem1 = comboLineItemRepository.save(comboLineItem1);
		ComboLineItemDTO result = comboLineItemMapper.toDto(comboLineItem1);
		comboLineItemSearchRepository.save(comboLineItem1);

		return updateToEs(result);
	}
	
	private ComboLineItemDTO updateToEs(ComboLineItemDTO comboLineItemDTO) {
		log.debug("Request to save ComboLineItem : {}", comboLineItemDTO);

		ComboLineItem comboLineItem1 = comboLineItemMapper.toEntity(comboLineItemDTO);
		comboLineItem1 = comboLineItemRepository.save(comboLineItem1);
		ComboLineItemDTO result = comboLineItemMapper.toDto(comboLineItem1);
		comboLineItemSearchRepository.save(comboLineItem1);

		return result;
	}

	/**
	 * Get all the comboLineItems.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<ComboLineItemDTO> findAll(Pageable pageable) {
		log.debug("Request to get all ComboLineItems");
		return comboLineItemRepository.findAll(pageable).map(comboLineItemMapper::toDto);
	}

	/**
	 * Get one comboLineItem by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<ComboLineItemDTO> findOne(Long id) {
		log.debug("Request to get ComboLineItem : {}", id);
		return comboLineItemRepository.findById(id).map(comboLineItemMapper::toDto);
	}

	/**
	 * Delete the comboLineItem by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete ComboLineItem : {}", id);
		comboLineItemRepository.deleteById(id);
		comboLineItemSearchRepository.deleteById(id);
	}

	/**
	 * Search for the comboLineItem corresponding to the query.
	 *
	 * @param query
	 *            the query of the search
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<ComboLineItemDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of ComboLineItems for query {}", query);
		return comboLineItemSearchRepository.search(queryStringQuery(query), pageable).map(comboLineItemMapper::toDto);
	}
}
