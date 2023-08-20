package com.dudis.foodorders.integration.configuration;

import com.dudis.foodorders.FoodOrdersApplication;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.CustomerJpaRepository;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.DeveloperJpaRepository;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.OwnerJpaRepository;
import com.dudis.foodorders.infrastructure.security.RegistrationService;
import com.dudis.foodorders.infrastructure.security.repository.jpa.AccountJpaRepository;
import com.dudis.foodorders.infrastructure.security.repository.jpa.ConfirmationTokenJpaRepository;
import com.dudis.foodorders.services.EmailSender;
import com.dudis.foodorders.utils.AccountUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;

@ActiveProfiles("test")
@Import(PersistanceContainerTestConfiguration.class)
@SpringBootTest(
    classes = FoodOrdersApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public abstract class AbstractIntegrationTest {

    @Autowired
    private OwnerJpaRepository ownerJpaRepository;
    @Autowired
    private CustomerJpaRepository customerJpaRepository;
    @Autowired
    private DeveloperJpaRepository developerJpaRepository;
    @Autowired
    private AccountJpaRepository accountJpaRepository;
    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private ConfirmationTokenJpaRepository confirmationTokenJpaRepository;

    @MockBean
    private EmailSender emailSender;

    @LocalServerPort
    protected int port;

    @Value("${server.servlet.context-path}")
    protected String basePath;

    @BeforeEach
    public void before() throws ReflectiveOperationException {
        doNothing().when(emailSender).send(anyString(),anyString());
        ReflectionTestUtils.setField(registrationService, "port", String.valueOf(port));
        String customerToken = registrationService.registerAccount(AccountUtils.customerRequest());
        String developerToken = registrationService.registerAccount(AccountUtils.developerRequest());
        String ownerToken = registrationService.registerAccount(AccountUtils.ownerRequest());

        List.of(customerToken, developerToken, ownerToken).forEach(t->registrationService.confirmToken(
            t.substring(t.indexOf("=")+1)));
//        confirmationTokenJpaRepository.deleteAll();
    }

    @AfterEach
    public void after() {
        confirmationTokenJpaRepository.deleteAll();
        customerJpaRepository.deleteAll();
        ownerJpaRepository.deleteAll();
        developerJpaRepository.deleteAll();
        accountJpaRepository.deleteAll();
    }
}
