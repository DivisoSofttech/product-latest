package com.diviso.graeshoppe.product.repository;

import com.diviso.graeshoppe.product.domain.AuxilaryLineItem;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AuxilaryLineItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuxilaryLineItemRepository extends JpaRepository<AuxilaryLineItem, Long> {

	@Override
	List<AuxilaryLineItem> findAll();
}
