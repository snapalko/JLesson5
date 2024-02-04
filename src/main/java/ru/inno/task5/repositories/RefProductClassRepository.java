package ru.inno.task5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.inno.task5.model.RefProductClass;

import java.util.Optional;

@Repository
public interface RefProductClassRepository extends JpaRepository<RefProductClass, Long> {

    @Query("select trpc from tpp_ref_product_class trpc where trpc.value = :value")
    Optional<RefProductClass> findOneByValue(@Param("value") String value);

}
