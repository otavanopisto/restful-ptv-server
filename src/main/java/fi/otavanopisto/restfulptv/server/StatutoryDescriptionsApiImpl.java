package fi.otavanopisto.restfulptv.server;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;

import fi.otavanopisto.restfulptv.server.rest.StatutoryDescriptionsApi;

/**
 * Statutory descriptions REST Service implementation
 * 
 * @author Antti Leppä
 * @author Heikki Kurhinen
 */
@RequestScoped
@Stateful
public class StatutoryDescriptionsApiImpl extends StatutoryDescriptionsApi {
  
  private static final String NOT_IMPLEMENTED = "Not implemented";

  @Override
  public Response findStatutoryDescription(String statutoryDescriptionId) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response listStatutoryDescriptions(Long firstResult, Long maxResults) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  

}

