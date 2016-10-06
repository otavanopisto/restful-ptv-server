package fi.otavanopisto.restfulptv.server.organizations;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

import fi.otavanopisto.restfulptv.server.cache.AbstractEntityCache;
import fi.otavanopisto.restfulptv.server.rest.model.Organization;

@ApplicationScoped
@Singleton
public class OrganizationCache extends AbstractEntityCache <Organization> {
 
  private static final long serialVersionUID = -3149184781683240865L;
 
  @Resource (lookup = "java:jboss/infinispan/container/kunta-api")
  private transient CacheContainer cacheContainer;

  @Override
  public Cache<String, String> getCache() {
    return cacheContainer.getCache("organizations");
  }

}
