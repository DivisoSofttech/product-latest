package com.diviso.graeshoppe.product.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.product.web.rest.TestUtil;

public class AuxilaryLineItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuxilaryLineItem.class);
        AuxilaryLineItem auxilaryLineItem1 = new AuxilaryLineItem();
        auxilaryLineItem1.setId(1L);
        AuxilaryLineItem auxilaryLineItem2 = new AuxilaryLineItem();
        auxilaryLineItem2.setId(auxilaryLineItem1.getId());
        assertThat(auxilaryLineItem1).isEqualTo(auxilaryLineItem2);
        auxilaryLineItem2.setId(2L);
        assertThat(auxilaryLineItem1).isNotEqualTo(auxilaryLineItem2);
        auxilaryLineItem1.setId(null);
        assertThat(auxilaryLineItem1).isNotEqualTo(auxilaryLineItem2);
    }
}
