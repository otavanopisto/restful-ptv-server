package fi.otavanopisto.restfulptv.server;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;

import fi.otavanopisto.restfulptv.server.rest.OrganizationsApi;
import fi.otavanopisto.restfulptv.server.rest.model.Organization;
import fi.otavanopisto.restfulptv.server.rest.model.OrganizationService;

/**
 * Organization REST Service implementation
 * 
 * @author Antti Leppä
 * @author Heikki Kurhinen
 */
@RequestScoped
@Stateful
public class OrganizationsApiImpl extends OrganizationsApi {
  
  private static final String NOT_IMPLEMENTED = "Not implemented";

  @Override
  public Response createOrganization(Organization body) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response createOrganizationService(String organizationId, OrganizationService body) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response findOrganization(String organizationId) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response findOrganizationService(String organizationId, String organizationServiceId) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response listOrganizationOrganizationServices(String organizationId, Long firstResult, Long maxResults) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response listOrganizations(Long firstResult, Long maxResults) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response updateOrganization(String organizationId, Organization body) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response updateOrganizationService(String organizationId, String organizationServiceId, OrganizationService body) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }
}

