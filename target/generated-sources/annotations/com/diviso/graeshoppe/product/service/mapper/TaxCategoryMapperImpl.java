package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.TaxCategory;
import com.diviso.graeshoppe.product.service.dto.TaxCategoryDTO;
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
public class TaxCategoryMapperImpl implements TaxCategoryMapper {

    @Override
    public TaxCategoryDTO toDto(TaxCategory entity) {
        if ( entity == null ) {
            return null;
        }

        TaxCategoryDTO taxCategoryDTO = new TaxCategoryDTO();

        taxCategoryDTO.setId( entity.getId() );
        taxCategoryDTO.setiDPcode( entity.getiDPcode() );
        taxCategoryDTO.setName( entity.getName() );
        taxCategoryDTO.setDescription( entity.getDescription() );

        return taxCategoryDTO;
    }

    @Override
    public List<TaxCategory> toEntity(List<TaxCategoryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TaxCategory> list = new ArrayList<TaxCategory>( dtoList.size() );
        for ( TaxCategoryDTO taxCategoryDTO : dtoList ) {
            list.add( toEntity( taxCategoryDTO ) );
        }

        return list;
    }

    @Override
    public List<TaxCategoryDTO> toDto(List<TaxCategory> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TaxCategoryDTO> list = new ArrayList<TaxCategoryDTO>( entityList.size() );
        for ( TaxCategory taxCategory : entityList ) {
            list.add( toDto( taxCategory ) );
        }

        return list;
    }

    @Override
    public TaxCategory toEntity(TaxCategoryDTO taxCategoryDTO) {
        if ( taxCategoryDTO == null ) {
            return null;
        }

        TaxCategory taxCategory = new TaxCategory();

        taxCategory.setId( taxCategoryDTO.getId() );
        taxCategory.setiDPcode( taxCategoryDTO.getiDPcode() );
        taxCategory.setName( taxCategoryDTO.getName() );
        taxCategory.setDescription( taxCategoryDTO.getDescription() );

        return taxCategory;
    }
}
