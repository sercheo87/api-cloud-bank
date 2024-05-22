package com.cloud.bank.clients.karate;

import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.Disabled;

@Disabled
public class Cliente2eTest {

    @Karate.Test
    Karate testCreateInvalidUser() {
        return Karate.run("clients").relativeTo(getClass());
    }

}
