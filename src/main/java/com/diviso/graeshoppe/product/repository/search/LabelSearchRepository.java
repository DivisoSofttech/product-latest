package com.diviso.graeshoppe.product.repository.search;

import com.diviso.graeshoppe.product.domain.Label;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Label entity.
 */
public interface LabelSearchRepository extends ElasticsearchRepository<Label, Long> {
}
