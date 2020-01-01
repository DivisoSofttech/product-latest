package com.diviso.graeshoppe.product.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.product.web.rest.TestUtil;

public class StockEntryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockEntry.class);
        StockEntry stockEntry1 = new StockEntry();
        stockEntry1.setId(1L);
        StockEntry stockEntry2 = new StockEntry();
        stockEntry2.setId(stockEntry1.getId());
        assertThat(stockEntry1).isEqualTo(stockEntry2);
        stockEntry2.setId(2L);
        assertThat(stockEntry1).isNotEqualTo(stockEntry2);
        stockEntry1.setId(null);
        assertThat(stockEntry1).isNotEqualTo(stockEntry2);
    }
}
