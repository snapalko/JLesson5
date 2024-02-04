package ru.inno.task5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.inno.task5.model.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select tp from tpp_product tp where tp.number = :contractNumber")
    public Optional<Product> findByContractNumber(@Param("contractNumber") String contractNumber);
}
