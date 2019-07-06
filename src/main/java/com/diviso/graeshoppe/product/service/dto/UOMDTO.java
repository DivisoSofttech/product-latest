package com.diviso.graeshoppe.product.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the UOM entity.
 */
public class UOMDTO implements Serializable {

    private Long id;

    private String iDPcode;

    private String unit;

    private String description;


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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UOMDTO uOMDTO = (UOMDTO) o;
        if (uOMDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uOMDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UOMDTO{" +
            "id=" + getId() +
            ", iDPcode='" + getiDPcode() + "'" +
            ", unit='" + getUnit() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
