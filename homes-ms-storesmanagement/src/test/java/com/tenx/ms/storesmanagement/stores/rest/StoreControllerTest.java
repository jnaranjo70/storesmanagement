package com.tenx.ms.storesmanagement.stores.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.storesmanagement.StoresmanagementServiceApp;
import com.tenx.ms.storesmanagement.stores.rest.dto.StoreDTO;
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
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StoresmanagementServiceApp.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
public class StoreControllerTest extends AbstractIntegrationTest {


    private final RestTemplate template = new TestRestTemplate();

    private static final Long NOT_VALID_ID = 11111L;
    private static final Long VALID_ID = 1L;

    @Autowired
    private ObjectMapper mapper;

    private final ClassLoader CLASSLOADER = Thread.currentThread().getContextClassLoader();

    @Value("testData/success/store/successTestStore.json")
    private String successTestStoreFilePath;

    @Value("testData/success/store/initialTestStore.json")
    private String initialTestStoreFilePath;

    @Value("testData/errors/store/errorTestStore.json")
    private String errorTestStoreFilePath;

    @Test
    @FlywayTest
    public void testGetStores() {
        try {
            String requestURI = basePath() + RestConstants.VERSION_ONE + "/stores/";

            ResponseEntity<String> response = getJSONResponse(template, requestURI, null, HttpMethod.GET);
            String resultJSON = response.getBody();
            assertEquals("For a success request the HTTP staus code should be " + HttpStatus.OK.toString(), HttpStatus.OK, response.getStatusCode());

            List<StoreDTO> stores = mapper.readValue(resultJSON, new TypeReference<List<StoreDTO>>() { });

            assertNotNull("Returned Stores list is null", stores);

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }


    @Test
    @FlywayTest
    public void testGetStoreByStoreId() {


        try {
            String requestURI = basePath() + RestConstants.VERSION_ONE + "/stores/";

            String initialTestStore = FileUtils.readFileToString( new File(CLASSLOADER.getResource(initialTestStoreFilePath).getFile()));
            StoreDTO initialStoreDTO = mapper.readValue(initialTestStore, StoreDTO.class);
            String storeName = initialStoreDTO.getName();

            ResponseEntity<String> response = getJSONResponse(template, requestURI + VALID_ID, null, HttpMethod.GET);
            String resultJSON = response.getBody();
            assertEquals("For a success request the HTTP staus code should be " + HttpStatus.OK.toString(), HttpStatus.OK, response.getStatusCode());
            StoreDTO store = mapper.readValue(resultJSON, StoreDTO.class);

            assertNotNull("Returned Store is null", store);
            assertEquals("Returned Store ID is not the expected", store.getStoreId(), VALID_ID);
            assertEquals("Returned Store name is not the expected", store.getName(), storeName);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testGetStoreByIdNotfound() {
        String requestURI = basePath() + RestConstants.VERSION_ONE + "/stores/";

        ResponseEntity<String> response = getJSONResponse(template, requestURI + NOT_VALID_ID, null, HttpMethod.GET);
        assertEquals("For a not found request the HTTP staus code should be " + HttpStatus.NOT_FOUND.toString(), HttpStatus.NOT_FOUND, response.getStatusCode());
    }



    @Test
    @FlywayTest
    public void testCreateStore() {
        try {
            String requestURI = basePath() + RestConstants.VERSION_ONE + "/stores/";

            String successTestStore = FileUtils.readFileToString(new File(CLASSLOADER.getResource(successTestStoreFilePath).getFile()));

            ResponseEntity<String> response = getJSONResponse(template, requestURI, successTestStore, HttpMethod.POST);
            String result = response.getBody();

            assertEquals("For a creation request the HTTP staus code should be " + HttpStatus.CREATED.toString(), HttpStatus.CREATED, response.getStatusCode());

            ResourceCreated<Long> resourceResult = mapper.readValue(result, new TypeReference<ResourceCreated<Long>>() { });

            Long storeId = resourceResult.getId();
            assertThat("Created Store ID is not valid", storeId, greaterThan(1L));

        } catch (IOException e) {
            fail(e.getMessage());
        }

    }

    @Test
    @FlywayTest
    public void testValidationOnCreateStore() {
        try {
            String requestURI = basePath() + RestConstants.VERSION_ONE + "/stores/";

            String errorTestStore = FileUtils.readFileToString(new File(CLASSLOADER.getResource(errorTestStoreFilePath).getFile()));
            ResponseEntity<String> response = getJSONResponse(template, requestURI, errorTestStore, HttpMethod.POST);
            assertEquals("For an invalidad input data request the HTTP staus code should be" + HttpStatus.PRECONDITION_FAILED.toString(), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

}


