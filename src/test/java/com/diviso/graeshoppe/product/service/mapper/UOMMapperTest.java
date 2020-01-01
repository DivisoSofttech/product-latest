package com.diviso.graeshoppe.product.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class UOMMapperTest {

    private UOMMapper uOMMapper;

    @BeforeEach
    public void setUp() {
        uOMMapper = new UOMMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(uOMMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(uOMMapper.fromId(null)).isNull();
    }
}
