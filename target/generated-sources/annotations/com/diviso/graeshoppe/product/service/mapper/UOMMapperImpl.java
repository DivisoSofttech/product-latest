package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.UOM;
import com.diviso.graeshoppe.product.service.dto.UOMDTO;
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
public class UOMMapperImpl implements UOMMapper {

    @Override
    public UOM toEntity(UOMDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UOM uOM = new UOM();

        uOM.setId( dto.getId() );
        uOM.setiDPcode( dto.getiDPcode() );
        uOM.setUnit( dto.getUnit() );
        uOM.setDescription( dto.getDescription() );

        return uOM;
    }

    @Override
    public UOMDTO toDto(UOM entity) {
        if ( entity == null ) {
            return null;
        }

        UOMDTO uOMDTO = new UOMDTO();

        uOMDTO.setId( entity.getId() );
        uOMDTO.setiDPcode( entity.getiDPcode() );
        uOMDTO.setUnit( entity.getUnit() );
        uOMDTO.setDescription( entity.getDescription() );

        return uOMDTO;
    }

    @Override
    public List<UOM> toEntity(List<UOMDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<UOM> list = new ArrayList<UOM>( dtoList.size() );
        for ( UOMDTO uOMDTO : dtoList ) {
            list.add( toEntity( uOMDTO ) );
        }

        return list;
    }

    @Override
    public List<UOMDTO> toDto(List<UOM> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UOMDTO> list = new ArrayList<UOMDTO>( entityList.size() );
        for ( UOM uOM : entityList ) {
            list.add( toDto( uOM ) );
        }

        return list;
    }
}
