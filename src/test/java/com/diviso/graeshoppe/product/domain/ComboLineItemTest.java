package com.diviso.graeshoppe.product.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.product.web.rest.TestUtil;

public class ComboLineItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComboLineItem.class);
        ComboLineItem comboLineItem1 = new ComboLineItem();
        comboLineItem1.setId(1L);
        ComboLineItem comboLineItem2 = new ComboLineItem();
        comboLineItem2.setId(comboLineItem1.getId());
        assertThat(comboLineItem1).isEqualTo(comboLineItem2);
        comboLineItem2.setId(2L);
        assertThat(comboLineItem1).isNotEqualTo(comboLineItem2);
        comboLineItem1.setId(null);
        assertThat(comboLineItem1).isNotEqualTo(comboLineItem2);
    }
}
