package fi.otavanopisto.restfulptv.server.organizationservices;

import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;

import fi.otavanopisto.restfulptv.server.cache.AbstractEntityCache;
import fi.otavanopisto.restfulptv.server.rest.model.OrganizationService;

@ApplicationScoped
@Singleton
public class OrganizationServiceCache extends AbstractEntityCache <OrganizationService> {
 
  private static final long serialVersionUID = -3149184781683240865L;
  
  @Override
  public String getCacheName() {
    return "organizationservices";
  }

}
