package com.diviso.graeshoppe.product.repository.search;

import com.diviso.graeshoppe.product.domain.Supplier;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Supplier entity.
 */
public interface SupplierSearchRepository extends ElasticsearchRepository<Supplier, Long> {
}
