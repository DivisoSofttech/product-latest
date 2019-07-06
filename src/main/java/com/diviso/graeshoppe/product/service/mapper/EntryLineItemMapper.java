package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.*;
import com.diviso.graeshoppe.product.service.dto.EntryLineItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EntryLineItem and its DTO EntryLineItemDTO.
 */
@Mapper(componentModel = "spring", uses = {StockEntryMapper.class, ProductMapper.class})
public interface EntryLineItemMapper extends EntityMapper<EntryLineItemDTO, EntryLineItem> {

    @Override
	@Mapping(source = "stockEntry.id", target = "stockEntryId")
    @Mapping(source = "product.id", target = "productId")
    EntryLineItemDTO toDto(EntryLineItem entryLineItem);

    @Override
	@Mapping(source = "stockEntryId", target = "stockEntry")
    @Mapping(source = "productId", target = "product")
    EntryLineItem toEntity(EntryLineItemDTO entryLineItemDTO);

    default EntryLineItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        EntryLineItem entryLineItem = new EntryLineItem();
        entryLineItem.setId(id);
        return entryLineItem;
    }
}
