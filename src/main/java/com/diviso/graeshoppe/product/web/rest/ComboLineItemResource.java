package com.diviso.graeshoppe.product.web.rest;
import com.diviso.graeshoppe.product.service.ComboLineItemService;
import com.diviso.graeshoppe.product.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.product.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.product.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.product.service.dto.ComboLineItemDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ComboLineItem.
 */
@RestController
@RequestMapping("/api")
public class ComboLineItemResource {

    private final Logger log = LoggerFactory.getLogger(ComboLineItemResource.class);

    private static final String ENTITY_NAME = "productComboLineItem";

    private final ComboLineItemService comboLineItemService;

    public ComboLineItemResource(ComboLineItemService comboLineItemService) {
        this.comboLineItemService = comboLineItemService;
    }

    /**
     * POST  /combo-line-items : Create a new comboLineItem.
     *
     * @param comboLineItemDTO the comboLineItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comboLineItemDTO, or with status 400 (Bad Request) if the comboLineItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/combo-line-items")
    public ResponseEntity<ComboLineItemDTO> createComboLineItem(@RequestBody ComboLineItemDTO comboLineItemDTO) throws URISyntaxException {
        log.debug("REST request to save ComboLineItem : {}", comboLineItemDTO);
        if (comboLineItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new comboLineItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComboLineItemDTO result = comboLineItemService.save(comboLineItemDTO);
        return ResponseEntity.created(new URI("/api/combo-line-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /combo-line-items : Updates an existing comboLineItem.
     *
     * @param comboLineItemDTO the comboLineItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comboLineItemDTO,
     * or with status 400 (Bad Request) if the comboLineItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the comboLineItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/combo-line-items")
    public ResponseEntity<ComboLineItemDTO> updateComboLineItem(@RequestBody ComboLineItemDTO comboLineItemDTO) throws URISyntaxException {
        log.debug("REST request to update ComboLineItem : {}", comboLineItemDTO);
        if (comboLineItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComboLineItemDTO result = comboLineItemService.save(comboLineItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, comboLineItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /combo-line-items : get all the comboLineItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of comboLineItems in body
     */
    @GetMapping("/combo-line-items")
    public ResponseEntity<List<ComboLineItemDTO>> getAllComboLineItems(Pageable pageable) {
        log.debug("REST request to get a page of ComboLineItems");
        Page<ComboLineItemDTO> page = comboLineItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/combo-line-items");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /combo-line-items/:id : get the "id" comboLineItem.
     *
     * @param id the id of the comboLineItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comboLineItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/combo-line-items/{id}")
    public ResponseEntity<ComboLineItemDTO> getComboLineItem(@PathVariable Long id) {
        log.debug("REST request to get ComboLineItem : {}", id);
        Optional<ComboLineItemDTO> comboLineItemDTO = comboLineItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comboLineItemDTO);
    }

    /**
     * DELETE  /combo-line-items/:id : delete the "id" comboLineItem.
     *
     * @param id the id of the comboLineItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/combo-line-items/{id}")
    public ResponseEntity<Void> deleteComboLineItem(@PathVariable Long id) {
        log.debug("REST request to delete ComboLineItem : {}", id);
        comboLineItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/combo-line-items?query=:query : search for the comboLineItem corresponding
     * to the query.
     *
     * @param query the query of the comboLineItem search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/combo-line-items")
    public ResponseEntity<List<ComboLineItemDTO>> searchComboLineItems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ComboLineItems for query {}", query);
        Page<ComboLineItemDTO> page = comboLineItemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/combo-line-items");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
