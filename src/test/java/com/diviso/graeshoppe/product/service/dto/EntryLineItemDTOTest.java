package com.diviso.graeshoppe.product.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.product.web.rest.TestUtil;

public class EntryLineItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntryLineItemDTO.class);
        EntryLineItemDTO entryLineItemDTO1 = new EntryLineItemDTO();
        entryLineItemDTO1.setId(1L);
        EntryLineItemDTO entryLineItemDTO2 = new EntryLineItemDTO();
        assertThat(entryLineItemDTO1).isNotEqualTo(entryLineItemDTO2);
        entryLineItemDTO2.setId(entryLineItemDTO1.getId());
        assertThat(entryLineItemDTO1).isEqualTo(entryLineItemDTO2);
        entryLineItemDTO2.setId(2L);
        assertThat(entryLineItemDTO1).isNotEqualTo(entryLineItemDTO2);
        entryLineItemDTO1.setId(null);
        assertThat(entryLineItemDTO1).isNotEqualTo(entryLineItemDTO2);
    }
}
