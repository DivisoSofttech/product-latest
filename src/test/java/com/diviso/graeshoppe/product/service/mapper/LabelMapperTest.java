package com.diviso.graeshoppe.product.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class LabelMapperTest {

    private LabelMapper labelMapper;

    @BeforeEach
    public void setUp() {
        labelMapper = new LabelMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(labelMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(labelMapper.fromId(null)).isNull();
    }
}
