package com.dudis.foodorders.integration.support;

import com.dudis.foodorders.infrastructure.security.SecurityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;

public abstract class ControllersSupport {
    public static final Integer OWNER_ID = 123;
    public static final int RESTAURANT_ID = 4;
    public static final int CUSTOMER_ID = 7;

    @MockBean
    private SecurityUtils securityUtils;
    @BeforeEach
    void checkSecurity() {
        doNothing().when(securityUtils).checkAccess(anyInt(),any(HttpServletRequest.class));
    }
}
