package com.diviso.graeshoppe.product.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class EntryLineItemMapperTest {

    private EntryLineItemMapper entryLineItemMapper;

    @BeforeEach
    public void setUp() {
        entryLineItemMapper = new EntryLineItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(entryLineItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(entryLineItemMapper.fromId(null)).isNull();
    }
}
