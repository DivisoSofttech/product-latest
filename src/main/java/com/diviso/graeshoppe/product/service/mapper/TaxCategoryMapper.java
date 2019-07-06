package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.*;
import com.diviso.graeshoppe.product.service.dto.TaxCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TaxCategory and its DTO TaxCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaxCategoryMapper extends EntityMapper<TaxCategoryDTO, TaxCategory> {


    @Override
	@Mapping(target = "taxes", ignore = true)
    TaxCategory toEntity(TaxCategoryDTO taxCategoryDTO);

    default TaxCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaxCategory taxCategory = new TaxCategory();
        taxCategory.setId(id);
        return taxCategory;
    }
}
