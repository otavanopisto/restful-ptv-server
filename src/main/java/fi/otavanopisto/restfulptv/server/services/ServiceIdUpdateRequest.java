package fi.otavanopisto.restfulptv.server.services;

public class ServiceIdUpdateRequest {

  private String id;  
  private boolean priority;
  
  public ServiceIdUpdateRequest(String id, boolean priority) {
    this.id = id;
    this.priority = priority;
  }
  
  public String getId() {
    return id;
  }
  
  public boolean isPriority() {
    return priority;
  }
  
}
