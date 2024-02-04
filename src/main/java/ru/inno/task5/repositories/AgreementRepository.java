package ru.inno.task5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.inno.task5.model.Agreement;

import java.util.Optional;

public interface AgreementRepository extends JpaRepository<Agreement, Long> {
    @Query(value = "select ag from agreements ag where ag.number = :contractNumber")
    Optional<Agreement> findByContractNumber(@Param("contractNumber") String contractNumber);
}
