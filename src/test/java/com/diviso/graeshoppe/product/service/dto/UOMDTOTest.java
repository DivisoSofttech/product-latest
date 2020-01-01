package com.diviso.graeshoppe.product.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.product.web.rest.TestUtil;

public class UOMDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UOMDTO.class);
        UOMDTO uOMDTO1 = new UOMDTO();
        uOMDTO1.setId(1L);
        UOMDTO uOMDTO2 = new UOMDTO();
        assertThat(uOMDTO1).isNotEqualTo(uOMDTO2);
        uOMDTO2.setId(uOMDTO1.getId());
        assertThat(uOMDTO1).isEqualTo(uOMDTO2);
        uOMDTO2.setId(2L);
        assertThat(uOMDTO1).isNotEqualTo(uOMDTO2);
        uOMDTO1.setId(null);
        assertThat(uOMDTO1).isNotEqualTo(uOMDTO2);
    }
}
