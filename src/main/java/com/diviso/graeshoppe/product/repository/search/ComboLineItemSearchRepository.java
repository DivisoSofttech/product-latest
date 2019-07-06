package com.diviso.graeshoppe.product.repository.search;

import com.diviso.graeshoppe.product.domain.ComboLineItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ComboLineItem entity.
 */
public interface ComboLineItemSearchRepository extends ElasticsearchRepository<ComboLineItem, Long> {
}
