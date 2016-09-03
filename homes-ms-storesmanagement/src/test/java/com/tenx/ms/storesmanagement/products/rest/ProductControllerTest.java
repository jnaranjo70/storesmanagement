package com.tenx.ms.storesmanagement.products.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.storesmanagement.StoresmanagementServiceApp;
import com.tenx.ms.storesmanagement.products.rest.dto.ProductDTO;
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
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StoresmanagementServiceApp.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
public class ProductControllerTest extends AbstractIntegrationTest {


    private final RestTemplate template = new TestRestTemplate();

    private static final Long NOT_VALID_ID = 11111L;
    private static final Long VALID_ID = 1L;

    private static final Long NOT_VALID_STORE_ID = 11111L;
    private static final Long VALID_STORE_ID = 1L;

    @Autowired
    private ObjectMapper mapper;

    private final ClassLoader CLASSLOADER = Thread.currentThread().getContextClassLoader();

    @Value("testData/success/product/successTestProduct.json")
    private String successTestProductFilePath;

    @Value("testData/success/product/initialTestProduct.json")
    private String initialTestProductFilePath;

     @Value("testData/errors/product/errorTestProduct.json")
    private String errorTestProductFilePath;


    @Test
    @FlywayTest
    public void testGetProducts() {
        try {
            String requestURI = basePath() + RestConstants.VERSION_ONE + "/products/"+VALID_STORE_ID.toString() ;

            ResponseEntity<String> response = getJSONResponse(template, requestURI, null, HttpMethod.GET);
            String resultJSON = response.getBody();
            assertEquals("For a success request the HTTP staus code should be " + HttpStatus.OK.toString(), HttpStatus.OK, response.getStatusCode());

            List<ProductDTO> products = mapper.readValue(resultJSON, new TypeReference<List<ProductDTO>>() { });

            assertNotNull("Returned Products list is null", products);

            Long[] storesIds = products.stream().map(product -> product.getStoreId()).collect(Collectors.toList()).toArray(new Long[0]);
            assertEquals("Not correct of products retuned for Store with ID" + VALID_STORE_ID.toString(),2, storesIds.length);
            assertArrayEquals("Not al products returned belongs to Store with ID" + VALID_STORE_ID.toString(), new Long[]{VALID_STORE_ID, VALID_STORE_ID},storesIds);


        } catch (IOException e) {
            fail(e.getMessage());
        }
    }


    @Test
    @FlywayTest
    public void testGetProductByProductId() {


        try {
            String requestURI = basePath() + RestConstants.VERSION_ONE + "/products/"+VALID_STORE_ID.toString()+"/";

            String initialTestProduct = FileUtils.readFileToString( new File(CLASSLOADER.getResource(initialTestProductFilePath).getFile()));
            ProductDTO initialProductDTO = mapper.readValue(initialTestProduct, ProductDTO.class);
            String productName = initialProductDTO.getName();

            ResponseEntity<String> response = getJSONResponse(template, requestURI + VALID_ID, null, HttpMethod.GET);
            String resultJSON = response.getBody();
            assertEquals("For a success request the HTTP staus code should be " + HttpStatus.OK.toString(), HttpStatus.OK, response.getStatusCode());
            ProductDTO product = mapper.readValue(resultJSON, ProductDTO.class);

            assertNotNull("Returned Product is null", product);
            assertEquals("Returned Product ID is not the expected", VALID_ID, product.getProductId());
            assertEquals("Returned Product name is not the expected",productName,product.getName());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testGetProductByIdNotfound() {
        String requestURI = basePath() + RestConstants.VERSION_ONE + "/products/"+VALID_STORE_ID.toString()+"/";

        ResponseEntity<String> response = getJSONResponse(template, requestURI + NOT_VALID_ID, null, HttpMethod.GET);
        assertEquals("For a not found request the HTTP staus code should be " + HttpStatus.NOT_FOUND.toString(), HttpStatus.NOT_FOUND, response.getStatusCode());
    }



    @Test
    @FlywayTest
    public void testCreateProduct() {
        try {
            String requestURI = basePath() + RestConstants.VERSION_ONE + "/products/"+VALID_STORE_ID.toString()+"/";

            String successTestProduct = FileUtils.readFileToString(new File(CLASSLOADER.getResource(successTestProductFilePath).getFile()));

            ResponseEntity<String> response = getJSONResponse(template, requestURI, successTestProduct, HttpMethod.POST);
            String result = response.getBody();

            assertEquals("For a creation request the HTTP staus code should be " + HttpStatus.CREATED.toString(), HttpStatus.CREATED, response.getStatusCode());

            ResourceCreated<Long> resourceResult = mapper.readValue(result, new TypeReference<ResourceCreated<Long>>() { });

            Long productId = resourceResult.getId();
            assertThat("Created Product ID is not valid", productId, greaterThan(5L));

        } catch (IOException e) {
            fail(e.getMessage());
        }

    }

    @Test
    @FlywayTest
    public void testNotValidStoreOnCreateProduct() {
        try {
            String requestURI = basePath() + RestConstants.VERSION_ONE + "/products/"+NOT_VALID_STORE_ID.toString()+"/";

            String successTestProduct = FileUtils.readFileToString(new File(CLASSLOADER.getResource(successTestProductFilePath).getFile()));
            ResponseEntity<String> response = getJSONResponse(template, requestURI, successTestProduct, HttpMethod.POST);
            assertEquals("For an invalidd input storeId request the HTTP staus code should be" + HttpStatus.PRECONDITION_FAILED.toString(), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testValidationOnCreateProduct() {
        try {
            String requestURI = basePath() + RestConstants.VERSION_ONE + "/products/" + VALID_STORE_ID.toString() + "/";

            String errorTestProduct = FileUtils.readFileToString(new File(CLASSLOADER.getResource(errorTestProductFilePath).getFile()));
            ResponseEntity<String> response = getJSONResponse(template, requestURI, errorTestProduct, HttpMethod.POST);
            assertEquals("For an invalidad input data request the HTTP staus code should be" + HttpStatus.PRECONDITION_FAILED.toString(), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

}



