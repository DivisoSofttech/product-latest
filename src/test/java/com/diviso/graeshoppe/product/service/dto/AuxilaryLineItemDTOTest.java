package com.diviso.graeshoppe.product.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.product.web.rest.TestUtil;

public class AuxilaryLineItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuxilaryLineItemDTO.class);
        AuxilaryLineItemDTO auxilaryLineItemDTO1 = new AuxilaryLineItemDTO();
        auxilaryLineItemDTO1.setId(1L);
        AuxilaryLineItemDTO auxilaryLineItemDTO2 = new AuxilaryLineItemDTO();
        assertThat(auxilaryLineItemDTO1).isNotEqualTo(auxilaryLineItemDTO2);
        auxilaryLineItemDTO2.setId(auxilaryLineItemDTO1.getId());
        assertThat(auxilaryLineItemDTO1).isEqualTo(auxilaryLineItemDTO2);
        auxilaryLineItemDTO2.setId(2L);
        assertThat(auxilaryLineItemDTO1).isNotEqualTo(auxilaryLineItemDTO2);
        auxilaryLineItemDTO1.setId(null);
        assertThat(auxilaryLineItemDTO1).isNotEqualTo(auxilaryLineItemDTO2);
    }
}
