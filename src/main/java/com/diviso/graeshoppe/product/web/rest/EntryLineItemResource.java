package com.diviso.graeshoppe.product.web.rest;
import com.diviso.graeshoppe.product.service.EntryLineItemService;
import com.diviso.graeshoppe.product.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import com.diviso.graeshoppe.product.service.dto.EntryLineItemDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EntryLineItem.
 */
@RestController
@RequestMapping("/api")
public class EntryLineItemResource {

    private final Logger log = LoggerFactory.getLogger(EntryLineItemResource.class);

    private static final String ENTITY_NAME = "productEntryLineItem";

    private final EntryLineItemService entryLineItemService;
    
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public EntryLineItemResource(EntryLineItemService entryLineItemService) {
        this.entryLineItemService = entryLineItemService;
    }

    /**
     * POST  /entry-line-items : Create a new entryLineItem.
     *
     * @param entryLineItemDTO the entryLineItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entryLineItemDTO, or with status 400 (Bad Request) if the entryLineItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entry-line-items")
    public ResponseEntity<EntryLineItemDTO> createEntryLineItem(@RequestBody EntryLineItemDTO entryLineItemDTO) throws URISyntaxException {
        log.debug("REST request to save EntryLineItem : {}", entryLineItemDTO);
        if (entryLineItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new entryLineItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntryLineItemDTO result = entryLineItemService.save(entryLineItemDTO);
        return ResponseEntity.created(new URI("/api/entry-line-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entry-line-items : Updates an existing entryLineItem.
     *
     * @param entryLineItemDTO the entryLineItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entryLineItemDTO,
     * or with status 400 (Bad Request) if the entryLineItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the entryLineItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entry-line-items")
    public ResponseEntity<EntryLineItemDTO> updateEntryLineItem(@RequestBody EntryLineItemDTO entryLineItemDTO) throws URISyntaxException {
        log.debug("REST request to update EntryLineItem : {}", entryLineItemDTO);
        if (entryLineItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntryLineItemDTO result = entryLineItemService.save(entryLineItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entryLineItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entry-line-items : get all the entryLineItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of entryLineItems in body
     */
    @GetMapping("/entry-line-items")
    public ResponseEntity<List<EntryLineItemDTO>> getAllEntryLineItems(Pageable pageable) {
        log.debug("REST request to get a page of EntryLineItems");
        Page<EntryLineItemDTO> page = entryLineItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /entry-line-items/:id : get the "id" entryLineItem.
     *
     * @param id the id of the entryLineItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entryLineItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/entry-line-items/{id}")
    public ResponseEntity<EntryLineItemDTO> getEntryLineItem(@PathVariable Long id) {
        log.debug("REST request to get EntryLineItem : {}", id);
        Optional<EntryLineItemDTO> entryLineItemDTO = entryLineItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entryLineItemDTO);
    }

    /**
     * DELETE  /entry-line-items/:id : delete the "id" entryLineItem.
     *
     * @param id the id of the entryLineItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entry-line-items/{id}")
    public ResponseEntity<Void> deleteEntryLineItem(@PathVariable Long id) {
        log.debug("REST request to delete EntryLineItem : {}", id);
        entryLineItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/entry-line-items?query=:query : search for the entryLineItem corresponding
     * to the query.
     *
     * @param query the query of the entryLineItem search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/entry-line-items")
    public ResponseEntity<List<EntryLineItemDTO>> searchEntryLineItems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EntryLineItems for query {}", query);
        Page<EntryLineItemDTO> page = entryLineItemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
