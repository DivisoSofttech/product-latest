package com.diviso.graeshoppe.product.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TaxCategoryMapperTest {

    private TaxCategoryMapper taxCategoryMapper;

    @BeforeEach
    public void setUp() {
        taxCategoryMapper = new TaxCategoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(taxCategoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taxCategoryMapper.fromId(null)).isNull();
    }
}
