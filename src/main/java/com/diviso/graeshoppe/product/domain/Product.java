package com.diviso.graeshoppe.product.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "reference")
    private String reference;

    @Column(name = "name")
    private String name;

    @Column(name = "show_in_catalogue")
    private Boolean showInCatalogue;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "sku")
    private String sku;

    @Column(name = "i_d_pcode")
    private String iDPcode;

    @Column(name = "is_service_item")
    private Boolean isServiceItem;

    @Column(name = "is_auxilary_item")
    private Boolean isAuxilaryItem;

    @Column(name = "min_quantity_level")
    private Double minQuantityLevel;

    @Column(name = "max_quantity_level")
    private Double maxQuantityLevel;

    @Column(name = "storage_cost")
    private Double storageCost;

    @Column(name = "selling_price")
    private Double sellingPrice;

    @Column(name = "buy_price")
    private Double buyPrice;

    @OneToMany(mappedBy = "product",cascade=CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AuxilaryLineItem> auxilaryLineItems = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade=CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ComboLineItem> comboLineItems = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade=CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Label> labels = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("products")
    private TaxCategory taxCategory;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private UOM unit;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Location location;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Supplier supplier;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Manufacturer manufacturer;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Brand brand;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Discount discount;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public Product reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isShowInCatalogue() {
        return showInCatalogue;
    }

    public Product showInCatalogue(Boolean showInCatalogue) {
        this.showInCatalogue = showInCatalogue;
        return this;
    }

    public void setShowInCatalogue(Boolean showInCatalogue) {
        this.showInCatalogue = showInCatalogue;
    }

    public String getImageLink() {
        return imageLink;
    }

    public Product imageLink(String imageLink) {
        this.imageLink = imageLink;
        return this;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Product isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getSku() {
        return sku;
    }

    public Product sku(String sku) {
        this.sku = sku;
        return this;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getiDPcode() {
        return iDPcode;
    }

    public Product iDPcode(String iDPcode) {
        this.iDPcode = iDPcode;
        return this;
    }

    public void setiDPcode(String iDPcode) {
        this.iDPcode = iDPcode;
    }

    public Boolean isIsServiceItem() {
        return isServiceItem;
    }

    public Product isServiceItem(Boolean isServiceItem) {
        this.isServiceItem = isServiceItem;
        return this;
    }

    public void setIsServiceItem(Boolean isServiceItem) {
        this.isServiceItem = isServiceItem;
    }

    public Boolean isIsAuxilaryItem() {
        return isAuxilaryItem;
    }

    public Product isAuxilaryItem(Boolean isAuxilaryItem) {
        this.isAuxilaryItem = isAuxilaryItem;
        return this;
    }

    public void setIsAuxilaryItem(Boolean isAuxilaryItem) {
        this.isAuxilaryItem = isAuxilaryItem;
    }

    public Double getMinQuantityLevel() {
        return minQuantityLevel;
    }

    public Product minQuantityLevel(Double minQuantityLevel) {
        this.minQuantityLevel = minQuantityLevel;
        return this;
    }

    public void setMinQuantityLevel(Double minQuantityLevel) {
        this.minQuantityLevel = minQuantityLevel;
    }

    public Double getMaxQuantityLevel() {
        return maxQuantityLevel;
    }

    public Product maxQuantityLevel(Double maxQuantityLevel) {
        this.maxQuantityLevel = maxQuantityLevel;
        return this;
    }

    public void setMaxQuantityLevel(Double maxQuantityLevel) {
        this.maxQuantityLevel = maxQuantityLevel;
    }

    public Double getStorageCost() {
        return storageCost;
    }

    public Product storageCost(Double storageCost) {
        this.storageCost = storageCost;
        return this;
    }

    public void setStorageCost(Double storageCost) {
        this.storageCost = storageCost;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public Product sellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public Product buyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
        return this;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Set<AuxilaryLineItem> getAuxilaryLineItems() {
        return auxilaryLineItems;
    }

    public Product auxilaryLineItems(Set<AuxilaryLineItem> auxilaryLineItems) {
        this.auxilaryLineItems = auxilaryLineItems;
        return this;
    }

    public Product addAuxilaryLineItems(AuxilaryLineItem auxilaryLineItem) {
        this.auxilaryLineItems.add(auxilaryLineItem);
        auxilaryLineItem.setProduct(this);
        return this;
    }

    public Product removeAuxilaryLineItems(AuxilaryLineItem auxilaryLineItem) {
        this.auxilaryLineItems.remove(auxilaryLineItem);
        auxilaryLineItem.setProduct(null);
        return this;
    }

    public void setAuxilaryLineItems(Set<AuxilaryLineItem> auxilaryLineItems) {
        this.auxilaryLineItems = auxilaryLineItems;
    }

    public Set<ComboLineItem> getComboLineItems() {
        return comboLineItems;
    }

    public Product comboLineItems(Set<ComboLineItem> comboLineItems) {
        this.comboLineItems = comboLineItems;
        return this;
    }

    public Product addComboLineItems(ComboLineItem comboLineItem) {
        this.comboLineItems.add(comboLineItem);
        comboLineItem.setProduct(this);
        return this;
    }

    public Product removeComboLineItems(ComboLineItem comboLineItem) {
        this.comboLineItems.remove(comboLineItem);
        comboLineItem.setProduct(null);
        return this;
    }

    public void setComboLineItems(Set<ComboLineItem> comboLineItems) {
        this.comboLineItems = comboLineItems;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public Product labels(Set<Label> labels) {
        this.labels = labels;
        return this;
    }

    public Product addLabels(Label label) {
        this.labels.add(label);
        label.setProduct(this);
        return this;
    }

    public Product removeLabels(Label label) {
        this.labels.remove(label);
        label.setProduct(null);
        return this;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }

    public TaxCategory getTaxCategory() {
        return taxCategory;
    }

    public Product taxCategory(TaxCategory taxCategory) {
        this.taxCategory = taxCategory;
        return this;
    }

    public void setTaxCategory(TaxCategory taxCategory) {
        this.taxCategory = taxCategory;
    }

    public UOM getUnit() {
        return unit;
    }

    public Product unit(UOM uOM) {
        this.unit = uOM;
        return this;
    }

    public void setUnit(UOM uOM) {
        this.unit = uOM;
    }

    public Location getLocation() {
        return location;
    }

    public Product location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Product supplier(Supplier supplier) {
        this.supplier = supplier;
        return this;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public Product manufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Brand getBrand() {
        return brand;
    }

    public Product brand(Brand brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Discount getDiscount() {
        return discount;
    }

    public Product discount(Discount discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Category getCategory() {
        return category;
    }

    public Product category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", name='" + getName() + "'" +
            ", showInCatalogue='" + isShowInCatalogue() + "'" +
            ", imageLink='" + getImageLink() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", sku='" + getSku() + "'" +
            ", iDPcode='" + getiDPcode() + "'" +
            ", isServiceItem='" + isIsServiceItem() + "'" +
            ", isAuxilaryItem='" + isIsAuxilaryItem() + "'" +
            ", minQuantityLevel=" + getMinQuantityLevel() +
            ", maxQuantityLevel=" + getMaxQuantityLevel() +
            ", storageCost=" + getStorageCost() +
            ", sellingPrice=" + getSellingPrice() +
            ", buyPrice=" + getBuyPrice() +
            "}";
    }
}
