package com.diviso.graeshoppe.product.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link SupplierSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SupplierSearchRepositoryMockConfiguration {

    @MockBean
    private SupplierSearchRepository mockSupplierSearchRepository;

}
