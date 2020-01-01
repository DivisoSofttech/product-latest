package com.diviso.graeshoppe.product.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A EntryLineItem.
 */
@Entity
@Table(name = "entry_line_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "entrylineitem")
public class EntryLineItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "quantity_adjustment")
    private Double quantityAdjustment;

    @Column(name = "value_adjustment")
    private Double valueAdjustment;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("entryLineItems")
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties("entryLineItems")
    private StockEntry stockEntry;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantityAdjustment() {
        return quantityAdjustment;
    }

    public EntryLineItem quantityAdjustment(Double quantityAdjustment) {
        this.quantityAdjustment = quantityAdjustment;
        return this;
    }

    public void setQuantityAdjustment(Double quantityAdjustment) {
        this.quantityAdjustment = quantityAdjustment;
    }

    public Double getValueAdjustment() {
        return valueAdjustment;
    }

    public EntryLineItem valueAdjustment(Double valueAdjustment) {
        this.valueAdjustment = valueAdjustment;
        return this;
    }

    public void setValueAdjustment(Double valueAdjustment) {
        this.valueAdjustment = valueAdjustment;
    }

    public String getDescription() {
        return description;
    }

    public EntryLineItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product getProduct() {
        return product;
    }

    public EntryLineItem product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public StockEntry getStockEntry() {
        return stockEntry;
    }

    public EntryLineItem stockEntry(StockEntry stockEntry) {
        this.stockEntry = stockEntry;
        return this;
    }

    public void setStockEntry(StockEntry stockEntry) {
        this.stockEntry = stockEntry;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntryLineItem)) {
            return false;
        }
        return id != null && id.equals(((EntryLineItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EntryLineItem{" +
            "id=" + getId() +
            ", quantityAdjustment=" + getQuantityAdjustment() +
            ", valueAdjustment=" + getValueAdjustment() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
