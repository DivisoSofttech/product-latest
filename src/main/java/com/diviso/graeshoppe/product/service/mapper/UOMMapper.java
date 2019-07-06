package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.*;
import com.diviso.graeshoppe.product.service.dto.UOMDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UOM and its DTO UOMDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UOMMapper extends EntityMapper<UOMDTO, UOM> {



    default UOM fromId(Long id) {
        if (id == null) {
            return null;
        }
        UOM uOM = new UOM();
        uOM.setId(id);
        return uOM;
    }
}
