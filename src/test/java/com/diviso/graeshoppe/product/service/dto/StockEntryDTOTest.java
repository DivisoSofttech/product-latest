package com.diviso.graeshoppe.product.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.product.web.rest.TestUtil;

public class StockEntryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockEntryDTO.class);
        StockEntryDTO stockEntryDTO1 = new StockEntryDTO();
        stockEntryDTO1.setId(1L);
        StockEntryDTO stockEntryDTO2 = new StockEntryDTO();
        assertThat(stockEntryDTO1).isNotEqualTo(stockEntryDTO2);
        stockEntryDTO2.setId(stockEntryDTO1.getId());
        assertThat(stockEntryDTO1).isEqualTo(stockEntryDTO2);
        stockEntryDTO2.setId(2L);
        assertThat(stockEntryDTO1).isNotEqualTo(stockEntryDTO2);
        stockEntryDTO1.setId(null);
        assertThat(stockEntryDTO1).isNotEqualTo(stockEntryDTO2);
    }
}
