package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.Address;
import com.diviso.graeshoppe.product.service.dto.AddressDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-01T13:02:32+0530",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_222 (Private Build)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public Address toEntity(AddressDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Address address = new Address();

        address.setId( dto.getId() );
        address.setAddressLine1( dto.getAddressLine1() );
        address.setAddressLine2( dto.getAddressLine2() );
        address.setCityOrTown( dto.getCityOrTown() );
        address.setState( dto.getState() );
        address.setCountry( dto.getCountry() );
        address.setZipcode( dto.getZipcode() );

        return address;
    }

    @Override
    public AddressDTO toDto(Address entity) {
        if ( entity == null ) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setId( entity.getId() );
        addressDTO.setAddressLine1( entity.getAddressLine1() );
        addressDTO.setAddressLine2( entity.getAddressLine2() );
        addressDTO.setCityOrTown( entity.getCityOrTown() );
        addressDTO.setState( entity.getState() );
        addressDTO.setCountry( entity.getCountry() );
        addressDTO.setZipcode( entity.getZipcode() );

        return addressDTO;
    }

    @Override
    public List<Address> toEntity(List<AddressDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Address> list = new ArrayList<Address>( dtoList.size() );
        for ( AddressDTO addressDTO : dtoList ) {
            list.add( toEntity( addressDTO ) );
        }

        return list;
    }

    @Override
    public List<AddressDTO> toDto(List<Address> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AddressDTO> list = new ArrayList<AddressDTO>( entityList.size() );
        for ( Address address : entityList ) {
            list.add( toDto( address ) );
        }

        return list;
    }
}
