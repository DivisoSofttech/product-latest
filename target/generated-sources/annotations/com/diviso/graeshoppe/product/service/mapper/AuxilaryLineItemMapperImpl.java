package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.AuxilaryLineItem;
import com.diviso.graeshoppe.product.domain.Product;
import com.diviso.graeshoppe.product.service.dto.AuxilaryLineItemDTO;
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
public class AuxilaryLineItemMapperImpl implements AuxilaryLineItemMapper {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<AuxilaryLineItem> toEntity(List<AuxilaryLineItemDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<AuxilaryLineItem> list = new ArrayList<AuxilaryLineItem>( dtoList.size() );
        for ( AuxilaryLineItemDTO auxilaryLineItemDTO : dtoList ) {
            list.add( toEntity( auxilaryLineItemDTO ) );
        }

        return list;
    }

    @Override
    public List<AuxilaryLineItemDTO> toDto(List<AuxilaryLineItem> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AuxilaryLineItemDTO> list = new ArrayList<AuxilaryLineItemDTO>( entityList.size() );
        for ( AuxilaryLineItem auxilaryLineItem : entityList ) {
            list.add( toDto( auxilaryLineItem ) );
        }

        return list;
    }

    @Override
    public AuxilaryLineItemDTO toDto(AuxilaryLineItem auxilaryLineItem) {
        if ( auxilaryLineItem == null ) {
            return null;
        }

        AuxilaryLineItemDTO auxilaryLineItemDTO = new AuxilaryLineItemDTO();

        auxilaryLineItemDTO.setProductId( auxilaryLineItemProductId( auxilaryLineItem ) );
        auxilaryLineItemDTO.setAuxilaryItemId( auxilaryLineItemAuxilaryItemId( auxilaryLineItem ) );
        auxilaryLineItemDTO.setId( auxilaryLineItem.getId() );
        auxilaryLineItemDTO.setDescription( auxilaryLineItem.getDescription() );
        auxilaryLineItemDTO.setQuantity( auxilaryLineItem.getQuantity() );

        return auxilaryLineItemDTO;
    }

    @Override
    public AuxilaryLineItem toEntity(AuxilaryLineItemDTO auxilaryLineItemDTO) {
        if ( auxilaryLineItemDTO == null ) {
            return null;
        }

        AuxilaryLineItem auxilaryLineItem = new AuxilaryLineItem();

        auxilaryLineItem.setProduct( productMapper.fromId( auxilaryLineItemDTO.getProductId() ) );
        auxilaryLineItem.setAuxilaryItem( productMapper.fromId( auxilaryLineItemDTO.getAuxilaryItemId() ) );
        auxilaryLineItem.setId( auxilaryLineItemDTO.getId() );
        auxilaryLineItem.setDescription( auxilaryLineItemDTO.getDescription() );
        auxilaryLineItem.setQuantity( auxilaryLineItemDTO.getQuantity() );

        return auxilaryLineItem;
    }

    private Long auxilaryLineItemProductId(AuxilaryLineItem auxilaryLineItem) {
        if ( auxilaryLineItem == null ) {
            return null;
        }
        Product product = auxilaryLineItem.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long auxilaryLineItemAuxilaryItemId(AuxilaryLineItem auxilaryLineItem) {
        if ( auxilaryLineItem == null ) {
            return null;
        }
        Product auxilaryItem = auxilaryLineItem.getAuxilaryItem();
        if ( auxilaryItem == null ) {
            return null;
        }
        Long id = auxilaryItem.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
