package com.diviso.graeshoppe.product.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class StockEntryMapperTest {

    private StockEntryMapper stockEntryMapper;

    @BeforeEach
    public void setUp() {
        stockEntryMapper = new StockEntryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(stockEntryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stockEntryMapper.fromId(null)).isNull();
    }
}
