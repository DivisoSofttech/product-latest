package com.diviso.graeshoppe.product.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UOM.
 */
@Entity
@Table(name = "uom")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "uom")
public class UOM implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "i_d_pcode")
    private String iDPcode;

    @Column(name = "unit")
    private String unit;

    @Column(name = "description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getiDPcode() {
        return iDPcode;
    }

    public UOM iDPcode(String iDPcode) {
        this.iDPcode = iDPcode;
        return this;
    }

    public void setiDPcode(String iDPcode) {
        this.iDPcode = iDPcode;
    }

    public String getUnit() {
        return unit;
    }

    public UOM unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public UOM description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UOM uOM = (UOM) o;
        if (uOM.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uOM.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UOM{" +
            "id=" + getId() +
            ", iDPcode='" + getiDPcode() + "'" +
            ", unit='" + getUnit() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
