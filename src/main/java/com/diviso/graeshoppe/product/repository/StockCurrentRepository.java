package com.diviso.graeshoppe.product.repository;

import com.diviso.graeshoppe.product.domain.StockCurrent;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StockCurrent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockCurrentRepository extends JpaRepository<StockCurrent, Long> {

	Optional<StockCurrent> findByProductId(Long productId);
}
