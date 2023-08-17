package com.dudis.foodorders.integration.configuration;

import com.dudis.foodorders.infrastructure.security.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;

public abstract class ControllersSupport {

    @MockBean
    private SecurityUtils securityUtils;

    @BeforeEach
    void checkSecurity() {
        doNothing().when(securityUtils).checkAccess(anyInt(),any(HttpServletRequest.class));
    }
}
