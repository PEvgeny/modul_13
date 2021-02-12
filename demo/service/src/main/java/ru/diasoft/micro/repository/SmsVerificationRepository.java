package ru.diasoft.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.micro.domain.SmsVerification;

import java.util.Optional;

@Repository
public interface SmsVerificationRepository extends JpaRepository<SmsVerification, Long> {
    Optional<SmsVerification> findBySecretCodeAndProcessGuidAndStatus(String secretCode, String processGuid, String status);
}
