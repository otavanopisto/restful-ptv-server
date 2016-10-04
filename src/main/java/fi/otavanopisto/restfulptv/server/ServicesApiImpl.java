package fi.otavanopisto.restfulptv.server;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;

import fi.otavanopisto.restfulptv.server.rest.ServicesApi;
import fi.otavanopisto.restfulptv.server.rest.model.ElectronicChannel;
import fi.otavanopisto.restfulptv.server.rest.model.PhoneChannel;
import fi.otavanopisto.restfulptv.server.rest.model.PrintableFormChannel;
import fi.otavanopisto.restfulptv.server.rest.model.Service;
import fi.otavanopisto.restfulptv.server.rest.model.ServiceLocationChannel;
import fi.otavanopisto.restfulptv.server.rest.model.WebPageChannel;

/**
 * Services REST Service implementation
 * 
 * @author Antti Leppä
 * @author Heikki Kurhinen
 */
@RequestScoped
@Stateful
public class ServicesApiImpl extends ServicesApi {
  
  private static final String NOT_IMPLEMENTED = "Not implemented";

  @Override
  public Response createService(Service body) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response createServiceElectronicChannel(String serviceId, ElectronicChannel body) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response createServicePhoneChannel(String serviceId, PhoneChannel body) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response createServicePrintableFormChannel(String serviceId, PrintableFormChannel body) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response createServiceServiceLocationChannel(String serviceId, ServiceLocationChannel body) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response createServiceWebPageChannel(String serviceId, WebPageChannel body) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response findService(String serviceId) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response findServiceElectronicChannel(String serviceId, String electronicChannelId) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response findServicePhoneChannel(String serviceId, String phoneChannelId) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response findServicePrintableFormChannel(String serviceId, String printableFormChannelId) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response findServiceServiceLocationChannel(String serviceId, String serviceLocationChannelId) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response findServiceWebPageChannel(String serviceId, String webPageChannelId) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response listServiceElectronicChannels(String serviceId, Long firstResult, Long maxResults) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response listServicePhoneChannels(String serviceId, Long firstResult, Long maxResults) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response listServicePrintableFormChannels(String serviceId, Long firstResult, Long maxResults) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response listServiceServiceLocationChannels(String serviceId, Long firstResult, Long maxResults) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response listServiceWebPageChannels(String serviceId, Long firstResult, Long maxResults) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response listServices(Long firstResult, Long maxResults) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response updatePhoneChannel(String serviceId, String phoneChannelId, PhoneChannel body) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response updatePrintableFormChannel(String serviceId, String printableFormChannelId,
      PrintableFormChannel body) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response updateService(String serviceId, Service body) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response updateServiceElectronicChannel(String serviceId, String electronicChannelId, ElectronicChannel body) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response updateServiceLocationChannel(String serviceId, String serviceLocationChannelId,
      ServiceLocationChannel body) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response updateWebPageChannel(String serviceId, String webPageChannelId, WebPageChannel body) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

}

