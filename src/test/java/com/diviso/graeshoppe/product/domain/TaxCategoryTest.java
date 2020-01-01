package com.diviso.graeshoppe.product.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.product.web.rest.TestUtil;

public class TaxCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxCategory.class);
        TaxCategory taxCategory1 = new TaxCategory();
        taxCategory1.setId(1L);
        TaxCategory taxCategory2 = new TaxCategory();
        taxCategory2.setId(taxCategory1.getId());
        assertThat(taxCategory1).isEqualTo(taxCategory2);
        taxCategory2.setId(2L);
        assertThat(taxCategory1).isNotEqualTo(taxCategory2);
        taxCategory1.setId(null);
        assertThat(taxCategory1).isNotEqualTo(taxCategory2);
    }
}
