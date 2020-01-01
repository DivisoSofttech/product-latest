package com.diviso.graeshoppe.product.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ComboLineItemMapperTest {

    private ComboLineItemMapper comboLineItemMapper;

    @BeforeEach
    public void setUp() {
        comboLineItemMapper = new ComboLineItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(comboLineItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(comboLineItemMapper.fromId(null)).isNull();
    }
}
