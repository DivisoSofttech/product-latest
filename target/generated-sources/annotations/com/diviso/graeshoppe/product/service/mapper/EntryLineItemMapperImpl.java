package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.EntryLineItem;
import com.diviso.graeshoppe.product.domain.Product;
import com.diviso.graeshoppe.product.domain.StockEntry;
import com.diviso.graeshoppe.product.service.dto.EntryLineItemDTO;
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
public class EntryLineItemMapperImpl implements EntryLineItemMapper {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StockEntryMapper stockEntryMapper;

    @Override
    public List<EntryLineItem> toEntity(List<EntryLineItemDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<EntryLineItem> list = new ArrayList<EntryLineItem>( dtoList.size() );
        for ( EntryLineItemDTO entryLineItemDTO : dtoList ) {
            list.add( toEntity( entryLineItemDTO ) );
        }

        return list;
    }

    @Override
    public List<EntryLineItemDTO> toDto(List<EntryLineItem> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<EntryLineItemDTO> list = new ArrayList<EntryLineItemDTO>( entityList.size() );
        for ( EntryLineItem entryLineItem : entityList ) {
            list.add( toDto( entryLineItem ) );
        }

        return list;
    }

    @Override
    public EntryLineItemDTO toDto(EntryLineItem entryLineItem) {
        if ( entryLineItem == null ) {
            return null;
        }

        EntryLineItemDTO entryLineItemDTO = new EntryLineItemDTO();

        entryLineItemDTO.setProductId( entryLineItemProductId( entryLineItem ) );
        entryLineItemDTO.setStockEntryId( entryLineItemStockEntryId( entryLineItem ) );
        entryLineItemDTO.setId( entryLineItem.getId() );
        entryLineItemDTO.setQuantityAdjustment( entryLineItem.getQuantityAdjustment() );
        entryLineItemDTO.setValueAdjustment( entryLineItem.getValueAdjustment() );
        entryLineItemDTO.setDescription( entryLineItem.getDescription() );

        return entryLineItemDTO;
    }

    @Override
    public EntryLineItem toEntity(EntryLineItemDTO entryLineItemDTO) {
        if ( entryLineItemDTO == null ) {
            return null;
        }

        EntryLineItem entryLineItem = new EntryLineItem();

        entryLineItem.setProduct( productMapper.fromId( entryLineItemDTO.getProductId() ) );
        entryLineItem.setStockEntry( stockEntryMapper.fromId( entryLineItemDTO.getStockEntryId() ) );
        entryLineItem.setId( entryLineItemDTO.getId() );
        entryLineItem.setQuantityAdjustment( entryLineItemDTO.getQuantityAdjustment() );
        entryLineItem.setValueAdjustment( entryLineItemDTO.getValueAdjustment() );
        entryLineItem.setDescription( entryLineItemDTO.getDescription() );

        return entryLineItem;
    }

    private Long entryLineItemProductId(EntryLineItem entryLineItem) {
        if ( entryLineItem == null ) {
            return null;
        }
        Product product = entryLineItem.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entryLineItemStockEntryId(EntryLineItem entryLineItem) {
        if ( entryLineItem == null ) {
            return null;
        }
        StockEntry stockEntry = entryLineItem.getStockEntry();
        if ( stockEntry == null ) {
            return null;
        }
        Long id = stockEntry.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
