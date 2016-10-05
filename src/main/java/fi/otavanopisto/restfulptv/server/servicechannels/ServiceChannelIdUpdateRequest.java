package fi.otavanopisto.restfulptv.server.servicechannels;

public class ServiceChannelIdUpdateRequest {

  private String id;  
  private boolean priority;
  
  public ServiceChannelIdUpdateRequest(String id, boolean priority) {
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
