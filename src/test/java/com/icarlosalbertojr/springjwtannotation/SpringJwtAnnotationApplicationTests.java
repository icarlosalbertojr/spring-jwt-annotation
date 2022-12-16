package com.icarlosalbertojr.springjwtannotation;

import com.icarlosalbertojr.springjwtannotation.services.AuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringJwtAnnotationApplicationTests {


    private AuthService authService = new AuthService();
    @Test
    void contextLoads() {

        Assertions.assertNotNull(authService.authenticate("email", "senha"));

    }

}
