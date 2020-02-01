package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.Tax;
import com.diviso.graeshoppe.product.domain.TaxCategory;
import com.diviso.graeshoppe.product.service.dto.TaxDTO;
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
public class TaxMapperImpl implements TaxMapper {

    @Autowired
    private TaxCategoryMapper taxCategoryMapper;

    @Override
    public List<Tax> toEntity(List<TaxDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Tax> list = new ArrayList<Tax>( dtoList.size() );
        for ( TaxDTO taxDTO : dtoList ) {
            list.add( toEntity( taxDTO ) );
        }

        return list;
    }

    @Override
    public List<TaxDTO> toDto(List<Tax> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TaxDTO> list = new ArrayList<TaxDTO>( entityList.size() );
        for ( Tax tax : entityList ) {
            list.add( toDto( tax ) );
        }

        return list;
    }

    @Override
    public TaxDTO toDto(Tax tax) {
        if ( tax == null ) {
            return null;
        }

        TaxDTO taxDTO = new TaxDTO();

        taxDTO.setTaxCategoryId( taxTaxCategoryId( tax ) );
        taxDTO.setId( tax.getId() );
        taxDTO.setName( tax.getName() );
        taxDTO.setRate( tax.getRate() );
        taxDTO.setDescription( tax.getDescription() );

        return taxDTO;
    }

    @Override
    public Tax toEntity(TaxDTO taxDTO) {
        if ( taxDTO == null ) {
            return null;
        }

        Tax tax = new Tax();

        tax.setTaxCategory( taxCategoryMapper.fromId( taxDTO.getTaxCategoryId() ) );
        tax.setId( taxDTO.getId() );
        tax.setName( taxDTO.getName() );
        tax.setRate( taxDTO.getRate() );
        tax.setDescription( taxDTO.getDescription() );

        return tax;
    }

    private Long taxTaxCategoryId(Tax tax) {
        if ( tax == null ) {
            return null;
        }
        TaxCategory taxCategory = tax.getTaxCategory();
        if ( taxCategory == null ) {
            return null;
        }
        Long id = taxCategory.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
