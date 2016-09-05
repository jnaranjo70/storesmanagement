package com.tenx.ms.storesmanagement.stock.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.storesmanagement.StoresmanagementServiceApp;
import com.tenx.ms.storesmanagement.stock.rest.dto.StockDTO;
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StoresmanagementServiceApp.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
public class StockControllerTest extends AbstractIntegrationTest {


    private final RestTemplate template = new TestRestTemplate();

    private static final Long NOT_VALID_PRODUCT_ID = 11111L;
    private static final Long VALID_PRODUCT_ID = 1L;

    private static final Long NOT_VALID_STORE_ID = 11111L;
    private static final Long VALID_STORE_ID = 1L;

    @Autowired
    private ObjectMapper mapper;

    private final ClassLoader CLASSLOADER = Thread.currentThread().getContextClassLoader();

    @Value("testData/success/stock/successTestStock.json")
    private String successTestStockFilePath;

    @Value("testData/success/stock/initialTestStock.json")
    private String initialTestStockFilePath;

     @Value("testData/errors/stock/errorTestStock.json")
    private String errorTestStockFilePath;


    @Test
    @FlywayTest
    public void testGetStockByStoreIdAndByProductId() {
        try {
            String requestURI = basePath() + RestConstants.VERSION_ONE + "/stock/"+VALID_STORE_ID.toString() + "/" + VALID_PRODUCT_ID.toString();

            String initialTestStock = FileUtils.readFileToString( new File(CLASSLOADER.getResource(initialTestStockFilePath).getFile()));
            StockDTO initialStockDTO = mapper.readValue(initialTestStock, StockDTO.class);

            ResponseEntity<String> response = getJSONResponse(template, requestURI, null, HttpMethod.GET);
            String resultJSON = response.getBody();
            assertEquals("For a success request the HTTP staus code should be " + HttpStatus.OK.toString(), HttpStatus.OK, response.getStatusCode());

            StockDTO stockDTO = mapper.readValue(resultJSON, StockDTO.class);

            assertNotNull("Returned Stock item is null", stockDTO);
            assertEquals("Not correct count for Stock" ,initialStockDTO.getCount(), stockDTO.getCount());
            assertEquals("Not correct Store ID for Stock" ,initialStockDTO.getStoreId(), stockDTO.getStoreId());
            assertEquals("Not correct Product ID for Stock" ,initialStockDTO.getProductId(), stockDTO.getProductId());

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }


    @Test
    @FlywayTest
    public void testGetStockByStoreIdAndByProductIdNotfound() {
        //not valid store and not valid product
        String requestURI = basePath() + RestConstants.VERSION_ONE + "/stock/"+NOT_VALID_STORE_ID.toString()+"/"+NOT_VALID_PRODUCT_ID.toString();

        ResponseEntity<String> response = getJSONResponse(template, requestURI, null, HttpMethod.GET);
        assertEquals("For a not found request the HTTP staus code should be " + HttpStatus.NOT_FOUND.toString(), HttpStatus.NOT_FOUND, response.getStatusCode());

        //valid store and not valid product
        requestURI = basePath() + RestConstants.VERSION_ONE + "/stock/"+VALID_STORE_ID.toString()+"/"+NOT_VALID_PRODUCT_ID.toString();

        response = getJSONResponse(template, requestURI, null, HttpMethod.GET);
        assertEquals("For a not found request the HTTP staus code should be " + HttpStatus.NOT_FOUND.toString(), HttpStatus.NOT_FOUND, response.getStatusCode());

        //not valid store and valid product
        requestURI = basePath() + RestConstants.VERSION_ONE + "/stock/"+NOT_VALID_STORE_ID.toString()+"/"+VALID_PRODUCT_ID.toString();

        response = getJSONResponse(template, requestURI, null, HttpMethod.GET);
        assertEquals("For a not found request the HTTP staus code should be " + HttpStatus.NOT_FOUND.toString(), HttpStatus.NOT_FOUND, response.getStatusCode());
    }



    @Test
    @FlywayTest
    public void testCreateStock() {
        try {
            String requestURI = basePath() + RestConstants.VERSION_ONE + "/stock/"+VALID_STORE_ID.toString()+"/"+VALID_PRODUCT_ID.toString();

            String successTestStock = FileUtils.readFileToString(new File(CLASSLOADER.getResource(successTestStockFilePath).getFile()));

            ResponseEntity<String> response = getJSONResponse(template, requestURI, successTestStock, HttpMethod.POST);
            assertEquals("For a creation request the HTTP staus code should be " + HttpStatus.CREATED.toString(), HttpStatus.CREATED, response.getStatusCode());

        } catch (IOException e) {
            fail(e.getMessage());
        }

    }

    @Test
    @FlywayTest
    public void testNotValidPathsOnCreateStock() {
        try {
             String successTestProduct = FileUtils.readFileToString(new File(CLASSLOADER.getResource(successTestStockFilePath).getFile()));

            //not valid store and valid product
            String requestURI = basePath() + RestConstants.VERSION_ONE + "/stock/"+NOT_VALID_STORE_ID.toString()+"/"+VALID_PRODUCT_ID.toString();
            ResponseEntity<String> response = getJSONResponse(template, requestURI, successTestProduct, HttpMethod.POST);
            assertEquals("For an invalidd input storeId request the HTTP staus code should be" + HttpStatus.PRECONDITION_FAILED.toString(), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());

            //valid store and not valid product
            requestURI = basePath() + RestConstants.VERSION_ONE + "/stock/"+VALID_STORE_ID.toString()+"/"+NOT_VALID_PRODUCT_ID.toString();
            response = getJSONResponse(template, requestURI, successTestProduct, HttpMethod.POST);
            assertEquals("For an invalidd input productId request the HTTP staus code should be" + HttpStatus.PRECONDITION_FAILED.toString(), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testValidationOnCreateStock() {
        try {
            String requestURI = basePath() + RestConstants.VERSION_ONE + "/stock/" + VALID_STORE_ID.toString() + "/"+VALID_PRODUCT_ID.toString();

            String errorTestStock = FileUtils.readFileToString(new File(CLASSLOADER.getResource(errorTestStockFilePath).getFile()));

            ResponseEntity<String> response = getJSONResponse(template, requestURI, errorTestStock, HttpMethod.POST);
            assertEquals("For an invalidad input data request the HTTP staus code should be" + HttpStatus.PRECONDITION_FAILED.toString(), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

}



