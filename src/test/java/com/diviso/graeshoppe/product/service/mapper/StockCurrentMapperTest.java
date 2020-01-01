package com.diviso.graeshoppe.product.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class StockCurrentMapperTest {

    private StockCurrentMapper stockCurrentMapper;

    @BeforeEach
    public void setUp() {
        stockCurrentMapper = new StockCurrentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(stockCurrentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stockCurrentMapper.fromId(null)).isNull();
    }
}
