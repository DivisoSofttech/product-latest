package com.diviso.graeshoppe.product.repository;

import com.diviso.graeshoppe.product.domain.UOM;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UOM entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UOMRepository extends JpaRepository<UOM, Long> {

}
