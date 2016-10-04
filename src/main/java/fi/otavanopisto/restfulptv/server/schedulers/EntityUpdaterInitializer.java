package fi.otavanopisto.restfulptv.server.schedulers;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.bertoncelj.wildflysingletonservice.Start;
import com.bertoncelj.wildflysingletonservice.Stop;

@ApplicationScoped
@SuppressWarnings ("squid:S3306")
public class EntityUpdaterInitializer {
  
  private static final int TIMER_INITIAL = 1000;
  
  @Inject
  private Logger logger;
  
  @Inject
  private OrganizationEntityUpdater entityUpdater;

  @Start
  public void start() {
    logger.info("Starting entity updater");
    entityUpdater.startTimer(TIMER_INITIAL);
    logger.info("Started entity updater");
  }
  
  @Stop
  public void stop() {
    logger.info("Stopping entity updater");
    entityUpdater.stopTimer();
    logger.info("Stopped entity updater");
  }
   
}
