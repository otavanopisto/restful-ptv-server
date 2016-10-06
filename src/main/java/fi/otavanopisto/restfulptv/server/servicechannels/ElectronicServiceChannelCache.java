package fi.otavanopisto.restfulptv.server.servicechannels;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

import fi.otavanopisto.restfulptv.server.cache.AbstractEntityCache;
import fi.otavanopisto.restfulptv.server.rest.model.ElectronicChannel;

@ApplicationScoped
@Singleton
public class ElectronicServiceChannelCache extends AbstractEntityCache <ElectronicChannel> {
 
  private static final long serialVersionUID = 8598552721802251272L;
  
  @Resource (lookup = "java:jboss/infinispan/container/kunta-api")
  private transient CacheContainer cacheContainer;

  @Override
  public Cache<String, String> getCache() {
    return cacheContainer.getCache("electronicchannels");
  }

}
