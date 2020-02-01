package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.Address;
import com.diviso.graeshoppe.product.domain.Contact;
import com.diviso.graeshoppe.product.domain.Supplier;
import com.diviso.graeshoppe.product.service.dto.SupplierDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-01T13:02:32+0530",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_222 (Private Build)"
)
@Component
public class SupplierMapperImpl implements SupplierMapper {

    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Supplier> toEntity(List<SupplierDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Supplier> list = new ArrayList<Supplier>( dtoList.size() );
        for ( SupplierDTO supplierDTO : dtoList ) {
            list.add( toEntity( supplierDTO ) );
        }

        return list;
    }

    @Override
    public List<SupplierDTO> toDto(List<Supplier> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SupplierDTO> list = new ArrayList<SupplierDTO>( entityList.size() );
        for ( Supplier supplier : entityList ) {
            list.add( toDto( supplier ) );
        }

        return list;
    }

    @Override
    public SupplierDTO toDto(Supplier supplier) {
        if ( supplier == null ) {
            return null;
        }

        SupplierDTO supplierDTO = new SupplierDTO();

        supplierDTO.setContactId( supplierContactId( supplier ) );
        supplierDTO.setAddressId( supplierAddressId( supplier ) );
        supplierDTO.setId( supplier.getId() );
        supplierDTO.setiDPcode( supplier.getiDPcode() );
        supplierDTO.setCompanyName( supplier.getCompanyName() );
        supplierDTO.setCreditLimit( supplier.getCreditLimit() );
        supplierDTO.setCurrentDebt( supplier.getCurrentDebt() );
        supplierDTO.setDebtDate( supplier.getDebtDate() );
        supplierDTO.setVisible( supplier.isVisible() );

        return supplierDTO;
    }

    @Override
    public Supplier toEntity(SupplierDTO supplierDTO) {
        if ( supplierDTO == null ) {
            return null;
        }

        Supplier supplier = new Supplier();

        supplier.setAddress( addressMapper.fromId( supplierDTO.getAddressId() ) );
        supplier.setContact( contactMapper.fromId( supplierDTO.getContactId() ) );
        supplier.setId( supplierDTO.getId() );
        supplier.setiDPcode( supplierDTO.getiDPcode() );
        supplier.setCompanyName( supplierDTO.getCompanyName() );
        supplier.setCreditLimit( supplierDTO.getCreditLimit() );
        supplier.setCurrentDebt( supplierDTO.getCurrentDebt() );
        supplier.setDebtDate( supplierDTO.getDebtDate() );
        supplier.setVisible( supplierDTO.isVisible() );

        return supplier;
    }

    private Long supplierContactId(Supplier supplier) {
        if ( supplier == null ) {
            return null;
        }
        Contact contact = supplier.getContact();
        if ( contact == null ) {
            return null;
        }
        Long id = contact.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long supplierAddressId(Supplier supplier) {
        if ( supplier == null ) {
            return null;
        }
        Address address = supplier.getAddress();
        if ( address == null ) {
            return null;
        }
        Long id = address.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
