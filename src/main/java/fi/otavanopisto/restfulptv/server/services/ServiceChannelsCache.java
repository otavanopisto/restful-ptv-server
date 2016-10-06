package fi.otavanopisto.restfulptv.server.services;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;

import fi.otavanopisto.restfulptv.server.cache.AbstractEntityCache;

@RequestScoped
public class ServiceChannelsCache extends AbstractEntityCache <ServiceChannelIds> {
 
  private static final long serialVersionUID = 8598552721802251272L;
  
  @Resource (lookup = "java:jboss/infinispan/container/kunta-api")
  private transient EmbeddedCacheManager cacheManager;

  @Override
  public Cache<String, String> getCache() {
    return cacheManager.getCache("servicechannels");
  }

}
