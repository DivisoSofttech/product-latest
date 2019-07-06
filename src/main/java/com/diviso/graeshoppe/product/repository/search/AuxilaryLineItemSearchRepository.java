package com.diviso.graeshoppe.product.repository.search;

import com.diviso.graeshoppe.product.domain.AuxilaryLineItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuxilaryLineItem entity.
 */
public interface AuxilaryLineItemSearchRepository extends ElasticsearchRepository<AuxilaryLineItem, Long> {
}
