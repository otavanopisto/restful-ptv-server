package fi.otavanopisto.restfulptv.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import fi.otavanopisto.ptv.client.model.VmOpenApiOrganization;
import fi.otavanopisto.restfulptv.server.organizations.OrganizationCache;
import fi.otavanopisto.restfulptv.server.rest.model.Organization;

@RequestScoped
@SuppressWarnings ("squid:S3306")
public class OrganizationController implements Serializable {
  
  private static final long serialVersionUID = -1069291263681772143L;
  
  @Inject
  private Logger logger;

  @Inject
  private OrganizationCache organizationCache;

  @Inject
  private PtvTranslator ptvTranslator;

  public Organization findOrganizationById(String id) {
    VmOpenApiOrganization organization = organizationCache.get(id);
    if (organization == null) {
      return null;
    }
    
    return ptvTranslator.translateOrganization(organization); 
  }
  
  public List<Organization> listOrganizations(Long firstResult, Long maxResults) {
    List<String> ids = organizationCache.getIds();
    
    int idCount = ids.size();
    int firstIndex = firstResult == null ? 0 : Math.min(firstResult.intValue(), idCount - 1);
    int toIndex = maxResults == null ? idCount - 1 : Math.min(firstIndex + maxResults.intValue(), idCount - 1);
    
    List<Organization> result = new ArrayList<>(toIndex - firstIndex);
    for (String id : ids.subList(firstIndex, toIndex)) {
      Organization organization = findOrganizationById(id);
      if (organization != null) {
        result.add(organization);
      } else {
        logger.severe(String.format("Could not find organization by id %s", id));
      }
    }
    
    return result;
  }
  
}
