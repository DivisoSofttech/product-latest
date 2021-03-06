package com.diviso.graeshoppe.product.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.io.Serializable;

/**
 * A StockCurrent.
 */
@Entity
@Table(name = "stock_current")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "stockcurrent")
@Setting(settingPath = "settings/stockcurrentsettings.json")
@Mapping(mappingPath = "mappings/stockcurrent.json") 
public class StockCurrent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "i_d_pcode")
    private String iDPcode;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "sell_price")
    private Double sellPrice;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JsonIgnoreProperties("stockCurrents")
    private Product product;

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

    public StockCurrent iDPcode(String iDPcode) {
        this.iDPcode = iDPcode;
        return this;
    }

    public void setiDPcode(String iDPcode) {
        this.iDPcode = iDPcode;
    }

    public Double getQuantity() {
        return quantity;
    }

    public StockCurrent quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public StockCurrent sellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
        return this;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getNotes() {
        return notes;
    }

    public StockCurrent notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Product getProduct() {
        return product;
    }

    public StockCurrent product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockCurrent)) {
            return false;
        }
        return id != null && id.equals(((StockCurrent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StockCurrent{" +
            "id=" + getId() +
            ", iDPcode='" + getiDPcode() + "'" +
            ", quantity=" + getQuantity() +
            ", sellPrice=" + getSellPrice() +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
