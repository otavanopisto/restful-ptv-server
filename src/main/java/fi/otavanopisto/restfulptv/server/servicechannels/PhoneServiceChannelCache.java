package fi.otavanopisto.restfulptv.server.servicechannels;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;

import org.infinispan.manager.CacheContainer;

import fi.otavanopisto.restfulptv.server.cache.AbstractEntityCache;
import fi.otavanopisto.restfulptv.server.rest.model.PhoneChannel;

@ApplicationScoped
@Singleton
public class PhoneServiceChannelCache extends AbstractEntityCache <PhoneChannel> {
 
  private static final long serialVersionUID = 8598552721802251272L;
  
  @Resource (lookup = "java:jboss/infinispan/container/kunta-api")
  private transient CacheContainer cacheContainer;

  @Override
  public String getCacheName() {
    return "phonechannels";
  }

}
