package ru.diasoft.micro.service;

import liquibase.pro.packaged.G;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.diasoft.micro.domain.*;
import ru.diasoft.micro.repository.SmsVerificationRepository;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SmsVerificationServiceTest {

    @Mock
    private SmsVerificationRepository repository;

    private SmsVerificationPrimaryService service;


    private final String PHONE_NUMBER = "12354";
    private final String VALID_SECRET_CODE = "0008";
    private final String INVALID_SECRET_CODE = "9999";
    private final String GUID = UUID.randomUUID().toString();
    private final String STATUS = "OK";

    @Before
    public void init(){
        service = new SmsVerificationPrimaryService(repository);

        SmsVerification smsVerification = SmsVerification.builder()
            .processGuid(GUID)
            .phoneNumber(PHONE_NUMBER)
            .secretCode(VALID_SECRET_CODE)
            .status(STATUS)
            .build();


        when(repository.findBySecretCodeAndProcessGuidAndStatus(VALID_SECRET_CODE, GUID, STATUS))
            .thenReturn(Optional.of(smsVerification));
        when(repository.findBySecretCodeAndProcessGuidAndStatus(INVALID_SECRET_CODE, GUID, STATUS))
            .thenReturn(Optional.empty());
    }

    //Get
    @Test
    public void testDsSmsVerificationCheck_Valid() {
        SmsVerificationCheckResponse getResponse = service.dsSmsVerificationCheck(new SmsVerificationCheckRequest(GUID, VALID_SECRET_CODE));
        Assert.assertTrue(getResponse.getCheckResult());

    }

    //Get
    @Test
    public void testDsSmsVerificationCheck_Invalid() {
        SmsVerificationCheckResponse getResponse = service.dsSmsVerificationCheck(new SmsVerificationCheckRequest(GUID, INVALID_SECRET_CODE));
        Assert.assertFalse(getResponse.getCheckResult());

    }

    //Post
    @Test
    public void testDsSmsVerificationCreate() {
        SmsVerificationPostResponse response = service.dsSmsVerificationCreate(new SmsVerificationPostRequest(PHONE_NUMBER));
        Assert.assertNotNull(response.getProcessGUID());
    }

}
