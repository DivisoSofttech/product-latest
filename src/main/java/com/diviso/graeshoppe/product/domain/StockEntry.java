package com.diviso.graeshoppe.product.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A StockEntry.
 */
@Entity
@Table(name = "stock_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "stockentry")
public class StockEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "i_d_pcode")
    private String iDPcode;

    @Column(name = "reference")
    private String reference;

    @Column(name = "date")
    private Instant date;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "stockEntry")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EntryLineItem> entryLineItems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("stockEntries")
    private Reason reason;

    @ManyToOne
    @JsonIgnoreProperties("stockEntries")
    private Location location;

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

    public StockEntry iDPcode(String iDPcode) {
        this.iDPcode = iDPcode;
        return this;
    }

    public void setiDPcode(String iDPcode) {
        this.iDPcode = iDPcode;
    }

    public String getReference() {
        return reference;
    }

    public StockEntry reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Instant getDate() {
        return date;
    }

    public StockEntry date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public StockEntry description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<EntryLineItem> getEntryLineItems() {
        return entryLineItems;
    }

    public StockEntry entryLineItems(Set<EntryLineItem> entryLineItems) {
        this.entryLineItems = entryLineItems;
        return this;
    }

    public StockEntry addEntryLineItems(EntryLineItem entryLineItem) {
        this.entryLineItems.add(entryLineItem);
        entryLineItem.setStockEntry(this);
        return this;
    }

    public StockEntry removeEntryLineItems(EntryLineItem entryLineItem) {
        this.entryLineItems.remove(entryLineItem);
        entryLineItem.setStockEntry(null);
        return this;
    }

    public void setEntryLineItems(Set<EntryLineItem> entryLineItems) {
        this.entryLineItems = entryLineItems;
    }

    public Reason getReason() {
        return reason;
    }

    public StockEntry reason(Reason reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public Location getLocation() {
        return location;
    }

    public StockEntry location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockEntry)) {
            return false;
        }
        return id != null && id.equals(((StockEntry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StockEntry{" +
            "id=" + getId() +
            ", iDPcode='" + getiDPcode() + "'" +
            ", reference='" + getReference() + "'" +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
