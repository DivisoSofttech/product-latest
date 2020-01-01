package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.*;
import com.diviso.graeshoppe.product.service.dto.AuxilaryLineItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AuxilaryLineItem} and its DTO {@link AuxilaryLineItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface AuxilaryLineItemMapper extends EntityMapper<AuxilaryLineItemDTO, AuxilaryLineItem> {

    @Mapping(source = "auxilaryItem.id", target = "auxilaryItemId")
    @Mapping(source = "product.id", target = "productId")
    AuxilaryLineItemDTO toDto(AuxilaryLineItem auxilaryLineItem);

    @Mapping(source = "auxilaryItemId", target = "auxilaryItem")
    @Mapping(source = "productId", target = "product")
    AuxilaryLineItem toEntity(AuxilaryLineItemDTO auxilaryLineItemDTO);

    default AuxilaryLineItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        AuxilaryLineItem auxilaryLineItem = new AuxilaryLineItem();
        auxilaryLineItem.setId(id);
        return auxilaryLineItem;
    }
}
