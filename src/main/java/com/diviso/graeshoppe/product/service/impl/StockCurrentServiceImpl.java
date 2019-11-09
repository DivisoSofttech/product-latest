package com.diviso.graeshoppe.product.service.impl;

import com.diviso.graeshoppe.product.service.StockCurrentService;
import com.diviso.graeshoppe.product.domain.StockCurrent;
import com.diviso.graeshoppe.product.repository.StockCurrentRepository;
import com.diviso.graeshoppe.product.repository.search.StockCurrentSearchRepository;
import com.diviso.graeshoppe.product.service.dto.StockCurrentDTO;
import com.diviso.graeshoppe.product.service.mapper.StockCurrentMapper;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.sql.DataSource;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing StockCurrent.
 */
@Service
@Transactional
public class StockCurrentServiceImpl implements StockCurrentService {

    private final Logger log = LoggerFactory.getLogger(StockCurrentServiceImpl.class);

    @Autowired
    DataSource dataSource;
    
    private final StockCurrentRepository stockCurrentRepository;

    private final StockCurrentMapper stockCurrentMapper;

    private final StockCurrentSearchRepository stockCurrentSearchRepository;

    public StockCurrentServiceImpl(StockCurrentRepository stockCurrentRepository, StockCurrentMapper stockCurrentMapper, StockCurrentSearchRepository stockCurrentSearchRepository) {
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
    public StockCurrentDTO save(StockCurrentDTO stockCurrentDTO){
        log.debug("Request to save StockCurrent : {}", stockCurrentDTO);
        StockCurrent stockCurrent = stockCurrentMapper.toEntity(stockCurrentDTO);
        stockCurrent = stockCurrentRepository.save(stockCurrent);
        StockCurrentDTO result = stockCurrentMapper.toDto(stockCurrent);
        stockCurrentSearchRepository.save(stockCurrent);
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
        return stockCurrentRepository.findAll(pageable)
            .map(stockCurrentMapper::toDto);
    }



    /**
     *  get all the stockCurrents where Product is null.
     *  @return the list of entities
     */
    @Override
	@Transactional(readOnly = true) 
    public List<StockCurrentDTO> findAllWhereProductIsNull() {
        log.debug("Request to get all stockCurrents where Product is null");
        return StreamSupport
            .stream(stockCurrentRepository.findAll().spliterator(), false)
            .filter(stockCurrent -> stockCurrent.getProduct() == null)
            .map(stockCurrentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
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
        return stockCurrentRepository.findById(id)
            .map(stockCurrentMapper::toDto);
    }

    /**
     * Delete the stockCurrent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockCurrent : {}", id);        stockCurrentRepository.deleteById(id);
        stockCurrentSearchRepository.deleteById(id);
    }

    /**
     * Search for the stockCurrent corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StockCurrentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StockCurrents for query {}", query);
        return stockCurrentSearchRepository.search(queryStringQuery(query), pageable)
            .map(stockCurrentMapper::toDto);
    }

	@Override
	@Transactional(readOnly = true)
	public Optional<StockCurrentDTO> findByProductId(Long id) {
		// TODO Auto-generated method stub
		log.debug("Request to get StockCurrent : {}", id);
		return stockCurrentRepository.findByProductId(id)
	            .map(stockCurrentMapper::toDto);
	}
	
	@Override
	 public  StockCurrentDTO updateStockCurrent( StockCurrentDTO stockCurrentDTO)  {
	        log.debug("REST request to update StockCurrent : {}", stockCurrentDTO);
	        if (stockCurrentDTO.getId() == null) {
	        	
	        	System.out.println("<<<<<<<<<<<<<<<EXCEPTION>>>>>>>>>>>>>>>>>>>>>>>>");
	            //throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
	        }
	    	System.out.println("<<<<<<<<<<<<<<<update stock current>>>>>>>>>>>>>>>>>>>>>>>>");
	        
	        return save(stockCurrentDTO);
		/*
		 * return ResponseEntity.ok()
		 * .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME,
		 * stockCurrentDTO.getId().toString())) .body(result);
		 */
	    }

	@Override
	public byte[] exportStockCurrentAsPdf(String idpcode) throws JRException {
		log.debug("Request to pdf of all stock current list");

		//JasperReport jr = JasperCompileManager.compileReport("product.jrxml");

		// Preparing parameters
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("i_d_pcode", idpcode);

		Connection conn = null;

		try {
			conn = dataSource.getConnection();

			// System.out.println(conn.getClientInfo()+"-----------------------"+conn.getMetaData().getURL()+"_________________________________");

		} catch (SQLException e) {
			e.printStackTrace();

		}
		JasperPrint jp = JasperFillManager.fillReport("src/main/resources/report/stockcurrent.jasper", parameters, conn);

		return JasperExportManager.exportReportToPdf(jp);
	}

	
	
	
	
}
