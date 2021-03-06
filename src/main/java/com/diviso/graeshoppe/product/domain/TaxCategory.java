package com.diviso.graeshoppe.product.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TaxCategory.
 */
@Entity
@Table(name = "tax_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "taxcategory")
public class TaxCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "i_d_pcode")
    private String iDPcode;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "taxCategory", cascade=CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tax> taxes = new HashSet<>();

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

    public TaxCategory iDPcode(String iDPcode) {
        this.iDPcode = iDPcode;
        return this;
    }

    public void setiDPcode(String iDPcode) {
        this.iDPcode = iDPcode;
    }

    public String getName() {
        return name;
    }

    public TaxCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public TaxCategory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Tax> getTaxes() {
        return taxes;
    }

    public TaxCategory taxes(Set<Tax> taxes) {
        this.taxes = taxes;
        return this;
    }

    public TaxCategory addTaxes(Tax tax) {
        this.taxes.add(tax);
        tax.setTaxCategory(this);
        return this;
    }

    public TaxCategory removeTaxes(Tax tax) {
        this.taxes.remove(tax);
        tax.setTaxCategory(null);
        return this;
    }

    public void setTaxes(Set<Tax> taxes) {
        this.taxes = taxes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaxCategory)) {
            return false;
        }
        return id != null && id.equals(((TaxCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TaxCategory{" +
            "id=" + getId() +
            ", iDPcode='" + getiDPcode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
