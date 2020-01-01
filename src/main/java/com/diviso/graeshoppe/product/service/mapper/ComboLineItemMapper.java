package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.*;
import com.diviso.graeshoppe.product.service.dto.ComboLineItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ComboLineItem} and its DTO {@link ComboLineItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ComboLineItemMapper extends EntityMapper<ComboLineItemDTO, ComboLineItem> {

    @Mapping(source = "comboItem.id", target = "comboItemId")
    @Mapping(source = "product.id", target = "productId")
    ComboLineItemDTO toDto(ComboLineItem comboLineItem);

    @Mapping(source = "comboItemId", target = "comboItem")
    @Mapping(source = "productId", target = "product")
    ComboLineItem toEntity(ComboLineItemDTO comboLineItemDTO);

    default ComboLineItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        ComboLineItem comboLineItem = new ComboLineItem();
        comboLineItem.setId(id);
        return comboLineItem;
    }
}
