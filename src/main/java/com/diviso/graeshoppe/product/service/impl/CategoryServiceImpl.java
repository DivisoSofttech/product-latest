package com.diviso.graeshoppe.product.service.impl;

import com.diviso.graeshoppe.product.service.CategoryService;
import com.diviso.graeshoppe.product.service.ImageService;
import com.diviso.graeshoppe.product.domain.Category;
import com.diviso.graeshoppe.product.domain.search.CategorySuggestion;
import com.diviso.graeshoppe.product.repository.CategoryRepository;
import com.diviso.graeshoppe.product.repository.search.CategorySearchRepository;
import com.diviso.graeshoppe.product.service.dto.CategoryDTO;
import com.diviso.graeshoppe.product.service.mapper.CategoryMapper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.completion.Completion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import static org.elasticsearch.index.query.QueryBuilders.*;
import com.diviso.graeshoppe.product.repository.search.CategorySuggestionSearchRepository;
/**
 * Service Implementation for managing Category.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	CategorySuggestionSearchRepository categorySuggestionSearchRepository;
	
	
	
	
    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final CategorySearchRepository categorySearchRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, CategorySearchRepository categorySearchRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.categorySearchRepository = categorySearchRepository;
    }

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
		
        
        String imageLink  = imageService.saveFile("category", UUID.randomUUID().toString(), categoryDTO.getImage());
		category.setImageLink(imageLink);
	
	
        category = categoryRepository.save(category);
        CategorySuggestion  categorySuggestion = new CategorySuggestion();
		categorySuggestion.setId(category.getId());
		Completion completion=new Completion(new String[]{category.getName()});
		completion.setWeight(2);
		categorySuggestion.setSuggest(completion);
        
        
        CategoryDTO result = categoryMapper.toDto(category);
        categorySearchRepository.save(category);
    	categorySuggestionSearchRepository.save(categorySuggestion);
        return updateToEs(result);
    }
    
    @Override
    public CategoryDTO saveForFileUpLoad(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
		
     
	
        category = categoryRepository.save(category);
        CategorySuggestion  categorySuggestion = new CategorySuggestion();
		categorySuggestion.setId(category.getId());
		Completion completion=new Completion(new String[]{category.getName()});
		completion.setWeight(2);
		categorySuggestion.setSuggest(completion);
        
        
        CategoryDTO result = categoryMapper.toDto(category);
        categorySearchRepository.save(category);
    	categorySuggestionSearchRepository.save(categorySuggestion);
        return updateToEs(result);
    }
    public CategoryDTO updateToEs(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
		if(categoryDTO.getImage()!=null) 
		{
        
        String imageLink  = imageService.saveFile("category", UUID.randomUUID().toString(), categoryDTO.getImage());
		category.setImageLink(imageLink);
		
		}
        category = categoryRepository.save(category);
        CategoryDTO result = categoryMapper.toDto(category);
        categorySearchRepository.save(category);
        return result;
    }
    
    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
		
        if(categoryDTO.getImage()!=null) 
		{
        String imageLink  = imageService.saveFile("category", UUID.randomUUID().toString(), categoryDTO.getImage());
		category.setImageLink(imageLink);
	
		}
        category = categoryRepository.save(category);
        CategoryDTO result = categoryMapper.toDto(category);
        categorySearchRepository.save(category);
        return updateToEs(result);
    }
    
    
    /**
     * Get all the categories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return categoryRepository.findAll(pageable)
            .map(categoryMapper::toDto);
    }


    /**
     * Get one category by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryDTO> findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        return categoryRepository.findById(id)
            .map(categoryMapper::toDto);
    }

    /**
     * Delete the category by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.deleteById(id);
        categorySearchRepository.deleteById(id);
    }

    /**
     * Search for the category corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Categories for query {}", query);
        return categorySearchRepository.search(queryStringQuery(query), pageable)
            .map(categoryMapper::toDto);
    }
    
    @Override
	public byte[] exportCategoryListAsPdf(String idpcode) throws JRException {
		

		log.debug("Request to pdf of all category list");

		//JasperReport jr = JasperCompileManager.compileReport("category.jrxml");

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
		JasperPrint jp = JasperFillManager.fillReport("src/main/resources/report/category.jasper", parameters, conn);

		return JasperExportManager.exportReportToPdf(jp);
	}

	@Override
	public Category findByName(String name) {
		return categoryRepository.findByName(name);
	}
    
}
