package com.diviso.graeshoppe.product.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.diviso.graeshoppe.product.domain.AuxilaryLineItem} entity.
 */
public class AuxilaryLineItemDTO implements Serializable {

    private Long id;

    private String description;

    private Double quantity;


    private Long auxilaryItemId;

    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Long getAuxilaryItemId() {
        return auxilaryItemId;
    }

    public void setAuxilaryItemId(Long productId) {
        this.auxilaryItemId = productId;
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

        AuxilaryLineItemDTO auxilaryLineItemDTO = (AuxilaryLineItemDTO) o;
        if (auxilaryLineItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auxilaryLineItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuxilaryLineItemDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", quantity=" + getQuantity() +
            ", auxilaryItemId=" + getAuxilaryItemId() +
            ", productId=" + getProductId() +
            "}";
    }
}
