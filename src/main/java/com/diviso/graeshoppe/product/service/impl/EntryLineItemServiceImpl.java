package com.diviso.graeshoppe.product.service.impl;

import com.diviso.graeshoppe.product.service.EntryLineItemService;
import com.diviso.graeshoppe.product.service.StockCurrentService;
import com.diviso.graeshoppe.product.domain.EntryLineItem;
import com.diviso.graeshoppe.product.repository.EntryLineItemRepository;
import com.diviso.graeshoppe.product.repository.search.EntryLineItemSearchRepository;
import com.diviso.graeshoppe.product.service.dto.EntryLineItemDTO;
import com.diviso.graeshoppe.product.service.dto.StockCurrentDTO;
import com.diviso.graeshoppe.product.service.mapper.EntryLineItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing EntryLineItem.
 */
@Service
@Transactional
public class EntryLineItemServiceImpl implements EntryLineItemService {

	private final Logger log = LoggerFactory.getLogger(EntryLineItemServiceImpl.class);

	private final EntryLineItemRepository entryLineItemRepository;

	private final EntryLineItemMapper entryLineItemMapper;

	private final StockCurrentService stockCurrentService;

	private final EntryLineItemSearchRepository entryLineItemSearchRepository;

	public EntryLineItemServiceImpl(StockCurrentService stockCurrentService,
			EntryLineItemRepository entryLineItemRepository, EntryLineItemMapper entryLineItemMapper,
			EntryLineItemSearchRepository entryLineItemSearchRepository) {
		this.entryLineItemRepository = entryLineItemRepository;
		this.entryLineItemMapper = entryLineItemMapper;
		this.entryLineItemSearchRepository = entryLineItemSearchRepository;
		this.stockCurrentService = stockCurrentService;
	}

	/**
	 * Save a entryLineItem.
	 *
	 * @param entryLineItemDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public EntryLineItemDTO save(EntryLineItemDTO entryLineItemDTO) {
		log.debug("Request to save EntryLineItem : {}", entryLineItemDTO);
		Optional<StockCurrentDTO> lineItem = stockCurrentService.findByProductId(entryLineItemDTO.getProductId());
		if (lineItem.isPresent()) {
			log.info("Inside if condition main");
			Double quantityUpdated = lineItem.get().getQuantity();
			log.info("quantity current "+quantityUpdated);
			Double priceAdjusted = lineItem.get().getSellPrice();
			log.info("price current "+priceAdjusted);
			if (entryLineItemDTO.getQuantityAdjustment() >= 0) {
				log.info("Quantity djustment is greater than 0 ");
				quantityUpdated += entryLineItemDTO.getQuantityAdjustment();
			log.info("Quantity Adjusted updatedquantity is "+quantityUpdated);
			} else {
				log.info("Quantity djustment is less than 0 ");
				quantityUpdated += entryLineItemDTO.getQuantityAdjustment();
				log.info("Quantity Adjusted updatedquantity is "+quantityUpdated);
			}
			if (entryLineItemDTO.getValueAdjustment() >= 0) {
				log.info(" Value Adjustment is greater than 0 ");
				priceAdjusted += entryLineItemDTO.getValueAdjustment();
				log.info("Value Adjusted priceadjusted is "+priceAdjusted);
			} else {
				log.info(" Value Adjustment is less than 0 ");
				priceAdjusted += entryLineItemDTO.getValueAdjustment();
				log.info("Value Adjusted priceadjusted is "+priceAdjusted);

			}
			lineItem.get().setQuantity(quantityUpdated);
			lineItem.get().setSellPrice(priceAdjusted);
			stockCurrentService.save(lineItem.get());
		}
		EntryLineItem entryLineItem = entryLineItemMapper.toEntity(entryLineItemDTO);
		entryLineItem = entryLineItemRepository.save(entryLineItem);
		EntryLineItemDTO result = entryLineItemMapper.toDto(entryLineItem);
		entryLineItemSearchRepository.save(entryLineItem);
		return result;
	}

	/**
	 * Get all the entryLineItems.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<EntryLineItemDTO> findAll(Pageable pageable) {
		log.debug("Request to get all EntryLineItems");
		return entryLineItemRepository.findAll(pageable).map(entryLineItemMapper::toDto);
	}

	/**
	 * Get one entryLineItem by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<EntryLineItemDTO> findOne(Long id) {
		log.debug("Request to get EntryLineItem : {}", id);
		return entryLineItemRepository.findById(id).map(entryLineItemMapper::toDto);
	}

	/**
	 * Delete the entryLineItem by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete EntryLineItem : {}", id);
		entryLineItemRepository.deleteById(id);
		entryLineItemSearchRepository.deleteById(id);
	}

	/**
	 * Search for the entryLineItem corresponding to the query.
	 *
	 * @param query    the query of the search
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<EntryLineItemDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of EntryLineItems for query {}", query);
		return entryLineItemSearchRepository.search(queryStringQuery(query), pageable).map(entryLineItemMapper::toDto);
	}
}
