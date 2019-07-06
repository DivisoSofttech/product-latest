package com.diviso.graeshoppe.product.repository.search;

import com.diviso.graeshoppe.product.domain.Tax;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Tax entity.
 */
public interface TaxSearchRepository extends ElasticsearchRepository<Tax, Long> {
}
