package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.*;
import com.diviso.graeshoppe.product.service.dto.StockEntryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StockEntry} and its DTO {@link StockEntryDTO}.
 */
@Mapper(componentModel = "spring", uses = {ReasonMapper.class, LocationMapper.class})
public interface StockEntryMapper extends EntityMapper<StockEntryDTO, StockEntry> {

    @Mapping(source = "reason.id", target = "reasonId")
    @Mapping(source = "location.id", target = "locationId")
    StockEntryDTO toDto(StockEntry stockEntry);

    @Mapping(target = "entryLineItems", ignore = true)
    @Mapping(target = "removeEntryLineItems", ignore = true)
    @Mapping(source = "reasonId", target = "reason")
    @Mapping(source = "locationId", target = "location")
    StockEntry toEntity(StockEntryDTO stockEntryDTO);

    default StockEntry fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockEntry stockEntry = new StockEntry();
        stockEntry.setId(id);
        return stockEntry;
    }
}
