package com.diviso.graeshoppe.product.repository.search;

import com.diviso.graeshoppe.product.domain.UOM;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UOM entity.
 */
public interface UOMSearchRepository extends ElasticsearchRepository<UOM, Long> {
}
