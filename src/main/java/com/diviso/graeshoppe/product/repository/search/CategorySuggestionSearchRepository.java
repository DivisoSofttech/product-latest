package com.diviso.graeshoppe.product.repository.search;

import com.diviso.graeshoppe.product.domain.search.CategorySuggestion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Category} entity.
 */
public interface CategorySuggestionSearchRepository extends ElasticsearchRepository<CategorySuggestion, Long> {
}
