package ru.itmo.model.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.itmo.model.client.validate.IValidatorClient;
import ru.itmo.model.client.validate.ValidatorClient;

public class BuildClientTests {
    IClientBuilder clientBuilderValidate;

    @BeforeEach
    void setUp(){
        IValidatorClient validatorClient = new ValidatorClient();
        clientBuilderValidate = new ClientBuilder(validatorClient);
    }

    @Test
    void build_whenWrongName_returnNull(){

        Assertions.assertNull(clientBuilderValidate.addFirstname("Nikita89")
                .addLastname("Kolesnikov").build());

    }

    @Test
    void build_whenCorrectName_returnClient(){

        Assertions.assertEquals(
                clientBuilderValidate.addFirstname("Nikita").addLastname("Kolesnikov").build(),
                new Client("Nikita", "Kolesnikov", null, null));

    }

    @Test
    void build_whenWrongAddress_returnNull(){
        Assertions.assertNull(clientBuilderValidate.addFirstname("Nikita")
                .addLastname("Kolesnikov")
                .addAddress("Prospect 5=")
                .build());
    }

    @Test
    void build_whenCorrectAddress_returnClient(){
        Assertions.assertEquals(
                clientBuilderValidate.addFirstname("Nikita").addLastname("Kolesnikov").addAddress("Prospect 5").build(),
                new Client("Nikita", "Kolesnikov", "Prospect 5", null));
    }

    @Test
    void build_whenWrongPassportNumber_returnNull(){
        Assertions.assertNull(clientBuilderValidate.addFirstname("Nikita")
                .addLastname("Kolesnikov")
                .addAddress("Prospect 5")
                .addPassportNumber("64327876+")
                .build());
    }

    @Test
    void build_whenCorrectPassportNumber_returnClient(){
        Assertions.assertEquals(clientBuilderValidate.addFirstname("Nikita").addLastname("Kolesnikov")
                        .addAddress("Prospect 5").addPassportNumber("64327876").build(),
                new Client("Nikita", "Kolesnikov", "Prospect 5", "64327876"));
    }

}
