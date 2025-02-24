package com.cloud.bank.movements;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.yml")
@ActiveProfiles("test")
class ApiCloudBankMovementsApplicationTests {

    @Test
    void contextLoads() {
    }

}
