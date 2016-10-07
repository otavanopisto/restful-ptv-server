package fi.otavanopisto.restfulptv.test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.jayway.restassured.http.ContentType;

/**
 * Abstract base class for integration tests
 * 
 * @author Heikki Kurhinen
 * @author Antti Leppä
 */
public abstract class AbstractIntegrationTest extends AbstractTest {

  private static Logger logger = Logger.getLogger(AbstractTest.class.getName());
  
  /**
   * Abstract base class for all mockers
   * 
   * @author Antti Leppä
   */
  public class AbstractMocker {
    
    private List<StringGetMock> stringMocks;
    private List<BinaryGetMock> binaryMocks;
    
    /**
     * Constructor
     */
    public AbstractMocker() {
      stringMocks = new ArrayList<>();
      binaryMocks = new ArrayList<>();
    }
    
    /**
     * Mocks binary response for GET request on path
     * 
     * @param path path
     * @param type response content type 
     * @param binaryFile path of mocked file
     */
    public void mockGetBinary(String path, String type, String binaryFile) {
      try (InputStream binaryStream = getClass().getClassLoader().getResourceAsStream(binaryFile)) {
        binaryMocks.add(new BinaryGetMock(path, type, IOUtils.toByteArray(binaryStream)));
      } catch (IOException e) {
        logger.log(Level.SEVERE, "Failed to read mock binary file", e);
        fail(e.getMessage());
      }
    }
    
    /**
     * Mocks string response for GET request on path
     * 
     * @param path path
     * @param type response content type 
     * @param content response content
     */
    public void mockGetString(String path, String type, String content) {
      stringMocks.add(new StringGetMock(path, type, content));
    }
    
    /**
     * Mocks JSON response for GET request on path
     * 
     * @param path path
     * @param object JSON object
     */
    public void mockGetJSON(String path, Object object) {
      try {
        stringMocks.add(new StringGetMock(path, "application/json", new ObjectMapper().writeValueAsString(object)));
      } catch (JsonProcessingException e) {
        logger.log(Level.SEVERE, "Failed to serialize mock JSON object", e);
        fail(e.getMessage());
      }
    }
    
    public void mockGetJSONFile(String path, String file) {
      try (InputStream stream = getClass().getClassLoader().getResourceAsStream(file)) {
        stringMocks.add(new StringGetMock(path, "application/json", IOUtils.toString(stream)));
      } catch (IOException e) {
        logger.log(Level.SEVERE, "Failed to mock JSON file", e);
        fail(e.getMessage());
      }
    }
    
    /**
     * Starts mocking requests
     */
    public void startMock() {
      for (StringGetMock stringMock : stringMocks) {
        createStringMock(stringMock.getPath(), stringMock.getType(), stringMock.getContent());
      }
      
      for (BinaryGetMock binaryMock : binaryMocks) {
        createBinaryMock(binaryMock.getPath(), binaryMock.getType(), binaryMock.getContent());
      }
    }

    /**
     * Ends mocking
     */
    public void endMock() {
      WireMock.reset();
    }
    
    private void createBinaryMock(String path, String type, byte[] binary) {
      stubFor(get(urlEqualTo(path))
        .willReturn(aResponse()
        .withHeader("Content-Type", type)
        .withBody(binary)));
    }
    
    private void createStringMock(String path, String type, String content) {
      stubFor(get(urlEqualTo(path))
          .willReturn(aResponse()
          .withHeader("Content-Type", type)
          .withBody(content)));
    }
    
    private class StringGetMock {

      private String path;
      private String type;
      private String content;
     
      public StringGetMock(String path, String type, String content) {
        this.path = path;
        this.type = type;
        this.content = content;
      }
      
      public String getPath() {
        return path;
      }
     
      public String getContent() {
        return content;
      }
      
      public String getType() {
        return type;
      }
    }
    
    private class BinaryGetMock {

      private String path;
      private String type;
      private byte[] content;
     
      public BinaryGetMock(String path, String type, byte[] content) {
        this.path = path;
        this.type = type;
        this.content = content;
      }
      
      public String getPath() {
        return path;
      }
     
      public byte[] getContent() {
        return content;
      }
      
      public String getType() {
        return type;
      }
    }

  }
  
  public class StatutoryDescriptionMocker extends AbstractMocker {
    
    public StatutoryDescriptionMocker() {
      mockGetJSONFile("/GeneralDescription", "statutorydescriptions/list.json");
      mockStatutoryDescription("18bbc7da-3700-4ebc-b030-d4ca89aafe72");
      mockStatutoryDescription("4ad2dd4d-7ecf-444a-bcfa-b99d79653214");
      mockStatutoryDescription("55167777-e95d-4379-9677-6b90841c01c6");
    }

    private void mockStatutoryDescription(String guid) {
      mockGetJSONFile(String.format("/GeneralDescription/%s", guid), String.format("statutorydescriptions/%s.json", guid));
    }
    
  }
  
  protected void waitApiListCount(String path, String property, int count) throws InterruptedException {
    long timeout = System.currentTimeMillis() + (60 * 1000);
    
    while (true) {
      Thread.sleep(1000);
      
      if (countApiList(path, property) == count) {
        return;
      }
      
      if (System.currentTimeMillis() > timeout) {
        fail(String.format("Timeout waiting for %s to have count of %s equal %d", path, property, count));
      }
    }
  }

  protected int countApiList(String path, String property) {
    return given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get(path)
      .andReturn()
      .body()
      .jsonPath()
      .get("size()");
  }
}
