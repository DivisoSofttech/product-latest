package com.diviso.graeshoppe.product.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EntryLineItem entity.
 */
public class EntryLineItemDTO implements Serializable {

    private Long id;

    private Double quantityAdjustment;

    private Double valueAdjustment;

    private String description;


    private Long stockEntryId;

    private Long productId;

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

    public Long getStockEntryId() {
        return stockEntryId;
    }

    public void setStockEntryId(Long stockEntryId) {
        this.stockEntryId = stockEntryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
            ", stockEntry=" + getStockEntryId() +
            ", product=" + getProductId() +
            "}";
    }
}
