package fi.otavanopisto.restfulptv.server.organizations;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;

import fi.otavanopisto.restfulptv.server.cache.AbstractEntityCache;
import fi.otavanopisto.restfulptv.server.rest.model.Organization;

@RequestScoped
public class OrganizationCache extends AbstractEntityCache <Organization> {
 
  private static final long serialVersionUID = -3149184781683240865L;
 
  @Resource (lookup = "java:jboss/infinispan/container/kunta-api")
  private transient EmbeddedCacheManager cacheManager;

  @Override
  public Cache<String, String> getCache() {
    return cacheManager.getCache("organizations");
  }

}
