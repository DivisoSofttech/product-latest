package com.diviso.graeshoppe.product.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Supplier.
 */
@Entity
@Table(name = "supplier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "supplier")
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "i_d_pcode")
    private String iDPcode;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "credit_limit")
    private Double creditLimit;

    @Column(name = "current_debt")
    private Double currentDebt;

    @Column(name = "debt_date")
    private LocalDate debtDate;

    @Column(name = "visible")
    private Boolean visible;

    @OneToOne
    @JoinColumn(unique = true)
    private Contact contact;

    @ManyToOne
    @JsonIgnoreProperties("suppliers")
    private Address address;

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

    public Supplier iDPcode(String iDPcode) {
        this.iDPcode = iDPcode;
        return this;
    }

    public void setiDPcode(String iDPcode) {
        this.iDPcode = iDPcode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Supplier companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public Supplier creditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
        return this;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getCurrentDebt() {
        return currentDebt;
    }

    public Supplier currentDebt(Double currentDebt) {
        this.currentDebt = currentDebt;
        return this;
    }

    public void setCurrentDebt(Double currentDebt) {
        this.currentDebt = currentDebt;
    }

    public LocalDate getDebtDate() {
        return debtDate;
    }

    public Supplier debtDate(LocalDate debtDate) {
        this.debtDate = debtDate;
        return this;
    }

    public void setDebtDate(LocalDate debtDate) {
        this.debtDate = debtDate;
    }

    public Boolean isVisible() {
        return visible;
    }

    public Supplier visible(Boolean visible) {
        this.visible = visible;
        return this;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Contact getContact() {
        return contact;
    }

    public Supplier contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Address getAddress() {
        return address;
    }

    public Supplier address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Supplier)) {
            return false;
        }
        return id != null && id.equals(((Supplier) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Supplier{" +
            "id=" + getId() +
            ", iDPcode='" + getiDPcode() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", creditLimit=" + getCreditLimit() +
            ", currentDebt=" + getCurrentDebt() +
            ", debtDate='" + getDebtDate() + "'" +
            ", visible='" + isVisible() + "'" +
            "}";
    }
}
