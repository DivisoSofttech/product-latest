package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.Product;
import com.diviso.graeshoppe.product.domain.StockCurrent;
import com.diviso.graeshoppe.product.service.dto.StockCurrentDTO;
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
public class StockCurrentMapperImpl implements StockCurrentMapper {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<StockCurrent> toEntity(List<StockCurrentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StockCurrent> list = new ArrayList<StockCurrent>( dtoList.size() );
        for ( StockCurrentDTO stockCurrentDTO : dtoList ) {
            list.add( toEntity( stockCurrentDTO ) );
        }

        return list;
    }

    @Override
    public List<StockCurrentDTO> toDto(List<StockCurrent> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StockCurrentDTO> list = new ArrayList<StockCurrentDTO>( entityList.size() );
        for ( StockCurrent stockCurrent : entityList ) {
            list.add( toDto( stockCurrent ) );
        }

        return list;
    }

    @Override
    public StockCurrentDTO toDto(StockCurrent stockCurrent) {
        if ( stockCurrent == null ) {
            return null;
        }

        StockCurrentDTO stockCurrentDTO = new StockCurrentDTO();

        stockCurrentDTO.setProductId( stockCurrentProductId( stockCurrent ) );
        stockCurrentDTO.setId( stockCurrent.getId() );
        stockCurrentDTO.setiDPcode( stockCurrent.getiDPcode() );
        stockCurrentDTO.setQuantity( stockCurrent.getQuantity() );
        stockCurrentDTO.setSellPrice( stockCurrent.getSellPrice() );
        stockCurrentDTO.setNotes( stockCurrent.getNotes() );

        return stockCurrentDTO;
    }

    @Override
    public StockCurrent toEntity(StockCurrentDTO stockCurrentDTO) {
        if ( stockCurrentDTO == null ) {
            return null;
        }

        StockCurrent stockCurrent = new StockCurrent();

        stockCurrent.setProduct( productMapper.fromId( stockCurrentDTO.getProductId() ) );
        stockCurrent.setId( stockCurrentDTO.getId() );
        stockCurrent.setiDPcode( stockCurrentDTO.getiDPcode() );
        stockCurrent.setQuantity( stockCurrentDTO.getQuantity() );
        stockCurrent.setSellPrice( stockCurrentDTO.getSellPrice() );
        stockCurrent.setNotes( stockCurrentDTO.getNotes() );

        return stockCurrent;
    }

    private Long stockCurrentProductId(StockCurrent stockCurrent) {
        if ( stockCurrent == null ) {
            return null;
        }
        Product product = stockCurrent.getProduct();
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
