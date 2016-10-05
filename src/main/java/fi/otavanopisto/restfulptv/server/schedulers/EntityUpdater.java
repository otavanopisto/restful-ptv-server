package fi.otavanopisto.restfulptv.server.schedulers;

public abstract class EntityUpdater {
  
  public abstract void startTimer();
  
  public abstract void stopTimer();
  
  public abstract String getName();
  
}
