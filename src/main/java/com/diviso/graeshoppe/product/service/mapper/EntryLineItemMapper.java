package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.*;
import com.diviso.graeshoppe.product.service.dto.EntryLineItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EntryLineItem} and its DTO {@link EntryLineItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class, StockEntryMapper.class})
public interface EntryLineItemMapper extends EntityMapper<EntryLineItemDTO, EntryLineItem> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "stockEntry.id", target = "stockEntryId")
    EntryLineItemDTO toDto(EntryLineItem entryLineItem);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "stockEntryId", target = "stockEntry")
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
