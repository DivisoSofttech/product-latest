package com.diviso.graeshoppe.product.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ComboLineItem.
 */
@Entity
@Table(name = "combo_line_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "combolineitem")
public class ComboLineItem implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("comboLineItems")
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties("comboLineItems")
    private Product comboItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public ComboLineItem quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public ComboLineItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product getProduct() {
        return product;
    }

    public ComboLineItem product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getComboItem() {
        return comboItem;
    }

    public ComboLineItem comboItem(Product product) {
        this.comboItem = product;
        return this;
    }

    public void setComboItem(Product product) {
        this.comboItem = product;
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
        ComboLineItem comboLineItem = (ComboLineItem) o;
        if (comboLineItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comboLineItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ComboLineItem{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
