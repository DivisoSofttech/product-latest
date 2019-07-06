package com.diviso.graeshoppe.product.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Discount entity.
 */
public class DiscountDTO implements Serializable {

    private Long id;

    private Double rate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DiscountDTO discountDTO = (DiscountDTO) o;
        if (discountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), discountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DiscountDTO{" +
            "id=" + getId() +
            ", rate=" + getRate() +
            "}";
    }
}
