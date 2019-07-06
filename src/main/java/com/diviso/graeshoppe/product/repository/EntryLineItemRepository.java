package com.diviso.graeshoppe.product.repository;

import com.diviso.graeshoppe.product.domain.EntryLineItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EntryLineItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntryLineItemRepository extends JpaRepository<EntryLineItem, Long> {

}
