package fi.otavanopisto.restfulptv.server.services;

import javax.annotation.Resource;
import javax.enterprise.context.Dependent;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;

import fi.otavanopisto.ptv.client.model.IVmOpenApiService;
import fi.otavanopisto.restfulptv.server.cache.AbstractEntityCache;

@Dependent
public class ServiceCache extends AbstractEntityCache <IVmOpenApiService> {
 
  @Resource (lookup = "java:jboss/infinispan/container/kunta-api")
  private EmbeddedCacheManager cacheManager;

  @Override
  public Cache<String, String> getCache() {
    return cacheManager.getCache("services");
  }

}
