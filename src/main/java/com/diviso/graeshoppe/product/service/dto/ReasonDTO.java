package com.diviso.graeshoppe.product.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Reason entity.
 */
public class ReasonDTO implements Serializable {

    private Long id;

    private String iDPcode;

    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        ReasonDTO reasonDTO = (ReasonDTO) o;
        if (reasonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reasonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReasonDTO{" +
            "id=" + getId() +
            ", iDPcode='" + getiDPcode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
