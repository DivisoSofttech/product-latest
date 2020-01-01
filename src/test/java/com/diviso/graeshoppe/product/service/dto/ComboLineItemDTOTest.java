package com.diviso.graeshoppe.product.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.product.web.rest.TestUtil;

public class ComboLineItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComboLineItemDTO.class);
        ComboLineItemDTO comboLineItemDTO1 = new ComboLineItemDTO();
        comboLineItemDTO1.setId(1L);
        ComboLineItemDTO comboLineItemDTO2 = new ComboLineItemDTO();
        assertThat(comboLineItemDTO1).isNotEqualTo(comboLineItemDTO2);
        comboLineItemDTO2.setId(comboLineItemDTO1.getId());
        assertThat(comboLineItemDTO1).isEqualTo(comboLineItemDTO2);
        comboLineItemDTO2.setId(2L);
        assertThat(comboLineItemDTO1).isNotEqualTo(comboLineItemDTO2);
        comboLineItemDTO1.setId(null);
        assertThat(comboLineItemDTO1).isNotEqualTo(comboLineItemDTO2);
    }
}
