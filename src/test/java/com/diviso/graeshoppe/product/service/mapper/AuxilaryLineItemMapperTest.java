package com.diviso.graeshoppe.product.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AuxilaryLineItemMapperTest {

    private AuxilaryLineItemMapper auxilaryLineItemMapper;

    @BeforeEach
    public void setUp() {
        auxilaryLineItemMapper = new AuxilaryLineItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(auxilaryLineItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(auxilaryLineItemMapper.fromId(null)).isNull();
    }
}
