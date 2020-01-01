package com.diviso.graeshoppe.product.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.diviso.graeshoppe.product.domain.EntryLineItem} entity.
 */
public class EntryLineItemDTO implements Serializable {

    private Long id;

    private Double quantityAdjustment;

    private Double valueAdjustment;

    private String description;


    private Long productId;

    private Long stockEntryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantityAdjustment() {
        return quantityAdjustment;
    }

    public void setQuantityAdjustment(Double quantityAdjustment) {
        this.quantityAdjustment = quantityAdjustment;
    }

    public Double getValueAdjustment() {
        return valueAdjustment;
    }

    public void setValueAdjustment(Double valueAdjustment) {
        this.valueAdjustment = valueAdjustment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStockEntryId() {
        return stockEntryId;
    }

    public void setStockEntryId(Long stockEntryId) {
        this.stockEntryId = stockEntryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntryLineItemDTO entryLineItemDTO = (EntryLineItemDTO) o;
        if (entryLineItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entryLineItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntryLineItemDTO{" +
            "id=" + getId() +
            ", quantityAdjustment=" + getQuantityAdjustment() +
            ", valueAdjustment=" + getValueAdjustment() +
            ", description='" + getDescription() + "'" +
            ", productId=" + getProductId() +
            ", stockEntryId=" + getStockEntryId() +
            "}";
    }
}
