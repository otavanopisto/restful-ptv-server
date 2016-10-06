package fi.otavanopisto.restfulptv.server.statutorydescriptions;

public class StatutoryDescriptionIdUpdateRequest {

  private String id;  
  private boolean priority;
  
  public StatutoryDescriptionIdUpdateRequest(String id, boolean priority) {
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
