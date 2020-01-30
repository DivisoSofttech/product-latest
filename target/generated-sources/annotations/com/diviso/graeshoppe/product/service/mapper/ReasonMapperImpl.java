package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.Reason;
import com.diviso.graeshoppe.product.service.dto.ReasonDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-30T22:33:52+0530",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_222 (Private Build)"
)
@Component
public class ReasonMapperImpl implements ReasonMapper {

    @Override
    public Reason toEntity(ReasonDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Reason reason = new Reason();

        reason.setId( dto.getId() );
        reason.setiDPcode( dto.getiDPcode() );
        reason.setName( dto.getName() );
        reason.setDescription( dto.getDescription() );

        return reason;
    }

    @Override
    public ReasonDTO toDto(Reason entity) {
        if ( entity == null ) {
            return null;
        }

        ReasonDTO reasonDTO = new ReasonDTO();

        reasonDTO.setId( entity.getId() );
        reasonDTO.setiDPcode( entity.getiDPcode() );
        reasonDTO.setName( entity.getName() );
        reasonDTO.setDescription( entity.getDescription() );

        return reasonDTO;
    }

    @Override
    public List<Reason> toEntity(List<ReasonDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Reason> list = new ArrayList<Reason>( dtoList.size() );
        for ( ReasonDTO reasonDTO : dtoList ) {
            list.add( toEntity( reasonDTO ) );
        }

        return list;
    }

    @Override
    public List<ReasonDTO> toDto(List<Reason> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ReasonDTO> list = new ArrayList<ReasonDTO>( entityList.size() );
        for ( Reason reason : entityList ) {
            list.add( toDto( reason ) );
        }

        return list;
    }
}
