package fi.otavanopisto.restfulptv.server.servicechannels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fi.otavanopisto.ptv.client.ApiResponse;
import fi.otavanopisto.ptv.client.ResultType;
import fi.otavanopisto.ptv.client.model.VmOpenApiElectronicChannel;
import fi.otavanopisto.ptv.client.model.VmOpenApiPhoneChannel;
import fi.otavanopisto.ptv.client.model.VmOpenApiPrintableFormChannel;
import fi.otavanopisto.ptv.client.model.VmOpenApiServiceLocationChannel;
import fi.otavanopisto.ptv.client.model.VmOpenApiWebPageChannel;
import fi.otavanopisto.restfulptv.server.PtvTranslator;
import fi.otavanopisto.restfulptv.server.ptv.PtvClient;
import fi.otavanopisto.restfulptv.server.rest.model.ElectronicChannel;
import fi.otavanopisto.restfulptv.server.rest.model.PhoneChannel;
import fi.otavanopisto.restfulptv.server.rest.model.PrintableFormChannel;
import fi.otavanopisto.restfulptv.server.rest.model.ServiceLocationChannel;
import fi.otavanopisto.restfulptv.server.rest.model.WebPageChannel;
import fi.otavanopisto.restfulptv.server.schedulers.EntityUpdater;

@ApplicationScoped
@Singleton
@SuppressWarnings ("squid:S3306")
public class ServiceChannelEntityUpdater extends EntityUpdater {
  
  private static final int TIMER_INTERVAL = 1000;

  @Inject
  private Logger logger;
  
  @Inject
  private PtvClient ptvClient;
  
  @Inject
  private ElectronicServiceChannelCache electronicServiceChannelCache;
  
  @Inject
  private LocationServiceChannelCache locationServiceChannelCache;
  
  @Inject
  private PrintableFormServiceChannelCache printableFormServiceChannelCache;
  
  @Inject
  private PhoneServiceChannelCache phoneServiceChannelCache;
  
  @Inject
  private WebPageChannelCache webPageChannelCache;
  
  @Inject
  private PtvTranslator ptvTranslator;
  
  @Resource
  private TimerService timerService;
  
  private boolean stopped;
  private List<String> queue;
  
  @PostConstruct
  public void init() {
    queue = new ArrayList<>();
  }
  
  @Override
  public String getName() {
    return "services";
  }

  @Override
  public void startTimer() {
    startTimer(TIMER_INTERVAL);
  }

  @Override
  public void stopTimer() {
    stopped = true;
  }
  
  private void startTimer(int duration) {
    stopped = false;
    TimerConfig timerConfig = new TimerConfig();
    timerConfig.setPersistent(false);
    timerService.createSingleActionTimer(duration, timerConfig);
  }
  
  public void onServiceIdUpdateRequest(@Observes ServiceChannelIdUpdateRequest event) {
    if (!stopped) {
      if (event.isPriority()) {
        queue.remove(event.getId());
        queue.add(0, event.getId());
      } else {
        if (!queue.contains(event.getId())) {
          queue.add(event.getId());
        }
      }
    }
  }
  
  @Timeout
  public void timeout(Timer timer) {
    if (!stopped) {
      if (!queue.isEmpty()) {
        String entityId = queue.iterator().next();
        if (!queue.remove(entityId)) {
          logger.warning(String.format("Could not remove %s from queue", entityId));
        }
        
        String path = String.format("/api/ServiceChannel/%s", entityId);
        ApiResponse<Map<String, Object>> response = ptvClient.doGETRequest(path, new ResultType<Map<String, Object>>() {}, null, null);
        if (response.isOk()) {
          handleResponse(entityId, response);
        } else {
          logger.warning(String.format("Service channel %s caching failed on [%d] %s. Request path was %s", entityId, response.getStatus(), response.getMessage(), path));
        }
      }
      
      startTimer(TIMER_INTERVAL);
    }
  }

  private void handleResponse(String entityId, ApiResponse<Map<String, Object>> response) {
    Object type = response.getResponse().get("serviceChannelType");
    if (!(type instanceof String)) {
      logger.warning(String.format("ServiceChannel %s does not have a type", entityId));
    } else {
      ObjectMapper objectMapper = new ObjectMapper();
      byte[] requestData;
      try {
        requestData = objectMapper.writeValueAsBytes(response.getResponse());
      } catch (JsonProcessingException e) {
        logger.log(Level.SEVERE, String.format("Failed to serialize electronic channel %s", entityId), e);
        return;
      }

      logger.info(String.format("Updating service channel type %s", (String) type));
      
      try {
        cacheServiceChannel(type, objectMapper, requestData);
      } catch (IOException e) {
        logger.log(Level.SEVERE, String.format("Failed to convert channel %s back to PTV format", entityId), e);
        return;
      }
    }
  }

