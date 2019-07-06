package com.diviso.graeshoppe.product.repository.search;

import com.diviso.graeshoppe.product.domain.StockEntry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StockEntry entity.
 */
public interface StockEntrySearchRepository extends ElasticsearchRepository<StockEntry, Long> {
}
