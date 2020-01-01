package com.diviso.graeshoppe.product.web.rest;
import com.diviso.graeshoppe.product.service.ReasonService;
import com.diviso.graeshoppe.product.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import com.diviso.graeshoppe.product.service.dto.ReasonDTO;
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
 * REST controller for managing Reason.
 */
@RestController
@RequestMapping("/api")
public class ReasonResource {

    private final Logger log = LoggerFactory.getLogger(ReasonResource.class);

    private static final String ENTITY_NAME = "productReason";

    private final ReasonService reasonService;

    
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    public ReasonResource(ReasonService reasonService) {
        this.reasonService = reasonService;
    }

    /**
     * POST  /reasons : Create a new reason.
     *
     * @param reasonDTO the reasonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reasonDTO, or with status 400 (Bad Request) if the reason has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reasons")
    public ResponseEntity<ReasonDTO> createReason(@RequestBody ReasonDTO reasonDTO) throws URISyntaxException {
        log.debug("REST request to save Reason : {}", reasonDTO);
        if (reasonDTO.getId() != null) {
            throw new BadRequestAlertException("A new reason cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReasonDTO result = reasonService.save(reasonDTO);
        return ResponseEntity.created(new URI("/api/reasons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true,ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reasons : Updates an existing reason.
     *
     * @param reasonDTO the reasonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reasonDTO,
     * or with status 400 (Bad Request) if the reasonDTO is not valid,
     * or with status 500 (Internal Server Error) if the reasonDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reasons")
    public ResponseEntity<ReasonDTO> updateReason(@RequestBody ReasonDTO reasonDTO) throws URISyntaxException {
        log.debug("REST request to update Reason : {}", reasonDTO);
        if (reasonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReasonDTO result = reasonService.save(reasonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reasonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reasons : get all the reasons.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reasons in body
     */
    @GetMapping("/reasons")
    public ResponseEntity<List<ReasonDTO>> getAllReasons(Pageable pageable) {
        log.debug("REST request to get a page of Reasons");
        Page<ReasonDTO> page = reasonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /reasons/:id : get the "id" reason.
     *
     * @param id the id of the reasonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reasonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reasons/{id}")
    public ResponseEntity<ReasonDTO> getReason(@PathVariable Long id) {
        log.debug("REST request to get Reason : {}", id);
        Optional<ReasonDTO> reasonDTO = reasonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reasonDTO);
    }

    /**
     * DELETE  /reasons/:id : delete the "id" reason.
     *
     * @param id the id of the reasonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reasons/{id}")
    public ResponseEntity<Void> deleteReason(@PathVariable Long id) {
        log.debug("REST request to delete Reason : {}", id);
        reasonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME,id.toString())).build();
    }

    /**
     * SEARCH  /_search/reasons?query=:query : search for the reason corresponding
     * to the query.
     *
     * @param query the query of the reason search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/reasons")
    public ResponseEntity<List<ReasonDTO>> searchReasons(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Reasons for query {}", query);
        Page<ReasonDTO> page = reasonService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
