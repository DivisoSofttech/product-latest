package com.diviso.graeshoppe.product.repository;

import com.diviso.graeshoppe.product.domain.Discount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Discount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

}
