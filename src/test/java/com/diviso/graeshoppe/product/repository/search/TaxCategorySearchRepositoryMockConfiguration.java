package com.diviso.graeshoppe.product.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link TaxCategorySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TaxCategorySearchRepositoryMockConfiguration {

    @MockBean
    private TaxCategorySearchRepository mockTaxCategorySearchRepository;

}
