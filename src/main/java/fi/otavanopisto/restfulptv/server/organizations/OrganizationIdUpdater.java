package fi.otavanopisto.restfulptv.server.organizations;

import java.time.OffsetDateTime;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import fi.otavanopisto.ptv.client.ApiResponse;
import fi.otavanopisto.ptv.client.model.VmOpenApiGuidPage;
import fi.otavanopisto.restfulptv.server.ptv.PtvApi;

@Singleton
@SuppressWarnings ("squid:S3306")
public class OrganizationIdUpdater {
  
  private static final int TIMER_INTERVAL = 5000;
  private static final int STANDARD_INTERVAL = 10;

  @Inject
  private Logger logger;
  
  @Inject
  private PtvApi ptvApi;
  
  @Inject
  private Event<OrganizationIdUpdateRequest> updateRequest;
  
  @Resource
  private TimerService timerService;
  
  private boolean stopped;
  private int page;
  private int pageCount;
  private int counter;
  private OffsetDateTime priortyScanTime;
  
  public void startTimer() {
    priortyScanTime = OffsetDateTime.now();
    stopped = false;
    counter = 0;
    startTimer(TIMER_INTERVAL);
  }
  
  private void startTimer(int duration) {
    TimerConfig timerConfig = new TimerConfig();
    timerConfig.setPersistent(false);
    timerService.createSingleActionTimer(duration, timerConfig);
  }

  public void stopTimer() {
    stopped = true;
  }
  
  @Timeout
  public void timeout(Timer timer) {
    if (!stopped) {
      if (counter % STANDARD_INTERVAL == 0) {
        discoverIds();
      }
      
      discoverPriorityIds();
      startTimer(TIMER_INTERVAL);
      counter++;
    }
  }

  private void discoverIds() {
    int discoverCount = 0;
    boolean hasMore = false;
    
    if (pageCount > 0) {
      logger.info(String.format("Updating organizations page %d / %d", page + 1, pageCount));
    } else {
      logger.info(String.format("Updating organizations page %d", page + 1));
    }
    
    ApiResponse<VmOpenApiGuidPage> response = ptvApi.getOrganizationApi().apiOrganizationGet(null, page);
    if (response.isOk()) {
      VmOpenApiGuidPage pageData = response.getResponse();
      
      for (String guid : pageData.getGuidList()) {
        updateRequest.fire(new OrganizationIdUpdateRequest(guid, false));
        discoverCount++;
      }
      
      pageCount = pageData.getPageCount();
      hasMore = pageCount > page + 1;
 
      if (discoverCount > 0) {
        logger.info(String.format("Discovered %d organization ids", discoverCount));
      }
    } else {
      logger.severe(String.format("Failed to update organization ids from PTV (%d: %s)", response.getStatus(), response.getMessage()));
    }
    
    if (hasMore) {
      page++;
    } else {
      page = 0;
    }
  }

  private void discoverPriorityIds() {
    int discoverCount = 0;
    logger.info("Updating priority organizations");
    
    ApiResponse<VmOpenApiGuidPage> response = ptvApi.getOrganizationApi().apiOrganizationGet(priortyScanTime, 0);
    if (response.isOk()) {
      VmOpenApiGuidPage pageData = response.getResponse();
      
      for (String guid : pageData.getGuidList()) {
        updateRequest.fire(new OrganizationIdUpdateRequest(guid, true));
        discoverCount++;
      }
      
      pageCount = pageData.getPageCount();
      
      if (discoverCount > 0) {
        logger.info(String.format("Discovered %d priority organizations", discoverCount));
      }
      
      priortyScanTime = OffsetDateTime.now();
    } else {
      logger.severe(String.format("Failed to update priority organization ids from PTV (%d: %s)", response.getStatus(), response.getMessage()));
    }
  }
   
}
