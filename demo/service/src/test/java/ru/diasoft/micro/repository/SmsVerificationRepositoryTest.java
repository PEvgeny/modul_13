package ru.diasoft.micro.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.diasoft.micro.domain.SmsVerification;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SmsVerificationRepositoryTest {

    @Autowired
    private SmsVerificationRepository repository;

    @Test
    public void smsVerificationCreateTest(){
        SmsVerification smsVerification = SmsVerification.builder()
            .processGuid(UUID.randomUUID().toString())
            .phoneNumber("1231")
            .secretCode("0008")
            .status("WAIT")
            .build();

        SmsVerification createdEntity = repository.save(smsVerification);
        Assert.assertNotNull(createdEntity.getVerificationId());
    }

    @Test
    public void findBySecretCodeAndProcessGuidAndStatusTest(){
        String processGuid = UUID.randomUUID().toString();
        String secretCode = "0008";
        String status = "WAIT";

        SmsVerification smsVerification = SmsVerification.builder()
            .processGuid(processGuid)
            .phoneNumber("1231")
            .secretCode(secretCode)
            .status(status)
            .build();

        SmsVerification createdEntity = repository.save(smsVerification);


        Assert.assertNotNull(repository.findBySecretCodeAndProcessGuidAndStatus(secretCode, processGuid, status));
    }
}
