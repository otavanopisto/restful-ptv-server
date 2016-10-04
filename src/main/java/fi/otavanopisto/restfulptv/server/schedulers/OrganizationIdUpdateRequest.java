package fi.otavanopisto.restfulptv.server.schedulers;

public class OrganizationIdUpdateRequest {

  private String id;  
  private boolean priority;
  
  public OrganizationIdUpdateRequest(String id, boolean priority) {
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
