package com.diviso.graeshoppe.product.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DiscountMapperTest {

    private DiscountMapper discountMapper;

    @BeforeEach
    public void setUp() {
        discountMapper = new DiscountMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(discountMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(discountMapper.fromId(null)).isNull();
    }
}
