package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.*;
import com.diviso.graeshoppe.product.service.dto.ComboLineItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ComboLineItem and its DTO ComboLineItemDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ComboLineItemMapper extends EntityMapper<ComboLineItemDTO, ComboLineItem> {

    @Override
	@Mapping(source = "product.id", target = "productId")
    @Mapping(source = "comboItem.id", target = "comboItemId")
    ComboLineItemDTO toDto(ComboLineItem comboLineItem);

    @Override
	@Mapping(source = "productId", target = "product")
    @Mapping(source = "comboItemId", target = "comboItem")
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
