package com.diviso.graeshoppe.product.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.product.web.rest.TestUtil;

public class TaxCategoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxCategoryDTO.class);
        TaxCategoryDTO taxCategoryDTO1 = new TaxCategoryDTO();
        taxCategoryDTO1.setId(1L);
        TaxCategoryDTO taxCategoryDTO2 = new TaxCategoryDTO();
        assertThat(taxCategoryDTO1).isNotEqualTo(taxCategoryDTO2);
        taxCategoryDTO2.setId(taxCategoryDTO1.getId());
        assertThat(taxCategoryDTO1).isEqualTo(taxCategoryDTO2);
        taxCategoryDTO2.setId(2L);
        assertThat(taxCategoryDTO1).isNotEqualTo(taxCategoryDTO2);
        taxCategoryDTO1.setId(null);
        assertThat(taxCategoryDTO1).isNotEqualTo(taxCategoryDTO2);
    }
}
