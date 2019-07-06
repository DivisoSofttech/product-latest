package com.diviso.graeshoppe.product.repository;

import com.diviso.graeshoppe.product.domain.TaxCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TaxCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxCategoryRepository extends JpaRepository<TaxCategory, Long> {

}
