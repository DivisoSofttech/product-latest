package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.*;
import com.diviso.graeshoppe.product.service.dto.StockCurrentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StockCurrent} and its DTO {@link StockCurrentDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface StockCurrentMapper extends EntityMapper<StockCurrentDTO, StockCurrent> {

    @Mapping(source = "product.id", target = "productId")
    StockCurrentDTO toDto(StockCurrent stockCurrent);

    @Mapping(source = "productId", target = "product")
    StockCurrent toEntity(StockCurrentDTO stockCurrentDTO);

    default StockCurrent fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockCurrent stockCurrent = new StockCurrent();
        stockCurrent.setId(id);
        return stockCurrent;
    }
}
