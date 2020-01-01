package com.diviso.graeshoppe.product.repository.search;

import com.diviso.graeshoppe.product.domain.StockCurrent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link StockCurrent} entity.
 */
public interface StockCurrentSearchRepository extends ElasticsearchRepository<StockCurrent, Long> {
}
