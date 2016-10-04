package fi.otavanopisto.restfulptv.server.schedulers;

import javax.annotation.Resource;
import javax.enterprise.context.Dependent;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;

import fi.otavanopisto.ptv.client.model.VmOpenApiOrganization;
import fi.otavanopisto.restfulptv.server.cache.AbstractEntityCache;

@Dependent
public class OrganizationCache extends AbstractEntityCache <VmOpenApiOrganization> {
 
  @Resource (lookup = "java:jboss/infinispan/container/kunta-api")
  private EmbeddedCacheManager cacheManager;

  @Override
  public Cache<String, String> getCache() {
    return cacheManager.getCache("organizations");
  }

}
