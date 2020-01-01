package com.diviso.graeshoppe.product.repository.search;

import com.diviso.graeshoppe.product.domain.Tax;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Tax} entity.
 */
public interface TaxSearchRepository extends ElasticsearchRepository<Tax, Long> {
}
