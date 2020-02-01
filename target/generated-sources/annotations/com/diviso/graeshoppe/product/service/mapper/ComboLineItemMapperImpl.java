package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.ComboLineItem;
import com.diviso.graeshoppe.product.domain.Product;
import com.diviso.graeshoppe.product.service.dto.ComboLineItemDTO;
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
public class ComboLineItemMapperImpl implements ComboLineItemMapper {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ComboLineItem> toEntity(List<ComboLineItemDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ComboLineItem> list = new ArrayList<ComboLineItem>( dtoList.size() );
        for ( ComboLineItemDTO comboLineItemDTO : dtoList ) {
            list.add( toEntity( comboLineItemDTO ) );
        }

        return list;
    }

    @Override
    public List<ComboLineItemDTO> toDto(List<ComboLineItem> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ComboLineItemDTO> list = new ArrayList<ComboLineItemDTO>( entityList.size() );
        for ( ComboLineItem comboLineItem : entityList ) {
            list.add( toDto( comboLineItem ) );
        }

        return list;
    }

    @Override
    public ComboLineItemDTO toDto(ComboLineItem comboLineItem) {
        if ( comboLineItem == null ) {
            return null;
        }

        ComboLineItemDTO comboLineItemDTO = new ComboLineItemDTO();

        comboLineItemDTO.setProductId( comboLineItemProductId( comboLineItem ) );
        comboLineItemDTO.setComboItemId( comboLineItemComboItemId( comboLineItem ) );
        comboLineItemDTO.setId( comboLineItem.getId() );
        comboLineItemDTO.setQuantity( comboLineItem.getQuantity() );
        comboLineItemDTO.setDescription( comboLineItem.getDescription() );

        return comboLineItemDTO;
    }

    @Override
    public ComboLineItem toEntity(ComboLineItemDTO comboLineItemDTO) {
        if ( comboLineItemDTO == null ) {
            return null;
        }

        ComboLineItem comboLineItem = new ComboLineItem();

        comboLineItem.setProduct( productMapper.fromId( comboLineItemDTO.getProductId() ) );
        comboLineItem.setComboItem( productMapper.fromId( comboLineItemDTO.getComboItemId() ) );
        comboLineItem.setId( comboLineItemDTO.getId() );
        comboLineItem.setQuantity( comboLineItemDTO.getQuantity() );
        comboLineItem.setDescription( comboLineItemDTO.getDescription() );

        return comboLineItem;
    }

    private Long comboLineItemProductId(ComboLineItem comboLineItem) {
        if ( comboLineItem == null ) {
            return null;
        }
        Product product = comboLineItem.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long comboLineItemComboItemId(ComboLineItem comboLineItem) {
        if ( comboLineItem == null ) {
            return null;
        }
        Product comboItem = comboLineItem.getComboItem();
        if ( comboItem == null ) {
            return null;
        }
        Long id = comboItem.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
