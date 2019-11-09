package com.diviso.graeshoppe.product.repository;

import com.diviso.graeshoppe.product.domain.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
/**
 * Spring Data  repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

 /*   @Query(value = "select distinct product from Product product left join fetch product.labels left join fetch product.categories",
        countQuery = "select count(distinct product) from Product product")
    Page<Product> findAllWithEagerRelationships(Pageable pageable);*/

   /* @Query(value = "select distinct product from Product product left join fetch product.labels left join fetch product.categories")
    List<Product> findAllWithEagerRelationships();*/

    /*@Query("select product from Product product left join fetch product.labels left join fetch product.categories where product.id =:id")
    Optional<Product> findOneWithEagerRelationships(@Param("id") Long id);*/
}
