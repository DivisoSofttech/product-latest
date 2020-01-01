package com.diviso.graeshoppe.product.service.dto;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import javax.persistence.Lob;

/**
 * A DTO for the {@link com.diviso.graeshoppe.product.domain.Product} entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    private String reference;

    private String name;

    private Boolean showInCatalogue;
    
    @Lob
    private byte[] image;

    private String imageLink;

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

	public Boolean getShowInCatalogue() {
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

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public Boolean getIsActive() {
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

	public Boolean getIsServiceItem() {
		return isServiceItem;
	}

	public void setIsServiceItem(Boolean isServiceItem) {
		this.isServiceItem = isServiceItem;
	}

	public Boolean getIsAuxilaryItem() {
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

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brandId == null) ? 0 : brandId.hashCode());
		result = prime * result + ((buyPrice == null) ? 0 : buyPrice.hashCode());
		result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
		result = prime * result + ((discountId == null) ? 0 : discountId.hashCode());
		result = prime * result + ((iDPcode == null) ? 0 : iDPcode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Arrays.hashCode(image);
		result = prime * result + ((imageLink == null) ? 0 : imageLink.hashCode());
		result = prime * result + ((isActive == null) ? 0 : isActive.hashCode());
		result = prime * result + ((isAuxilaryItem == null) ? 0 : isAuxilaryItem.hashCode());
		result = prime * result + ((isServiceItem == null) ? 0 : isServiceItem.hashCode());
		result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
		result = prime * result + ((manufacturerId == null) ? 0 : manufacturerId.hashCode());
		result = prime * result + ((maxQuantityLevel == null) ? 0 : maxQuantityLevel.hashCode());
		result = prime * result + ((minQuantityLevel == null) ? 0 : minQuantityLevel.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
		result = prime * result + ((sellingPrice == null) ? 0 : sellingPrice.hashCode());
		result = prime * result + ((showInCatalogue == null) ? 0 : showInCatalogue.hashCode());
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		result = prime * result + ((storageCost == null) ? 0 : storageCost.hashCode());
		result = prime * result + ((supplierId == null) ? 0 : supplierId.hashCode());
		result = prime * result + ((taxCategoryId == null) ? 0 : taxCategoryId.hashCode());
		result = prime * result + ((unitId == null) ? 0 : unitId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDTO other = (ProductDTO) obj;
		if (brandId == null) {
			if (other.brandId != null)
				return false;
		} else if (!brandId.equals(other.brandId))
			return false;
		if (buyPrice == null) {
			if (other.buyPrice != null)
				return false;
		} else if (!buyPrice.equals(other.buyPrice))
			return false;
		if (categoryId == null) {
			if (other.categoryId != null)
				return false;
		} else if (!categoryId.equals(other.categoryId))
			return false;
		if (discountId == null) {
			if (other.discountId != null)
				return false;
		} else if (!discountId.equals(other.discountId))
			return false;
		if (iDPcode == null) {
			if (other.iDPcode != null)
				return false;
		} else if (!iDPcode.equals(other.iDPcode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (!Arrays.equals(image, other.image))
			return false;
		if (imageLink == null) {
			if (other.imageLink != null)
				return false;
		} else if (!imageLink.equals(other.imageLink))
			return false;
		if (isActive == null) {
			if (other.isActive != null)
				return false;
		} else if (!isActive.equals(other.isActive))
			return false;
		if (isAuxilaryItem == null) {
			if (other.isAuxilaryItem != null)
				return false;
		} else if (!isAuxilaryItem.equals(other.isAuxilaryItem))
			return false;
		if (isServiceItem == null) {
			if (other.isServiceItem != null)
				return false;
		} else if (!isServiceItem.equals(other.isServiceItem))
			return false;
		if (locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!locationId.equals(other.locationId))
			return false;
		if (manufacturerId == null) {
			if (other.manufacturerId != null)
				return false;
		} else if (!manufacturerId.equals(other.manufacturerId))
			return false;
		if (maxQuantityLevel == null) {
			if (other.maxQuantityLevel != null)
				return false;
		} else if (!maxQuantityLevel.equals(other.maxQuantityLevel))
			return false;
		if (minQuantityLevel == null) {
			if (other.minQuantityLevel != null)
				return false;
		} else if (!minQuantityLevel.equals(other.minQuantityLevel))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		if (sellingPrice == null) {
			if (other.sellingPrice != null)
				return false;
		} else if (!sellingPrice.equals(other.sellingPrice))
			return false;
		if (showInCatalogue == null) {
			if (other.showInCatalogue != null)
				return false;
		} else if (!showInCatalogue.equals(other.showInCatalogue))
			return false;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.equals(other.sku))
			return false;
		if (storageCost == null) {
			if (other.storageCost != null)
				return false;
		} else if (!storageCost.equals(other.storageCost))
			return false;
		if (supplierId == null) {
			if (other.supplierId != null)
				return false;
		} else if (!supplierId.equals(other.supplierId))
			return false;
		if (taxCategoryId == null) {
			if (other.taxCategoryId != null)
				return false;
		} else if (!taxCategoryId.equals(other.taxCategoryId))
			return false;
		if (unitId == null) {
			if (other.unitId != null)
				return false;
		} else if (!unitId.equals(other.unitId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", reference=" + reference + ", name=" + name + ", showInCatalogue="
				+ showInCatalogue + ", image=" + Arrays.toString(image) + ", imageLink=" + imageLink + ", isActive="
				+ isActive + ", sku=" + sku + ", iDPcode=" + iDPcode + ", isServiceItem=" + isServiceItem
				+ ", isAuxilaryItem=" + isAuxilaryItem + ", minQuantityLevel=" + minQuantityLevel
				+ ", maxQuantityLevel=" + maxQuantityLevel + ", storageCost=" + storageCost + ", sellingPrice="
				+ sellingPrice + ", buyPrice=" + buyPrice + ", taxCategoryId=" + taxCategoryId + ", unitId=" + unitId
				+ ", locationId=" + locationId + ", supplierId=" + supplierId + ", manufacturerId=" + manufacturerId
				+ ", brandId=" + brandId + ", discountId=" + discountId + ", categoryId=" + categoryId + "]";
	}

}