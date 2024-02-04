package ru.inno.task5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.inno.task5.model.ProductRegistry;

import java.util.Optional;

@Repository
public interface ProductRegistryRepository extends JpaRepository<ProductRegistry, Long> {

    @Query("select tpr from tpp_product_register tpr where tpr.product_id = :product_id and tpr.type = :type")
    Optional<ProductRegistry> findByProductIdAndType(@Param("product_id") Long product_id, @Param("type") String type);

}
