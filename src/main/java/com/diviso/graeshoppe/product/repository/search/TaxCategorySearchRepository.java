package com.diviso.graeshoppe.product.repository.search;

import com.diviso.graeshoppe.product.domain.TaxCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TaxCategory} entity.
 */
public interface TaxCategorySearchRepository extends ElasticsearchRepository<TaxCategory, Long> {
}
