package fi.otavanopisto.restfulptv.server.services;

import java.util.ArrayList;
import java.util.List;
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

import fi.otavanopisto.ptv.client.ApiResponse;
import fi.otavanopisto.ptv.client.model.IVmOpenApiService;
import fi.otavanopisto.restfulptv.server.ptv.PtvApi;
import fi.otavanopisto.restfulptv.server.schedulers.EntityUpdater;

@ApplicationScoped
@Singleton
@SuppressWarnings ("squid:S3306")
public class ServiceEntityUpdater extends EntityUpdater {
  
  private static final int TIMER_INTERVAL = 1000;

  @Inject
  private Logger logger;
  
  @Inject
  private PtvApi ptvApi;
  
  @Inject
  private ServiceCache serviceCache;
  
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
  
  public void onServiceIdUpdateRequest(@Observes ServiceIdUpdateRequest event) {
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
        
        ApiResponse<IVmOpenApiService> response = ptvApi.getServiceApi().apiServiceByIdGet(entityId);
        if (response.isOk()) {
          serviceCache.put(entityId, response.getResponse());
        } else {
          logger.warning(String.format("Service %s caching failed on [%d] %s", entityId, response.getStatus(), response.getMessage()));
        }
        
      }
      
      startTimer(TIMER_INTERVAL);
    }
  }
   
}
