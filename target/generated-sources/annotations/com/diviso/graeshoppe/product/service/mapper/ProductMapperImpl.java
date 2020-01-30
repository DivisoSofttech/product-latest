package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.Brand;
import com.diviso.graeshoppe.product.domain.Category;
import com.diviso.graeshoppe.product.domain.Discount;
import com.diviso.graeshoppe.product.domain.Location;
import com.diviso.graeshoppe.product.domain.Manufacturer;
import com.diviso.graeshoppe.product.domain.Product;
import com.diviso.graeshoppe.product.domain.Supplier;
import com.diviso.graeshoppe.product.domain.TaxCategory;
import com.diviso.graeshoppe.product.domain.UOM;
import com.diviso.graeshoppe.product.service.dto.ProductDTO;
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
public class ProductMapperImpl implements ProductMapper {

    @Autowired
    private TaxCategoryMapper taxCategoryMapper;
    @Autowired
    private UOMMapper uOMMapper;
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private ManufacturerMapper manufacturerMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private DiscountMapper discountMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Product> toEntity(List<ProductDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( dtoList.size() );
        for ( ProductDTO productDTO : dtoList ) {
            list.add( toEntity( productDTO ) );
        }

        return list;
    }

    @Override
    public List<ProductDTO> toDto(List<Product> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ProductDTO> list = new ArrayList<ProductDTO>( entityList.size() );
        for ( Product product : entityList ) {
            list.add( toDto( product ) );
        }

        return list;
    }

    @Override
    public ProductDTO toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setSupplierId( productSupplierId( product ) );
        productDTO.setLocationId( productLocationId( product ) );
        productDTO.setBrandId( productBrandId( product ) );
        productDTO.setManufacturerId( productManufacturerId( product ) );
        productDTO.setUnitId( productUnitId( product ) );
        productDTO.setDiscountId( productDiscountId( product ) );
        productDTO.setCategoryId( productCategoryId( product ) );
        productDTO.setTaxCategoryId( productTaxCategoryId( product ) );
        productDTO.setId( product.getId() );
        productDTO.setReference( product.getReference() );
        productDTO.setName( product.getName() );
        productDTO.setShowInCatalogue( product.isShowInCatalogue() );
        productDTO.setImageLink( product.getImageLink() );
        productDTO.setIsActive( product.isIsActive() );
        productDTO.setSku( product.getSku() );
        productDTO.setiDPcode( product.getiDPcode() );
        productDTO.setIsServiceItem( product.isIsServiceItem() );
        productDTO.setIsAuxilaryItem( product.isIsAuxilaryItem() );
        productDTO.setMinQuantityLevel( product.getMinQuantityLevel() );
        productDTO.setMaxQuantityLevel( product.getMaxQuantityLevel() );
        productDTO.setStorageCost( product.getStorageCost() );
        productDTO.setSellingPrice( product.getSellingPrice() );
        productDTO.setBuyPrice( product.getBuyPrice() );

        return productDTO;
    }

    @Override
    public Product toEntity(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setDiscount( discountMapper.fromId( productDTO.getDiscountId() ) );
        product.setTaxCategory( taxCategoryMapper.fromId( productDTO.getTaxCategoryId() ) );
        product.setManufacturer( manufacturerMapper.fromId( productDTO.getManufacturerId() ) );
        product.setUnit( uOMMapper.fromId( productDTO.getUnitId() ) );
        product.setSupplier( supplierMapper.fromId( productDTO.getSupplierId() ) );
        product.setLocation( locationMapper.fromId( productDTO.getLocationId() ) );
        product.setCategory( categoryMapper.fromId( productDTO.getCategoryId() ) );
        product.setBrand( brandMapper.fromId( productDTO.getBrandId() ) );
        product.setId( productDTO.getId() );
        product.setReference( productDTO.getReference() );
        product.setName( productDTO.getName() );
        product.setShowInCatalogue( productDTO.getShowInCatalogue() );
        product.setImageLink( productDTO.getImageLink() );
        product.setIsActive( productDTO.getIsActive() );
        product.setSku( productDTO.getSku() );
        product.setiDPcode( productDTO.getiDPcode() );
        product.setIsServiceItem( productDTO.getIsServiceItem() );
        product.setIsAuxilaryItem( productDTO.getIsAuxilaryItem() );
        product.setMinQuantityLevel( productDTO.getMinQuantityLevel() );
        product.setMaxQuantityLevel( productDTO.getMaxQuantityLevel() );
        product.setStorageCost( productDTO.getStorageCost() );
        product.setSellingPrice( productDTO.getSellingPrice() );
        product.setBuyPrice( productDTO.getBuyPrice() );

        return product;
    }

    private Long productSupplierId(Product product) {
        if ( product == null ) {
            return null;
        }
        Supplier supplier = product.getSupplier();
        if ( supplier == null ) {
            return null;
        }
        Long id = supplier.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long productLocationId(Product product) {
        if ( product == null ) {
            return null;
        }
        Location location = product.getLocation();
        if ( location == null ) {
            return null;
        }
        Long id = location.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long productBrandId(Product product) {
        if ( product == null ) {
            return null;
        }
        Brand brand = product.getBrand();
        if ( brand == null ) {
            return null;
        }
        Long id = brand.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long productManufacturerId(Product product) {
        if ( product == null ) {
            return null;
        }
        Manufacturer manufacturer = product.getManufacturer();
        if ( manufacturer == null ) {
            return null;
        }
        Long id = manufacturer.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long productUnitId(Product product) {
        if ( product == null ) {
            return null;
        }
        UOM unit = product.getUnit();
        if ( unit == null ) {
            return null;
        }
        Long id = unit.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long productDiscountId(Product product) {
        if ( product == null ) {
            return null;
        }
        Discount discount = product.getDiscount();
        if ( discount == null ) {
            return null;
        }
        Long id = discount.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long productCategoryId(Product product) {
        if ( product == null ) {
            return null;
        }
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        Long id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long productTaxCategoryId(Product product) {
        if ( product == null ) {
            return null;
        }
        TaxCategory taxCategory = product.getTaxCategory();
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
