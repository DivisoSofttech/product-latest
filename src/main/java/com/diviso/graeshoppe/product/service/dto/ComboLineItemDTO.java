package com.diviso.graeshoppe.product.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ComboLineItem entity.
 */
public class ComboLineItemDTO implements Serializable {

    private Long id;

    private Double quantity;

    private String description;


    private Long productId;

    private Long comboItemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
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

    public Long getComboItemId() {
        return comboItemId;
    }

    public void setComboItemId(Long productId) {
        this.comboItemId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ComboLineItemDTO comboLineItemDTO = (ComboLineItemDTO) o;
        if (comboLineItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comboLineItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ComboLineItemDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", description='" + getDescription() + "'" +
            ", product=" + getProductId() +
            ", comboItem=" + getComboItemId() +
            "}";
    }
}
