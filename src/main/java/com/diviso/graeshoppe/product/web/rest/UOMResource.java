package com.diviso.graeshoppe.product.web.rest;
import com.diviso.graeshoppe.product.service.UOMService;
import com.diviso.graeshoppe.product.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import com.diviso.graeshoppe.product.service.dto.UOMDTO;
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
 * REST controller for managing UOM.
 */
@RestController
@RequestMapping("/api")
public class UOMResource {

    private final Logger log = LoggerFactory.getLogger(UOMResource.class);

    private static final String ENTITY_NAME = "productUom";

    private final UOMService uOMService;
    
	
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public UOMResource(UOMService uOMService) {
        this.uOMService = uOMService;
    }

    /**
     * POST  /uoms : Create a new uOM.
     *
     * @param uOMDTO the uOMDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uOMDTO, or with status 400 (Bad Request) if the uOM has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/uoms")
    public ResponseEntity<UOMDTO> createUOM(@RequestBody UOMDTO uOMDTO) throws URISyntaxException {
        log.debug("REST request to save UOM : {}", uOMDTO);
        if (uOMDTO.getId() != null) {
            throw new BadRequestAlertException("A new uOM cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UOMDTO result = uOMService.save(uOMDTO);
        return ResponseEntity.created(new URI("/api/uoms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true,ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /uoms : Updates an existing uOM.
     *
     * @param uOMDTO the uOMDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uOMDTO,
     * or with status 400 (Bad Request) if the uOMDTO is not valid,
     * or with status 500 (Internal Server Error) if the uOMDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/uoms")
    public ResponseEntity<UOMDTO> updateUOM(@RequestBody UOMDTO uOMDTO) throws URISyntaxException {
        log.debug("REST request to update UOM : {}", uOMDTO);
        if (uOMDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UOMDTO result = uOMService.save(uOMDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true,ENTITY_NAME,uOMDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /uoms : get all the uOMS.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of uOMS in body
     */
    @GetMapping("/uoms")
    public ResponseEntity<List<UOMDTO>> getAllUOMS(Pageable pageable) {
        log.debug("REST request to get a page of UOMS");
        Page<UOMDTO> page = uOMService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /uoms/:id : get the "id" uOM.
     *
     * @param id the id of the uOMDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uOMDTO, or with status 404 (Not Found)
     */
    @GetMapping("/uoms/{id}")
    public ResponseEntity<UOMDTO> getUOM(@PathVariable Long id) {
        log.debug("REST request to get UOM : {}", id);
        Optional<UOMDTO> uOMDTO = uOMService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uOMDTO);
    }

    /**
     * DELETE  /uoms/:id : delete the "id" uOM.
     *
     * @param id the id of the uOMDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/uoms/{id}")
    public ResponseEntity<Void> deleteUOM(@PathVariable Long id) {
        log.debug("REST request to delete UOM : {}", id);
        uOMService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true,ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/uoms?query=:query : search for the uOM corresponding
     * to the query.
     *
     * @param query the query of the uOM search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/uoms")
    public ResponseEntity<List<UOMDTO>> searchUOMS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UOMS for query {}", query);
        Page<UOMDTO> page = uOMService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
