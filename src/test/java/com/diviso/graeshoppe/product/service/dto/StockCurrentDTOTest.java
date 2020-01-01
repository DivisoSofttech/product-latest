package com.diviso.graeshoppe.product.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.product.web.rest.TestUtil;

public class StockCurrentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockCurrentDTO.class);
        StockCurrentDTO stockCurrentDTO1 = new StockCurrentDTO();
        stockCurrentDTO1.setId(1L);
        StockCurrentDTO stockCurrentDTO2 = new StockCurrentDTO();
        assertThat(stockCurrentDTO1).isNotEqualTo(stockCurrentDTO2);
        stockCurrentDTO2.setId(stockCurrentDTO1.getId());
        assertThat(stockCurrentDTO1).isEqualTo(stockCurrentDTO2);
        stockCurrentDTO2.setId(2L);
        assertThat(stockCurrentDTO1).isNotEqualTo(stockCurrentDTO2);
        stockCurrentDTO1.setId(null);
        assertThat(stockCurrentDTO1).isNotEqualTo(stockCurrentDTO2);
    }
}
