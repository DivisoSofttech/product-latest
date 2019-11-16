package com.diviso.graeshoppe.product.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AuxilaryLineItem.
 */
@Entity
@Table(name = "auxilary_line_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "auxilarylineitem")
public class AuxilaryLineItem implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private Double quantity;

    @ManyToOne
    //@ManyToOne(cascade=CascadeType.ALL)
    @JsonIgnoreProperties("auxilaryLineItems")
    private Product product;

    @ManyToOne
    //@ManyToOne(cascade=CascadeType.ALL)
    @JsonIgnoreProperties("auxilaryLineItems")
    private Product auxilaryItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public AuxilaryLineItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getQuantity() {
        return quantity;
    }

    public AuxilaryLineItem quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public AuxilaryLineItem product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getAuxilaryItem() {
        return auxilaryItem;
    }

    public AuxilaryLineItem auxilaryItem(Product product) {
        this.auxilaryItem = product;
        return this;
    }

    public void setAuxilaryItem(Product product) {
        this.auxilaryItem = product;
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
        AuxilaryLineItem auxilaryLineItem = (AuxilaryLineItem) o;
        if (auxilaryLineItem.getId() == null || getId() == null) {
            return false;
        }
       
        return Objects.equals(getId(), auxilaryLineItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuxilaryLineItem{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", quantity=" + getQuantity() +
            "}";
    }
}
