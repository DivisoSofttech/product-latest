package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.Manufacturer;
import com.diviso.graeshoppe.product.service.dto.ManufacturerDTO;
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
public class ManufacturerMapperImpl implements ManufacturerMapper {

    @Override
    public Manufacturer toEntity(ManufacturerDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Manufacturer manufacturer = new Manufacturer();

        manufacturer.setId( dto.getId() );
        manufacturer.setiDPcode( dto.getiDPcode() );
        manufacturer.setName( dto.getName() );

        return manufacturer;
    }

    @Override
    public ManufacturerDTO toDto(Manufacturer entity) {
        if ( entity == null ) {
            return null;
        }

        ManufacturerDTO manufacturerDTO = new ManufacturerDTO();

        manufacturerDTO.setId( entity.getId() );
        manufacturerDTO.setiDPcode( entity.getiDPcode() );
        manufacturerDTO.setName( entity.getName() );

        return manufacturerDTO;
    }

    @Override
    public List<Manufacturer> toEntity(List<ManufacturerDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Manufacturer> list = new ArrayList<Manufacturer>( dtoList.size() );
        for ( ManufacturerDTO manufacturerDTO : dtoList ) {
            list.add( toEntity( manufacturerDTO ) );
        }

        return list;
    }

    @Override
    public List<ManufacturerDTO> toDto(List<Manufacturer> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ManufacturerDTO> list = new ArrayList<ManufacturerDTO>( entityList.size() );
        for ( Manufacturer manufacturer : entityList ) {
            list.add( toDto( manufacturer ) );
        }

        return list;
    }
}
