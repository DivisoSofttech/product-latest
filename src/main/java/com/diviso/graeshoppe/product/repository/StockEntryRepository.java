package com.diviso.graeshoppe.product.repository;

import com.diviso.graeshoppe.product.domain.StockEntry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StockEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockEntryRepository extends JpaRepository<StockEntry, Long> {

}
