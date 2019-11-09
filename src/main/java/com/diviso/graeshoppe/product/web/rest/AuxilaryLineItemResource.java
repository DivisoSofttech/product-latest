package com.diviso.graeshoppe.product.web.rest;

import com.diviso.graeshoppe.product.domain.AuxilaryLineItem;
import com.diviso.graeshoppe.product.service.AuxilaryLineItemService;
import com.diviso.graeshoppe.product.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.product.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.product.web.rest.util.PaginationUtil;

import com.diviso.graeshoppe.product.service.dto.AuxilaryLineItemDTO;
import com.diviso.graeshoppe.product.service.mapper.AuxilaryLineItemMapper;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AuxilaryLineItem.
 */
@RestController
@RequestMapping("/api")
public class AuxilaryLineItemResource {

	private final Logger log = LoggerFactory.getLogger(AuxilaryLineItemResource.class);

	private static final String ENTITY_NAME = "productAuxilaryLineItem";

	private final AuxilaryLineItemService auxilaryLineItemService;

	@Autowired
	AuxilaryLineItemMapper auxilaryLineItemsMapper;

	public AuxilaryLineItemResource(AuxilaryLineItemService auxilaryLineItemService) {
		this.auxilaryLineItemService = auxilaryLineItemService;
	}

	/**
	 * POST /auxilary-line-items : Create a new auxilaryLineItem.
	 *
	 * @param auxilaryLineItemDTO
	 *            the auxilaryLineItemDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new auxilaryLineItemDTO, or with status 400 (Bad Request) if the
	 *         auxilaryLineItem has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/auxilary-line-items")
	public ResponseEntity<AuxilaryLineItemDTO> createAuxilaryLineItem(@RequestBody AuxilaryLineItemDTO auxilaryLineItemDTO) throws URISyntaxException {
	        log.debug("REST request to save Banner : {}", auxilaryLineItemDTO);
	        if (auxilaryLineItemDTO.getId() != null) {
	            throw new BadRequestAlertException("A new banner cannot already have an ID", ENTITY_NAME, "idexists");
	        }
	        AuxilaryLineItemDTO result1 = auxilaryLineItemService.save(auxilaryLineItemDTO);
			if (result1.getId() == null) {
				throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
			}
			AuxilaryLineItemDTO result = auxilaryLineItemService.save(result1);
	        return ResponseEntity.created(new URI("/api/auxilary-line-items/" + result.getId()))
	            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
	            .body(result);	}

	/**
	 * PUT /auxilary-line-items : Updates an existing auxilaryLineItem.
	 *
	 * @param auxilaryLineItemDTO
	 *            the auxilaryLineItemDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         auxilaryLineItemDTO, or with status 400 (Bad Request) if the
	 *         auxilaryLineItemDTO is not valid, or with status 500 (Internal
	 *         Server Error) if the auxilaryLineItemDTO couldn't be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/auxilary-line-items")
	public ResponseEntity<AuxilaryLineItemDTO> updateAuxilaryLineItem(
			@RequestBody AuxilaryLineItemDTO auxilaryLineItemDTO) throws URISyntaxException {
		log.debug("REST request to update AuxilaryLineItem : {}", auxilaryLineItemDTO);
		if (auxilaryLineItemDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		AuxilaryLineItemDTO result = auxilaryLineItemService.save(auxilaryLineItemDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auxilaryLineItemDTO.getId().toString()))
				.body(result);
	}

	/**
	 * GET /auxilary-line-items : get all the auxilaryLineItems.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         auxilaryLineItems in body
	 */
	@GetMapping("/auxilary-line-items")
	public ResponseEntity<List<AuxilaryLineItemDTO>> getAllAuxilaryLineItems(Pageable pageable) {
		log.debug("REST request to get a page of AuxilaryLineItems");
		Page<AuxilaryLineItemDTO> page = auxilaryLineItemService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/auxilary-line-items");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * GET /auxilary-line-items/:id : get the "id" auxilaryLineItem.
	 *
	 * @param id
	 *            the id of the auxilaryLineItemDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         auxilaryLineItemDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/auxilary-line-items/{id}")
	public ResponseEntity<AuxilaryLineItemDTO> getAuxilaryLineItem(@PathVariable Long id) {
		log.debug("REST request to get AuxilaryLineItem : {}", id);
		Optional<AuxilaryLineItemDTO> auxilaryLineItemDTO = auxilaryLineItemService.findOne(id);
		return ResponseUtil.wrapOrNotFound(auxilaryLineItemDTO);
	}

	/**
	 * DELETE /auxilary-line-items/:id : delete the "id" auxilaryLineItem.
	 *
	 * @param id
	 *            the id of the auxilaryLineItemDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/auxilary-line-items/{id}")
	public ResponseEntity<Void> deleteAuxilaryLineItem(@PathVariable Long id) {
		log.debug("REST request to delete AuxilaryLineItem : {}", id);
		auxilaryLineItemService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	/**
	 * SEARCH /_search/auxilary-line-items?query=:query : search for the
	 * auxilaryLineItem corresponding to the query.
	 *
	 * @param query
	 *            the query of the auxilaryLineItem search
	 * @param pageable
	 *            the pagination information
	 * @return the result of the search
	 */
	@GetMapping("/_search/auxilary-line-items")
	public ResponseEntity<List<AuxilaryLineItemDTO>> searchAuxilaryLineItems(@RequestParam String query,
			Pageable pageable) {
		log.debug("REST request to search for a page of AuxilaryLineItems for query {}", query);
		Page<AuxilaryLineItemDTO> page = auxilaryLineItemService.search(query, pageable);
		HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page,
				"/api/_search/auxilary-line-items");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@PostMapping("/auxilary-line-items/toDto")
	public ResponseEntity<List<AuxilaryLineItemDTO>> listToDto(@RequestBody List<AuxilaryLineItem> auxilaryLineItems) {
		log.debug("REST request to convert to DTO");
		List<AuxilaryLineItemDTO> dtos = new ArrayList<>();
		auxilaryLineItems.forEach(a -> {
			dtos.add(auxilaryLineItemsMapper.toDto(a));
		});
		return ResponseEntity.ok().body(dtos);
	}
}
