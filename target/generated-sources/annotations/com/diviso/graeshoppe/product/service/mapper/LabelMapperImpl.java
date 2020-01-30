package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.Label;
import com.diviso.graeshoppe.product.domain.Product;
import com.diviso.graeshoppe.product.service.dto.LabelDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-30T22:33:52+0530",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_222 (Private Build)"
)
@Component
public class LabelMapperImpl implements LabelMapper {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Label> toEntity(List<LabelDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Label> list = new ArrayList<Label>( dtoList.size() );
        for ( LabelDTO labelDTO : dtoList ) {
            list.add( toEntity( labelDTO ) );
        }

        return list;
    }

    @Override
    public List<LabelDTO> toDto(List<Label> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<LabelDTO> list = new ArrayList<LabelDTO>( entityList.size() );
        for ( Label label : entityList ) {
            list.add( toDto( label ) );
        }

        return list;
    }

    @Override
    public LabelDTO toDto(Label label) {
        if ( label == null ) {
            return null;
        }

        LabelDTO labelDTO = new LabelDTO();

        labelDTO.setProductId( labelProductId( label ) );
        labelDTO.setId( label.getId() );
        labelDTO.setiDPcode( label.getiDPcode() );
        labelDTO.setName( label.getName() );

        return labelDTO;
    }

    @Override
    public Label toEntity(LabelDTO labelDTO) {
        if ( labelDTO == null ) {
            return null;
        }

        Label label = new Label();

        label.setProduct( productMapper.fromId( labelDTO.getProductId() ) );
        label.setId( labelDTO.getId() );
        label.setiDPcode( labelDTO.getiDPcode() );
        label.setName( labelDTO.getName() );

        return label;
    }

    private Long labelProductId(Label label) {
        if ( label == null ) {
            return null;
        }
        Product product = label.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
