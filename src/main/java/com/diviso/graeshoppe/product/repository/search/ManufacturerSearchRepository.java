package com.diviso.graeshoppe.product.repository.search;

import com.diviso.graeshoppe.product.domain.Manufacturer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Manufacturer entity.
 */
public interface ManufacturerSearchRepository extends ElasticsearchRepository<Manufacturer, Long> {
}
