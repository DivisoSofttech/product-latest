package com.diviso.graeshoppe.product.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.product.web.rest.TestUtil;

public class StockCurrentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockCurrent.class);
        StockCurrent stockCurrent1 = new StockCurrent();
        stockCurrent1.setId(1L);
        StockCurrent stockCurrent2 = new StockCurrent();
        stockCurrent2.setId(stockCurrent1.getId());
        assertThat(stockCurrent1).isEqualTo(stockCurrent2);
        stockCurrent2.setId(2L);
        assertThat(stockCurrent1).isNotEqualTo(stockCurrent2);
        stockCurrent1.setId(null);
        assertThat(stockCurrent1).isNotEqualTo(stockCurrent2);
    }
}
