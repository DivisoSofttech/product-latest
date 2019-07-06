package com.diviso.graeshoppe.product.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the StockCurrent entity.
 */
public class StockCurrentDTO implements Serializable {

    private Long id;

    private String iDPcode;

    private Double quantity;
                                               
    private Double sellPrice;

    private String notes;


    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getiDPcode() {
        return iDPcode;
    }

    public void setiDPcode(String iDPcode) {
        this.iDPcode = iDPcode;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

        StockCurrentDTO stockCurrentDTO = (StockCurrentDTO) o;
        if (stockCurrentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockCurrentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockCurrentDTO{" +
            "id=" + getId() +
            ", iDPcode='" + getiDPcode() + "'" +
            ", quantity=" + getQuantity() +
            ", sellPrice=" + getSellPrice() +
            ", notes='" + getNotes() + "'" +
            ", product=" + getProductId() +
            "}";
    }
}
