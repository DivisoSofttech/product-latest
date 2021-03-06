package com.diviso.graeshoppe.product.repository;

import com.diviso.graeshoppe.product.domain.ComboLineItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ComboLineItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComboLineItemRepository extends JpaRepository<ComboLineItem, Long> {

}
