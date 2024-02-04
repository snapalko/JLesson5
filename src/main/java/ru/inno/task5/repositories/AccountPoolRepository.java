package ru.inno.task5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.inno.task5.model.AccountTemp;
import ru.inno.task5.model.Account_pool;

import java.util.Optional;

@Repository
public interface AccountPoolRepository extends JpaRepository<AccountTemp, Long> {

    @Query(value = """
            select ap.id, ap.branchcode, ap.currencycode, ap.mdmcode, ap.prioritycode,\s
                ap.registrytypecode, ap.accounts[1]\s
            from account_pool ap\s
               where ap.branchcode = :branchCode and ap.currencycode = :currencyCode\s
                   and ap.mdmcode = :mdmCode and ap.prioritycode = :priorityCode\s
                   and ap.registrytypecode = :registryTypeCode""", nativeQuery = true)
    Optional<AccountTemp> findAccount(@Param("branchCode") String branchCode,
                                      @Param("currencyCode") String currencyCode,
                                      @Param("mdmCode") String mdmCode,
                                      @Param("priorityCode") String priorityCode,
                                      @Param("registryTypeCode") String registryTypeCode);

    @Query(value = "select ap from account_pool ap where ap.registryTypeCode = :registryTypeCode")
    Optional<Account_pool> findByRegistryTypeCode(@Param("registryTypeCode") String registryTypeCode);
}
