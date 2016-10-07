package fi.otavanopisto.restfulptv.test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.http.ContentType;

@SuppressWarnings ("squid:S1192")
public class ServicesTestIT extends AbstractIntegrationTest {
  
  @Before
  public void beforeTest() throws InterruptedException {
    getPtvMocker()
      .mockServices("04c01602-cd3a-4ef5-92e4-6a4ee2723e67", "1c75be4e-293b-4503-9658-8e25a380fa5a", "9f97d7d9-509a-4185-a798-4b91b6f1c25f")
      .startMock();

    waitApiListCount("/services", 3);
  }

  @After
  public void afterClass() {
    getPtvMocker().endMock();
  }
    
  @Test
  public void findService() {
    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/services/{serviceId}", "9f97d7d9-509a-4185-a798-4b91b6f1c25f")
      .then()
      .assertThat()
      .statusCode(200)
      .body("id", is("9f97d7d9-509a-4185-a798-4b91b6f1c25f"))
      .body("type", is("Service"))
      .body("statutoryDescriptionId", is((String) null))
      
      .body("serviceClasses.size()", is(2))
      .body("serviceClasses[0].id", is("b34122ef-e758-44df-bb93-328fcdcbe4dc"))
      .body("serviceClasses[0].name", is("Kulttuuri"))
      .body("serviceClasses[0].code", is("P25"))
      .body("serviceClasses[0].ontologyType", is((String) null))
      .body("serviceClasses[0].uri", is("http://urn.fi/URN:NBN:fi:au:ptvl:P25"))
      .body("serviceClasses[0].parentId", is((String) null))
      .body("serviceClasses[0].parentUri", is((String) null))
      
      .body("ontologyTerms.size()", is(4))
      .body("ontologyTerms[0].id", is("eb5f8925-d8ee-47e3-84e9-7aae81007c12"))
      .body("ontologyTerms[0].name", is("torimyyntipaikat"))
      .body("ontologyTerms[0].code", is(""))
      .body("ontologyTerms[0].ontologyType", is("All"))
      .body("ontologyTerms[0].uri", is("http://www.yso.fi/onto/koko/p67723"))
      .body("ontologyTerms[0].parentId", is((String) null))
      .body("ontologyTerms[0].parentUri", is((String) null))
      
      .body("names.size()", is(2))
      .body("names[0].language", is("fi"))
      .body("names[0].value", is("Tapahtuma- ja torimyyntipaikkojen vuokraus"))
      .body("names[0].type", is("Name"))
      
      .body("descriptions.size()", is(3))
      .body("descriptions[0].language", is("fi"))
      .body("descriptions[0].value", is("Kaupunki vuokraa julkisia alueitaan erilaisiin tapahtumiin."))
      .body("descriptions[0].type", is("ShortDescription"))
      
      .body("languages.size()", is(1))
      .body("languages[0]", is("fi"))
      
      .body("targetGroups.size()", is(3))
      .body("targetGroups[0].id", is("b95c9f0a-aef8-4d92-ab49-979baf4c6bbb"))
      .body("targetGroups[0].name", is("Viranomaiset"))
      .body("targetGroups[0].code", is("KR3"))
      .body("targetGroups[0].ontologyType", is((String) null))
      .body("targetGroups[0].uri", is("http://urn.fi/URN:NBN:fi:au:ptvl:KR3"))
      .body("targetGroups[0].parentId", is((String) null))
      .body("targetGroups[0].parentUri", is((String) null))
      
      .body("lifeEvents.size()", is(0))
      .body("industrialClasses.size()", is(0))
      .body("keywords.size()", is(0))
      .body("webPages.size()", is(0))
      .body("coverageType", is("Local"))
      
      .body("municipalities.size()", is(1))
      .body("municipalities[0]", is("Tornio"))

      .body("requirements.size()", is(1))
      .body("requirements[0].value", is("Vuokrasopimus"))
      .body("requirements[0].language", is("fi"))
      
      .body("publishingStatus", is("Published"))
      .body("chargeType", is("Charged"))
      
      .body("additionalInformations.size()", is(1))
      .body("additionalInformations[0].language", is("fi"))
      .body("additionalInformations[0].value", is(""))
      .body("additionalInformations[0].type", is("ChargeType"));
  }
  
