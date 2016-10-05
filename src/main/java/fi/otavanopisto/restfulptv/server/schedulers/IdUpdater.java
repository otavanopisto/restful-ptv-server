package fi.otavanopisto.restfulptv.server.schedulers;

public abstract class IdUpdater {
  
  public abstract void startTimer();
  
  public abstract void stopTimer();
  
  public abstract String getName();
  
}
