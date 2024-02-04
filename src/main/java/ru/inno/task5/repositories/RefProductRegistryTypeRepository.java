package ru.inno.task5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.inno.task5.model.RefProductRegistryType;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefProductRegistryTypeRepository extends JpaRepository<RefProductRegistryType, Long> {

    @Query("select t from tpp_ref_product_register_type t where t.value = :value")
    Optional<RefProductRegistryType> findOneByValue(@Param("value") String value);

    @Query("select t from tpp_ref_product_register_type t where t.product_class_code = :product_class_code and t.account_type = :account_type")
    List<RefProductRegistryType> findAllByProductClassCodeAndAccountType(
                                                            @Param("product_class_code") String product_class_code,
                                                            @Param("account_type") String account_type);
}
