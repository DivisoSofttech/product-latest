package com.diviso.graeshoppe.product.repository.search;

import com.diviso.graeshoppe.product.domain.Address;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Address entity.
 */
public interface AddressSearchRepository extends ElasticsearchRepository<Address, Long> {
}
