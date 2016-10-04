package fi.otavanopisto.restfulptv.server.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.infinispan.Cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fi.otavanopisto.restfulptv.server.http.GenericHttpClient.ResultType;

/**
 * Abstract base cache for all PTV entity caches
 * 
 * @author Antti Leppä
 * @author Heikki Kurhinen
 */
@SuppressWarnings ("squid:S3306")
public abstract class AbstractEntityCache <T> {
  
  @Inject
  private Logger logger;
  
  public abstract Cache<String, String> getCache();
  
  /**
   * Returns cached entity by id
   * 
   * @param id entity id
   * @param type result type
   * @return cached api reposponse or null if non found
   */
  public T get(String id, ResultType<T> type) {
    Cache<String, String> cache = getCache();
    if (cache.containsKey(id)) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        return objectMapper.readValue(cache.get(id), type.getTypeReference());
      } catch (IOException e) {
        cache.remove(id);
        logger.log(Level.SEVERE, "Invalid serizalized object found from the cache. Dropped object", e);
      }
    }
    
    return null;
  }
  
  /**
   * Caches an entity
   * 
   * @param id entity id
   * @param response
   */
  public void put(String id, T response) {
    Cache<String, String> cache = getCache();
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      cache.put(id, objectMapper.writeValueAsString(response));
    } catch (JsonProcessingException e) {
      logger.log(Level.SEVERE, "Failed to serialize response into cache", e);
    }
  }
  
  /**
   * Removes elements from the cache
   * 
   * @param id entity id
   */
  public void clear(String id) {
    Cache<String, String> cache = getCache();
    cache.remove(id);
  }
  
  /**
   * Returns all cached ids
   * 
   * @return  all cached ids
   */
  public List<String> getIds() {
    Cache<String, String> cache = getCache();
    return new ArrayList<>(cache.keySet());
  }
  
}
