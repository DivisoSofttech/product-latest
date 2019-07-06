package com.diviso.graeshoppe.product.web.rest;
import com.diviso.graeshoppe.product.service.SupplierService;
import com.diviso.graeshoppe.product.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.product.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.product.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.product.service.dto.SupplierDTO;
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
 * REST controller for managing Supplier.
 */
@RestController
@RequestMapping("/api")
public class SupplierResource {

    private final Logger log = LoggerFactory.getLogger(SupplierResource.class);

    private static final String ENTITY_NAME = "productSupplier";

    private final SupplierService supplierService;

    public SupplierResource(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    /**
     * POST  /suppliers : Create a new supplier.
     *
     * @param supplierDTO the supplierDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supplierDTO, or with status 400 (Bad Request) if the supplier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/suppliers")
    public ResponseEntity<SupplierDTO> createSupplier(@RequestBody SupplierDTO supplierDTO) throws URISyntaxException {
        log.debug("REST request to save Supplier : {}", supplierDTO);
        if (supplierDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplierDTO result = supplierService.save(supplierDTO);
        return ResponseEntity.created(new URI("/api/suppliers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /suppliers : Updates an existing supplier.
     *
     * @param supplierDTO the supplierDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supplierDTO,
     * or with status 400 (Bad Request) if the supplierDTO is not valid,
     * or with status 500 (Internal Server Error) if the supplierDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/suppliers")
    public ResponseEntity<SupplierDTO> updateSupplier(@RequestBody SupplierDTO supplierDTO) throws URISyntaxException {
        log.debug("REST request to update Supplier : {}", supplierDTO);
        if (supplierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplierDTO result = supplierService.save(supplierDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplierDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /suppliers : get all the suppliers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of suppliers in body
     */
    @GetMapping("/suppliers")
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers(Pageable pageable) {
        log.debug("REST request to get a page of Suppliers");
        Page<SupplierDTO> page = supplierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/suppliers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /suppliers/:id : get the "id" supplier.
     *
     * @param id the id of the supplierDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supplierDTO, or with status 404 (Not Found)
     */
    @GetMapping("/suppliers/{id}")
    public ResponseEntity<SupplierDTO> getSupplier(@PathVariable Long id) {
        log.debug("REST request to get Supplier : {}", id);
        Optional<SupplierDTO> supplierDTO = supplierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplierDTO);
    }

    /**
     * DELETE  /suppliers/:id : delete the "id" supplier.
     *
     * @param id the id of the supplierDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/suppliers/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        log.debug("REST request to delete Supplier : {}", id);
        supplierService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/suppliers?query=:query : search for the supplier corresponding
     * to the query.
     *
     * @param query the query of the supplier search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/suppliers")
    public ResponseEntity<List<SupplierDTO>> searchSuppliers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Suppliers for query {}", query);
        Page<SupplierDTO> page = supplierService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/suppliers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
