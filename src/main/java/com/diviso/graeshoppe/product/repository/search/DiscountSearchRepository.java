package com.diviso.graeshoppe.product.repository.search;

import com.diviso.graeshoppe.product.domain.Discount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Discount entity.
 */
public interface DiscountSearchRepository extends ElasticsearchRepository<Discount, Long> {
}
