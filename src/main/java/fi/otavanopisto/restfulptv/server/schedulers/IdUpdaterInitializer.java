package fi.otavanopisto.restfulptv.server.schedulers;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.bertoncelj.wildflysingletonservice.Start;
import com.bertoncelj.wildflysingletonservice.Stop;

import fi.otavanopisto.restfulptv.server.organizations.OrganizationIdUpdater;

@ApplicationScoped
@SuppressWarnings ("squid:S3306")
public class IdUpdaterInitializer {
  
  @Inject
  private Logger logger;
  
  @Inject
  private OrganizationIdUpdater idUpdater;

  @Start
  public void start() {
    logger.info("Starting id updater");
    idUpdater.startTimer();
    logger.info("Started id updater");
  }
  
  @Stop
  public void stop() {
    logger.info("Stopping id updater");
    idUpdater.stopTimer();
    logger.info("Stopped id updater");
  }
   
}
