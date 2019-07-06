package com.diviso.graeshoppe.product.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    private String reference;

    private String name;

    private Boolean showInCatalogue;

    @Lob
    private byte[] image;

    private String imageContentType;
    private Boolean isActive;

    private String sku;

    private String iDPcode;

    private Boolean isServiceItem;

    private Boolean isAuxilaryItem;

    private Double minQuantityLevel;

    private Double maxQuantityLevel;

    private Double storageCost;

    private Double sellingPrice;

    private Double buyPrice;


    private Long taxCategoryId;

    private Long unitId;

    private Long locationId;

    private Long supplierId;

    private Long manufacturerId;

    private Long brandId;

    private Long discountId;

    private Long categoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isShowInCatalogue() {
        return showInCatalogue;
    }

    public void setShowInCatalogue(Boolean showInCatalogue) {
        this.showInCatalogue = showInCatalogue;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getiDPcode() {
        return iDPcode;
    }

    public void setiDPcode(String iDPcode) {
        this.iDPcode = iDPcode;
    }

    public Boolean isIsServiceItem() {
        return isServiceItem;
    }

    public void setIsServiceItem(Boolean isServiceItem) {
        this.isServiceItem = isServiceItem;
    }

    public Boolean isIsAuxilaryItem() {
        return isAuxilaryItem;
    }

    public void setIsAuxilaryItem(Boolean isAuxilaryItem) {
        this.isAuxilaryItem = isAuxilaryItem;
    }

    public Double getMinQuantityLevel() {
        return minQuantityLevel;
    }

    public void setMinQuantityLevel(Double minQuantityLevel) {
        this.minQuantityLevel = minQuantityLevel;
    }

    public Double getMaxQuantityLevel() {
        return maxQuantityLevel;
    }

    public void setMaxQuantityLevel(Double maxQuantityLevel) {
        this.maxQuantityLevel = maxQuantityLevel;
    }

    public Double getStorageCost() {
        return storageCost;
    }

    public void setStorageCost(Double storageCost) {
        this.storageCost = storageCost;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Long getTaxCategoryId() {
        return taxCategoryId;
    }

    public void setTaxCategoryId(Long taxCategoryId) {
        this.taxCategoryId = taxCategoryId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long uOMId) {
        this.unitId = uOMId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (productDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", name='" + getName() + "'" +
            ", showInCatalogue='" + isShowInCatalogue() + "'" +
            ", image='" + getImage() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", sku='" + getSku() + "'" +
            ", iDPcode='" + getiDPcode() + "'" +
            ", isServiceItem='" + isIsServiceItem() + "'" +
            ", isAuxilaryItem='" + isIsAuxilaryItem() + "'" +
            ", minQuantityLevel=" + getMinQuantityLevel() +
            ", maxQuantityLevel=" + getMaxQuantityLevel() +
            ", storageCost=" + getStorageCost() +
            ", sellingPrice=" + getSellingPrice() +
            ", buyPrice=" + getBuyPrice() +
            ", taxCategory=" + getTaxCategoryId() +
            ", unit=" + getUnitId() +
            ", location=" + getLocationId() +
            ", supplier=" + getSupplierId() +
            ", manufacturer=" + getManufacturerId() +
            ", brand=" + getBrandId() +
            ", discount=" + getDiscountId() +
            ", category=" + getCategoryId() +
            "}";
    }
}
