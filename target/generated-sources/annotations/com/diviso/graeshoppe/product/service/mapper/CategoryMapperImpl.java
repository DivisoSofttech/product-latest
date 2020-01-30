package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.Category;
import com.diviso.graeshoppe.product.service.dto.CategoryDTO;
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
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDTO toDto(Category entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId( entity.getId() );
        categoryDTO.setiDPcode( entity.getiDPcode() );
        categoryDTO.setName( entity.getName() );
        categoryDTO.setImageLink( entity.getImageLink() );
        categoryDTO.setDescription( entity.getDescription() );

        return categoryDTO;
    }

    @Override
    public List<Category> toEntity(List<CategoryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Category> list = new ArrayList<Category>( dtoList.size() );
        for ( CategoryDTO categoryDTO : dtoList ) {
            list.add( toEntity( categoryDTO ) );
        }

        return list;
    }

    @Override
    public List<CategoryDTO> toDto(List<Category> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CategoryDTO> list = new ArrayList<CategoryDTO>( entityList.size() );
        for ( Category category : entityList ) {
            list.add( toDto( category ) );
        }

        return list;
    }

    @Override
    public Category toEntity(CategoryDTO categoryDTO) {
        if ( categoryDTO == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( categoryDTO.getId() );
        category.setiDPcode( categoryDTO.getiDPcode() );
        category.setName( categoryDTO.getName() );
        category.setImageLink( categoryDTO.getImageLink() );
        category.setDescription( categoryDTO.getDescription() );

        return category;
    }
}
