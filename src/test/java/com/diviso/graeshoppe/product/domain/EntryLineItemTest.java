package com.diviso.graeshoppe.product.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.product.web.rest.TestUtil;

public class EntryLineItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntryLineItem.class);
        EntryLineItem entryLineItem1 = new EntryLineItem();
        entryLineItem1.setId(1L);
        EntryLineItem entryLineItem2 = new EntryLineItem();
        entryLineItem2.setId(entryLineItem1.getId());
        assertThat(entryLineItem1).isEqualTo(entryLineItem2);
        entryLineItem2.setId(2L);
        assertThat(entryLineItem1).isNotEqualTo(entryLineItem2);
        entryLineItem1.setId(null);
        assertThat(entryLineItem1).isNotEqualTo(entryLineItem2);
    }
}
