package com.diviso.graeshoppe.product.web.rest;
import com.diviso.graeshoppe.product.service.StockEntryService;
import com.diviso.graeshoppe.product.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.product.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.product.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.product.service.dto.StockEntryDTO;
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
 * REST controller for managing StockEntry.
 */
@RestController
@RequestMapping("/api")
public class StockEntryResource {

    private final Logger log = LoggerFactory.getLogger(StockEntryResource.class);

    private static final String ENTITY_NAME = "productStockEntry";

    private final StockEntryService stockEntryService;

    public StockEntryResource(StockEntryService stockEntryService) {
        this.stockEntryService = stockEntryService;
    }

    /**
     * POST  /stock-entries : Create a new stockEntry.
     *
     * @param stockEntryDTO the stockEntryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stockEntryDTO, or with status 400 (Bad Request) if the stockEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stock-entries")
    public ResponseEntity<StockEntryDTO> createStockEntry(@RequestBody StockEntryDTO stockEntryDTO) throws URISyntaxException {
        log.debug("REST request to save StockEntry : {}", stockEntryDTO);
        if (stockEntryDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockEntryDTO result = stockEntryService.save(stockEntryDTO);
        return ResponseEntity.created(new URI("/api/stock-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stock-entries : Updates an existing stockEntry.
     *
     * @param stockEntryDTO the stockEntryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stockEntryDTO,
     * or with status 400 (Bad Request) if the stockEntryDTO is not valid,
     * or with status 500 (Internal Server Error) if the stockEntryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stock-entries")
    public ResponseEntity<StockEntryDTO> updateStockEntry(@RequestBody StockEntryDTO stockEntryDTO) throws URISyntaxException {
        log.debug("REST request to update StockEntry : {}", stockEntryDTO);
        if (stockEntryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockEntryDTO result = stockEntryService.save(stockEntryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stockEntryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stock-entries : get all the stockEntries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of stockEntries in body
     */
    @GetMapping("/stock-entries")
    public ResponseEntity<List<StockEntryDTO>> getAllStockEntries(Pageable pageable) {
        log.debug("REST request to get a page of StockEntries");
        Page<StockEntryDTO> page = stockEntryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stock-entries");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /stock-entries/:id : get the "id" stockEntry.
     *
     * @param id the id of the stockEntryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stockEntryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stock-entries/{id}")
    public ResponseEntity<StockEntryDTO> getStockEntry(@PathVariable Long id) {
        log.debug("REST request to get StockEntry : {}", id);
        Optional<StockEntryDTO> stockEntryDTO = stockEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockEntryDTO);
    }

    /**
     * DELETE  /stock-entries/:id : delete the "id" stockEntry.
     *
     * @param id the id of the stockEntryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stock-entries/{id}")
    public ResponseEntity<Void> deleteStockEntry(@PathVariable Long id) {
        log.debug("REST request to delete StockEntry : {}", id);
        stockEntryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/stock-entries?query=:query : search for the stockEntry corresponding
     * to the query.
     *
     * @param query the query of the stockEntry search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/stock-entries")
    public ResponseEntity<List<StockEntryDTO>> searchStockEntries(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StockEntries for query {}", query);
        Page<StockEntryDTO> page = stockEntryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/stock-entries");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
