package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.*;
import com.diviso.graeshoppe.product.service.dto.SupplierDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Supplier} and its DTO {@link SupplierDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContactMapper.class, AddressMapper.class})
public interface SupplierMapper extends EntityMapper<SupplierDTO, Supplier> {

    @Mapping(source = "contact.id", target = "contactId")
    @Mapping(source = "address.id", target = "addressId")
    SupplierDTO toDto(Supplier supplier);

    @Mapping(source = "contactId", target = "contact")
    @Mapping(source = "addressId", target = "address")
    Supplier toEntity(SupplierDTO supplierDTO);

    default Supplier fromId(Long id) {
        if (id == null) {
            return null;
        }
        Supplier supplier = new Supplier();
        supplier.setId(id);
        return supplier;
    }
}
