package fi.otavanopisto.restfulptv.server.ptv;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.apache.http.client.utils.URIBuilder;

import fi.otavanopisto.ptv.client.ApiResponse;
import fi.otavanopisto.ptv.client.ResultType;
import fi.otavanopisto.restfulptv.server.http.GenericHttpClient;
import fi.otavanopisto.restfulptv.server.http.GenericHttpClient.Response;

/**
 * API Client for palvelutietovaranto
 * 
 * @author Antti Leppä
 */
@Dependent
public class PtvClient extends fi.otavanopisto.ptv.client.ApiClient {

  // TODO: Parameterize this
  private static final String BASE_PATH = "https://api.palvelutietovaranto.trn.suomi.fi";
  private static final String INVALID_URI_SYNTAX = "Invalid uri syntax";
  
  @Inject
  private Logger logger;

  @Inject
  private GenericHttpClient httpClient;
  
  private PtvClient() {
  }
  
  @Override
  public <T> ApiResponse<T> doGETRequest(String path, ResultType<T> resultType, Map<String, Object> queryParams, Map<String, Object> postParams) {
    URIBuilder uriBuilder;
    try {
      uriBuilder = new URIBuilder(String.format("%s%s", BASE_PATH, path));
    } catch (URISyntaxException e) {
      logger.log(Level.SEVERE, INVALID_URI_SYNTAX, e);
      return new ApiResponse<>(500, INVALID_URI_SYNTAX, null);
    }
    
    if (queryParams != null) {
      for (Entry<String, Object> entry : queryParams.entrySet()) {
        addQueryParam(uriBuilder, entry);
      }
    }
    
    URI uri;
    try {
      uri = uriBuilder.build();
    } catch (URISyntaxException e) {
      logger.log(Level.SEVERE, INVALID_URI_SYNTAX, e);
      return new ApiResponse<>(500, INVALID_URI_SYNTAX, null);
    }
    
    Response<T> response = httpClient.doGETRequest(uri, new GenericHttpClient.ResultTypeWrapper<>(resultType.getType()));
    
    return new ApiResponse<>(response.getStatus(), response.getMessage(), response.getResponseEntity());
  }
  
  @Override
  public <T> ApiResponse<T> doPOSTRequest(String path, ResultType<T> resultType, Map<String, Object> queryParams, Map<String, Object> postParams) {
    return null;
  }

  @Override
  public <T> ApiResponse<T> doPUTRequest(String path, ResultType<T> resultType, Map<String, Object> queryParams, Map<String, Object> postParams) {
    return null;
  }

  @Override
  public <T> ApiResponse<T> doDELETERequest(String path, ResultType<T> resultType, Map<String, Object> queryParams, Map<String, Object> postParams) {
    return null;
  }
  
  private void addQueryParam(URIBuilder uriBuilder, Entry<String, Object> entry) {
    if (entry.getValue() instanceof List) {
      for (Object value : (List<?>) entry.getValue()) {
        uriBuilder.addParameter(entry.getKey(), parameterToString(value));
      }
    } else {
      uriBuilder.addParameter(entry.getKey(), parameterToString(entry.getValue()));
    }
  }
  
  private String parameterToString(Object value) {
    if (value instanceof OffsetDateTime) {
      OffsetDateTime offsetDateTime = (OffsetDateTime) value;
      ZonedDateTime utcWithoutSeconds = offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS);
      return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(utcWithoutSeconds);
    } 
    
    return String.valueOf(value);
  }
  
}