  @Test
  public void testListServices() {
    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/services")
      .then()
      .assertThat()
      .statusCode(200)
      .body("id.size()", is(3))
      .body("id[1]", is("1c75be4e-293b-4503-9658-8e25a380fa5a"))
      .body("type[1]", is("Service"))
      .body("statutoryDescriptionId[1]", is((String) null))
      
      .body("serviceClasses[1].size()", is(2))
      .body("serviceClasses[1][0].id", is("a19df93c-150c-4f66-ae1a-12e394fe317a"))
      .body("serviceClasses[1][0].name", is("Veneily"))
      .body("serviceClasses[1][0].code", is("P27.3"))
      .body("serviceClasses[1][0].ontologyType", is((String) null))
      .body("serviceClasses[1][0].uri", is("http://urn.fi/URN:NBN:fi:au:ptvl:P27.3"))
      .body("serviceClasses[1][0].parentId", is("32584bbe-da14-4d14-a355-8feb74879e40"))
      .body("serviceClasses[1][0].parentUri", is((String) null))
      
      .body("ontologyTerms[1].size()", is(2))
      .body("ontologyTerms[1][0].id", is("2baf1da0-59a1-445b-904e-54104a08a98e"))
      .body("ontologyTerms[1][0].name", is("venesatamat"))
      .body("ontologyTerms[1][0].code", is(""))
      .body("ontologyTerms[1][0].ontologyType", is("All"))
      .body("ontologyTerms[1][0].uri", is("http://www.yso.fi/onto/koko/p33341"))
      .body("ontologyTerms[1][0].parentId", is((String) null))
      .body("ontologyTerms[1][0].parentUri", is((String) null))
      
      .body("names[1].size()", is(2))
      .body("names[1][0].language", is("fi"))
      .body("names[1][0].value", is("Venepaikat ja veneilykeskukset"))
      .body("names[1][0].type", is("AlternateName"))
      
      .body("descriptions[1].size()", is(3))
      .body("descriptions[1][0].language", is("fi"))
      .body("descriptions[1][0].value", is("Satamapalveluita veneilijöille."))
      .body("descriptions[1][0].type", is("ShortDescription"))
      
      .body("languages[1].size()", is(1))
      .body("languages[1][0]", is("fi"))
      
      .body("targetGroups[1].size()", is(3))
      .body("targetGroups[1][0].id", is("e7196fa6-e696-4032-a611-83e152f8acfe"))
      .body("targetGroups[1][0].name", is("Yritykset"))
      .body("targetGroups[1][0].code", is("KR2"))
      .body("targetGroups[1][0].ontologyType", is((String) null))
      .body("targetGroups[1][0].uri", is("http://urn.fi/URN:NBN:fi:au:ptvl:KR2"))
      .body("targetGroups[1][0].parentId", is((String) null))
      .body("targetGroups[1][0].parentUri", is((String) null))
      
      .body("lifeEvents[1].size()", is(0))
      .body("industrialClasses[1].size()", is(0))
      .body("keywords[1].size()", is(2))
      .body("keywords[1][0]", is("laituripaikat"))
      .body("webPages[1].size()", is(0))
      .body("coverageType[1]", is("Local"))
      
      .body("municipalities[1].size()", is(1))
      .body("municipalities[1][0]", is("Tornio"))

      .body("requirements[1].size()", is(1))
      .body("requirements[1][0].value", is("Vuokrasopimus"))
      .body("requirements[1][0].language", is("fi"))
      
      .body("publishingStatus[1]", is("Published"))
      .body("chargeType[1]", is("Charged"))
      
      .body("additionalInformations[1].size()", is(5))
      .body("additionalInformations[1][0].language", is("fi"))
      .body("additionalInformations[1][0].value", is(""))
      .body("additionalInformations[1][0].type", is("ChargeType"));
  }
  
  @Test
  public void testListServicesLimits() {
    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/services?firstResult=1")
      .then()
      .assertThat()
      .statusCode(200)
      .body("id.size()", is(2));
    
    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/services?firstResult=2")
      .then()
      .assertThat()
      .statusCode(200)
      .body("id.size()", is(1));
    
    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/services?firstResult=666")
      .then()
      .assertThat()
      .statusCode(200)
      .body("id.size()", is(0));
    
    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/services?firstResult=-1")
      .then()
      .assertThat()
      .statusCode(400);
    
    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/services?maxResults=2")
      .then()
      .assertThat()
      .statusCode(200)
      .body("id.size()", is(2));
    
    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/services?maxResults=0")
      .then()
      .assertThat()
      .statusCode(200)
      .body("id.size()", is(0));
    
    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/services?maxResults=-1")
      .then()
      .assertThat()
      .statusCode(400);
    
    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/services?maxResults=666")
      .then()
      .assertThat()
      .statusCode(200)
      .body("id.size()", is(3));
    

    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/services?firstResult=0&maxResults=2")
      .then()
      .assertThat()
      .statusCode(200)
      .body("id.size()", is(2));
    
    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/services?firstResult=1&maxResults=2")
      .then()
      .assertThat()
      .statusCode(200)
      .body("id.size()", is(2));
    
    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/services?firstResult=1&maxResults=1")
      .then()
      .assertThat()
      .statusCode(200)
      .body("id.size()", is(1));
    
    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/services?firstResult=-1&maxResults=1")
      .then()
      .assertThat()
      .statusCode(400);
    
    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/services?firstResult=2&maxResults=-1")
      .then()
      .assertThat()
      .statusCode(400);
    
    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/services?firstResult=1&maxResults=0")
      .then()
      .assertThat()
      .statusCode(200)
      .body("id.size()", is(0));
    
    given() 
      .baseUri(getApiBasePath())
      .contentType(ContentType.JSON)
      .get("/services?firstResult=21&maxResults=20")
      .then()
      .assertThat()
      .statusCode(200)
      .body("id.size()", is(0));
  }

  
}
