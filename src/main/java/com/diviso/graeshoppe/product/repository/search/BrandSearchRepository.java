package com.diviso.graeshoppe.product.repository.search;

import com.diviso.graeshoppe.product.domain.Brand;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Brand entity.
 */
public interface BrandSearchRepository extends ElasticsearchRepository<Brand, Long> {
}
