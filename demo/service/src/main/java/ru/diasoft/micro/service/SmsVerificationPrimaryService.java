package ru.diasoft.micro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.*;
import ru.diasoft.micro.repository.SmsVerificationRepository;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Primary
public class SmsVerificationPrimaryService implements SmsVerificationService {

    private final SmsVerificationRepository repository;

    @Override
    public SmsVerificationCheckResponse dsSmsVerificationCheck(SmsVerificationCheckRequest smsVerificationCheckRequest){
        Optional<SmsVerification> smsVerification = repository.findBySecretCodeAndProcessGuidAndStatus(
            smsVerificationCheckRequest.getCode(), smsVerificationCheckRequest.getProcessGUID(), "OK"
        );

        return new SmsVerificationCheckResponse(smsVerification.isPresent());
    }

    @Override
    public SmsVerificationPostResponse dsSmsVerificationCreate(SmsVerificationPostRequest smsVerificationPostRequest){
        String guid = UUID.randomUUID().toString();
        String secretCode = String.format("%04d", new Random().nextInt(10000));

        SmsVerification smsVerification =SmsVerification.builder()
            .phoneNumber(smsVerificationPostRequest.getPhoneNumber())
            .processGuid(guid)
            .secretCode(secretCode)
            .status("WAITING")
            .build();

        repository.save(smsVerification);

        return new SmsVerificationPostResponse(guid);
    }
}
