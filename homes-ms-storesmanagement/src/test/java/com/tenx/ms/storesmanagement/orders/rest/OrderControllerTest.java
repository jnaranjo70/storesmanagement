package com.tenx.ms.storesmanagement.orders.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.storesmanagement.StoresmanagementServiceApp;
import com.tenx.ms.storesmanagement.orders.rest.dto.OrderResponseDTO;
import org.apache.commons.io.FileUtils;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StoresmanagementServiceApp.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
public class OrderControllerTest extends AbstractIntegrationTest {


    private final RestTemplate template = new TestRestTemplate();

    private static final Long NOT_VALID_ID = 11111L;
    private static final Long VALID_ID = 1L;

    private static final Long NOT_VALID_STORE_ID = 11111L;
    private static final Long VALID_STORE_ID = 1L;

    @Autowired
    private ObjectMapper mapper;

    private final ClassLoader CLASSLOADER = Thread.currentThread().getContextClassLoader();

    @Value("testData/success/order/successTestOrder.json")
    private String successTestOrderFilePath;

    @Value("testData/errors/order/errorTestOrder.json")
    private String errorTestOrderFilePath;


    @Test
    @FlywayTest
    public void testCreateOrder() {
        try {
            String requestURI = basePath() + RestConstants.VERSION_ONE + "/orders/"+VALID_STORE_ID.toString()+"/";

            String successTestOrder = FileUtils.readFileToString(new File(CLASSLOADER.getResource(successTestOrderFilePath).getFile()));

            ResponseEntity<String> response = getJSONResponse(template, requestURI, successTestOrder, HttpMethod.POST);
            String result = response.getBody();

            assertEquals("For a creation request the HTTP staus code should be " + HttpStatus.CREATED.toString(), HttpStatus.CREATED, response.getStatusCode());
            assertNotNull("Response could not be null for a success creation",result);

            OrderResponseDTO resourceResult = mapper.readValue(result, OrderResponseDTO.class);

            assertThat("Created Oredr ID is not valid", resourceResult.getOrderId(), greaterThanOrEqualTo(1L));
            assertEquals("Created Order Status is not valid", 1, resourceResult.getStatus());

        } catch (IOException e) {
            fail(e.getMessage());
        }

    }

    @Test
    @FlywayTest
    public void testNotValidStoreOnCreateOrder() {
        try {
            String requestURI = basePath() + RestConstants.VERSION_ONE + "/orders/"+NOT_VALID_STORE_ID.toString()+"/";

            String successTestOrder = FileUtils.readFileToString(new File(CLASSLOADER.getResource(successTestOrderFilePath).getFile()));
            ResponseEntity<String> response = getJSONResponse(template, requestURI, successTestOrder, HttpMethod.POST);
            assertEquals("For an invalidd input storeId request the HTTP staus code should be " + HttpStatus.PRECONDITION_FAILED.toString(), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testValidationOnCreateOrder() {
        try {
            String requestURI = basePath() + RestConstants.VERSION_ONE + "/orders/" + VALID_STORE_ID.toString() + "/";

            String errorTestOrder = FileUtils.readFileToString(new File(CLASSLOADER.getResource(errorTestOrderFilePath).getFile()));
            ResponseEntity<String> response = getJSONResponse(template, requestURI, errorTestOrder, HttpMethod.POST);
            assertEquals("For an invalidad input data request the HTTP staus code should be" + HttpStatus.PRECONDITION_FAILED.toString(), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

}



