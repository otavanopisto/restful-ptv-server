package fi.otavanopisto.restfulptv.server.services;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

import fi.otavanopisto.restfulptv.server.cache.AbstractEntityCache;
import fi.otavanopisto.restfulptv.server.rest.model.Service;

@ApplicationScoped
@Singleton
public class ServiceCache extends AbstractEntityCache <Service> {
 
  private static final long serialVersionUID = 8598552721802251272L;
  
  @Resource (lookup = "java:jboss/infinispan/container/kunta-api")
  private transient CacheContainer cacheContainer;

  @Override
  public Cache<String, String> getCache() {
    return cacheContainer.getCache("services");
  }

}
