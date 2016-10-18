package fi.otavanopisto.restfulptv.test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.http.ContentType;

@SuppressWarnings ("squid:S1192")
public class OrganizationsServicesTestIT extends AbstractIntegrationTest {
  
  @Before
  public void beforeTest() throws InterruptedException {
    getPtvMocker()
      .mockOrganizations("5f648c3f-20e2-4d84-83ee-2e2e307a90f2", "cc2cfb59-e814-44cb-95cd-a35b64c0a478", "cdc0c5ea-a57a-41ae-ad0f-c8f920c7cd19")
      .startMock();
    
    waitApiListCount("/organizations", 3);
    waitApiListCount(String.format("/organizations/%s/organizationServices", "5f648c3f-20e2-4d84-83ee-2e2e307a90f2"), 3);
  }

  @After
  public void afterClass() {
    getPtvMocker().endMock();
  }
    
  @Test
  public void testFindOrganizationService() {
    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/organizations/{organizationId}/organizationServices/{organizationServiceId}", "5f648c3f-20e2-4d84-83ee-2e2e307a90f2", "5f648c3f-20e2-4d84-83ee-2e2e307a90f2+204e6847-4b3c-4930-950d-bab617b5539d")
      .then()
      .assertThat()
      .statusCode(200)
      .body("id", is("5f648c3f-20e2-4d84-83ee-2e2e307a90f2+204e6847-4b3c-4930-950d-bab617b5539d"))
      .body("serviceId", is("204e6847-4b3c-4930-950d-bab617b5539d"))
      .body("organizationId", is("5f648c3f-20e2-4d84-83ee-2e2e307a90f2"))
      .body("roleType", is("Responsible"))
      .body("provisionType", nullValue())
      .body("additionalInformation.size()", is(0))
      .body("webPages.size()", is(0));
  }
  
  @Test
  public void testListOrganizationServices() {
    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/organizations/{organizationId}/organizationServices", "5f648c3f-20e2-4d84-83ee-2e2e307a90f2")
      .then()
      .assertThat()
      .statusCode(200)
      .body("id.size()", is(3))
      .body("id[0]", is("5f648c3f-20e2-4d84-83ee-2e2e307a90f2+048dac43-c497-4dad-ac8f-08bd95da3dba"))
      .body("serviceId[0]", is("048dac43-c497-4dad-ac8f-08bd95da3dba"))
      .body("organizationId[0]", is("5f648c3f-20e2-4d84-83ee-2e2e307a90f2"))
      .body("roleType[0]", is("Responsible"))
      .body("provisionType[0]", nullValue())
      .body("additionalInformation[0].size()", is(0))
      .body("webPages[0].size()", is(0));
  }
  
  @Test
  public void testListOrganizationsLimits() {
    assertListLimits(String.format("/organizations/%s/organizationServices", "5f648c3f-20e2-4d84-83ee-2e2e307a90f2"), 3);
  }

  
}
