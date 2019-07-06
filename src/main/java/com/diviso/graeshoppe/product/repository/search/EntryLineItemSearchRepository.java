package com.diviso.graeshoppe.product.repository.search;

import com.diviso.graeshoppe.product.domain.EntryLineItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EntryLineItem entity.
 */
public interface EntryLineItemSearchRepository extends ElasticsearchRepository<EntryLineItem, Long> {
}
