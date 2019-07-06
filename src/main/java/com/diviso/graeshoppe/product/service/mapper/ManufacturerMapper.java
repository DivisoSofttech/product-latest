package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.*;
import com.diviso.graeshoppe.product.service.dto.ManufacturerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Manufacturer and its DTO ManufacturerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ManufacturerMapper extends EntityMapper<ManufacturerDTO, Manufacturer> {



    default Manufacturer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        return manufacturer;
    }
}
