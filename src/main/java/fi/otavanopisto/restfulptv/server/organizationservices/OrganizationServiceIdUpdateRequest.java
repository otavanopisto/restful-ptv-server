package fi.otavanopisto.restfulptv.server.organizationservices;

public class OrganizationServiceIdUpdateRequest {

  private String id;  
  private boolean priority;
  
  public OrganizationServiceIdUpdateRequest(String id, boolean priority) {
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