  private void cacheServiceChannel(Object type, ObjectMapper objectMapper, byte[] requestData) throws IOException {
    switch ((String) type) {
      case "EChannel":
        cacheElectronicChannel(objectMapper.readValue(requestData, VmOpenApiElectronicChannel.class));
      break;
      case "ServiceLocation":
        cacheServiceLocationChannel(objectMapper.readValue(requestData, VmOpenApiServiceLocationChannel.class));
      break;
      case "PrintableForm":
        cachePrintableFormChannel(objectMapper.readValue(requestData, VmOpenApiPrintableFormChannel.class));
      break;
      case "Phone":
        cachePhoneChannel(objectMapper.readValue(requestData, VmOpenApiPhoneChannel.class));
      break;
      case "WebPage":
        cacheWebPageChannel(objectMapper.readValue(requestData, VmOpenApiWebPageChannel.class));
      break;
      default:
      break;
    }
  }

  private void cacheElectronicChannel(VmOpenApiElectronicChannel ptvElectronicChannel) {
    logger.info(String.format("Updating electronic service channel %s", ptvElectronicChannel.getId()));
    
    ElectronicChannel electronicChannel = ptvTranslator.translateElectronicChannel(ptvElectronicChannel);
    if (electronicChannel != null) {
      electronicServiceChannelCache.put(electronicChannel.getId(), electronicChannel);
    } else {
      logger.warning(String.format("Failed to translate ptvElectronicChannel %s", ptvElectronicChannel.getId()));
    }
  }

  private void cacheServiceLocationChannel(VmOpenApiServiceLocationChannel ptvServiceLocationChannel) {
    logger.info(String.format("Updating serviceLocation service channel %s", ptvServiceLocationChannel.getId()));
    
    ServiceLocationChannel serviceLocationChannel = ptvTranslator.translateServiceLocationChannel(ptvServiceLocationChannel);
    if (serviceLocationChannel != null) {
      locationServiceChannelCache.put(serviceLocationChannel.getId(), serviceLocationChannel);
    } else {
      logger.warning(String.format("Failed to translate ptvServiceLocationChannel %s", ptvServiceLocationChannel.getId()));
    }
  }

  private void cachePrintableFormChannel(VmOpenApiPrintableFormChannel ptvPrintableFormChannel) {
    logger.info(String.format("Updating printableForm service channel %s", ptvPrintableFormChannel.getId()));
    
    PrintableFormChannel printableFormChannel = ptvTranslator.translatePrintableFormChannel(ptvPrintableFormChannel);
    if (printableFormChannel != null) {
      printableFormServiceChannelCache.put(printableFormChannel.getId(), printableFormChannel);
    } else {
      logger.warning(String.format("Failed to translate ptvPrintableFormChannel %s", ptvPrintableFormChannel.getId()));
    }
  }

  private void cachePhoneChannel(VmOpenApiPhoneChannel ptvPhoneChannel) {
    logger.info(String.format("Updating phone service channel %s", ptvPhoneChannel.getId()));
    
    PhoneChannel phoneChannel = ptvTranslator.translatePhoneChannel(ptvPhoneChannel);
    if (phoneChannel != null) {
      phoneServiceChannelCache.put(phoneChannel.getId(), phoneChannel);
    } else {
      logger.warning(String.format("Failed to translate ptvPhoneChannel %s", ptvPhoneChannel.getId()));
    }
  }

  private void cacheWebPageChannel(VmOpenApiWebPageChannel ptvWebPageChannel) {
    logger.info(String.format("Updating webPage service channel %s", ptvWebPageChannel.getId()));
    
    WebPageChannel webPageChannel = ptvTranslator.translateWebPageChannel(ptvWebPageChannel);
    if (webPageChannel != null) {
      webPageChannelCache.put(webPageChannel.getId(), webPageChannel);
    } else {
      logger.warning(String.format("Failed to translate ptvWebPageChannel %s", ptvWebPageChannel.getId()));
    }
  }
  
}
