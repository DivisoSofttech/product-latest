package com.diviso.graeshoppe.product.repository;

import com.diviso.graeshoppe.product.domain.Manufacturer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Manufacturer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

}
