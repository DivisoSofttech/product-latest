package com.diviso.graeshoppe.product.repository.search;

import com.diviso.graeshoppe.product.domain.Reason;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Reason entity.
 */
public interface ReasonSearchRepository extends ElasticsearchRepository<Reason, Long> {
}
