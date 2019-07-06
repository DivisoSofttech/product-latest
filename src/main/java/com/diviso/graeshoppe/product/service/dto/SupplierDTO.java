package com.diviso.graeshoppe.product.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Supplier entity.
 */
public class SupplierDTO implements Serializable {

    private Long id;

    private String iDPcode;

    private String companyName;

    private Double creditLimit;

    private Double currentDebt;

    private LocalDate debtDate;

    private Boolean visible;


    private Long contactId;

    private Long addressId;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getCurrentDebt() {
        return currentDebt;
    }

    public void setCurrentDebt(Double currentDebt) {
        this.currentDebt = currentDebt;
    }

    public LocalDate getDebtDate() {
        return debtDate;
    }

    public void setDebtDate(LocalDate debtDate) {
        this.debtDate = debtDate;
    }

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SupplierDTO supplierDTO = (SupplierDTO) o;
        if (supplierDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplierDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplierDTO{" +
            "id=" + getId() +
            ", iDPcode='" + getiDPcode() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", creditLimit=" + getCreditLimit() +
            ", currentDebt=" + getCurrentDebt() +
            ", debtDate='" + getDebtDate() + "'" +
            ", visible='" + isVisible() + "'" +
            ", contact=" + getContactId() +
            ", address=" + getAddressId() +
            "}";
    }
}
