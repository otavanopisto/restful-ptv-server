package fi.otavanopisto.restfulptv.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import fi.otavanopisto.ptv.client.model.IVmOpenApiService;
import fi.otavanopisto.ptv.client.model.VmOpenApiAddressWithType;
import fi.otavanopisto.ptv.client.model.VmOpenApiFintoItem;
import fi.otavanopisto.ptv.client.model.VmOpenApiLanguageItem;
import fi.otavanopisto.ptv.client.model.VmOpenApiLocalizedListItem;
import fi.otavanopisto.ptv.client.model.VmOpenApiOrganization;
import fi.otavanopisto.ptv.client.model.VmOpenApiOrganizationEmail;
import fi.otavanopisto.ptv.client.model.VmOpenApiOrganizationPhone;
import fi.otavanopisto.ptv.client.model.VmOpenApiService;
import fi.otavanopisto.ptv.client.model.VmOpenApiWebPage;
import fi.otavanopisto.restfulptv.server.rest.model.Address;
import fi.otavanopisto.restfulptv.server.rest.model.FintoItem;
import fi.otavanopisto.restfulptv.server.rest.model.LanguageItem;
import fi.otavanopisto.restfulptv.server.rest.model.LocalizedListItem;
import fi.otavanopisto.restfulptv.server.rest.model.Organization;
import fi.otavanopisto.restfulptv.server.rest.model.OrganizationEmail;
import fi.otavanopisto.restfulptv.server.rest.model.OrganizationPhone;
import fi.otavanopisto.restfulptv.server.rest.model.Service;
import fi.otavanopisto.restfulptv.server.rest.model.WebPage;

@RequestScoped
public class PtvTranslator implements Serializable {

  private static final long serialVersionUID = 8345479101834674145L;

  public List<Organization> translateOrganizations(List<VmOpenApiOrganization> ptvOrganizations) {
    if (ptvOrganizations == null || ptvOrganizations.isEmpty()) {
      return Collections.emptyList();
    }
    
    List<Organization> result = new ArrayList<>(ptvOrganizations.size());
    for (VmOpenApiOrganization ptvOrganization : ptvOrganizations) {
      result.add(translateOrganization(ptvOrganization));
    }

    return result;
  }

  public List<WebPage> translateWebPages(List<VmOpenApiWebPage> ptvWebPages) {
    if (ptvWebPages == null || ptvWebPages.isEmpty()) {
      return Collections.emptyList();
    }
    
    List<WebPage> result = new ArrayList<>(ptvWebPages.size());
    for (VmOpenApiWebPage ptvWebPage : ptvWebPages) {
      result.add(translateWebPage(ptvWebPage));
    }

    return result;
  }
  
  public List<OrganizationPhone> translateOrganizationPhoneNumbers(List<VmOpenApiOrganizationPhone> ptvPhoneNumbers) {
    if (ptvPhoneNumbers == null || ptvPhoneNumbers.isEmpty()) {
      return Collections.emptyList();
    }
    
    List<OrganizationPhone> result = new ArrayList<>(ptvPhoneNumbers.size());
    for (VmOpenApiOrganizationPhone ptvPhoneNumber : ptvPhoneNumbers) {
      result.add(translatePhoneNumber(ptvPhoneNumber));
    }

    return result;
  }

  public List<Address> translateAddresses(List<VmOpenApiAddressWithType> ptvAddresses) {
    if (ptvAddresses == null || ptvAddresses.isEmpty()) {
      return Collections.emptyList();
    }
    
    List<Address> result = new ArrayList<>(ptvAddresses.size());
    for (VmOpenApiAddressWithType ptvAddress : ptvAddresses) {
      result.add(translateAddress(ptvAddress));
    }

    return result;
  }

  public List<LocalizedListItem> translateLocalizedListItems(List<VmOpenApiLocalizedListItem> ptvItems) {
    if (ptvItems == null || ptvItems.isEmpty()) {
      return Collections.emptyList();
    }
    
    List<LocalizedListItem> result = new ArrayList<>(ptvItems.size());
    for (VmOpenApiLocalizedListItem ptvItem : ptvItems) {
      result.add(translateLocalizedListItem(ptvItem));
    }

    return result;
  }

  public List<LanguageItem> translateLangaugeItems(List<VmOpenApiLanguageItem> ptvLanguageItems) {
    if (ptvLanguageItems == null || ptvLanguageItems.isEmpty()) {
      return Collections.emptyList();
    }
    
    List<LanguageItem> result = new ArrayList<>(ptvLanguageItems.size());
    for (VmOpenApiLanguageItem ptvLanguageItem : ptvLanguageItems) {
      result.add(translateLangaugeItem(ptvLanguageItem));
    }

    return result;
  }
  
  public List<OrganizationEmail> translateOrganizationEmails(List<VmOpenApiOrganizationEmail> ptvEmailAddresses) {
    if (ptvEmailAddresses == null || ptvEmailAddresses.isEmpty()) {
      return Collections.emptyList();
    }
    
    List<OrganizationEmail> result = new ArrayList<>(ptvEmailAddresses.size());
    for (VmOpenApiOrganizationEmail ptvEmailAddress : ptvEmailAddresses) {
      result.add(translateOrganizationEmail(ptvEmailAddress));
    }

    return result;
  }

  private List<FintoItem> translateFintoItems(List<VmOpenApiFintoItem> ptvFintoItems) {
    if (ptvFintoItems == null || ptvFintoItems.isEmpty()) {
      return Collections.emptyList();
    }
    
    List<FintoItem> result = new ArrayList<>(ptvFintoItems.size());
    for (VmOpenApiFintoItem ptvFintoItem : ptvFintoItems) {
      result.add(translateFintoItem(ptvFintoItem));
    }

    return result;
  }

  public Organization translateOrganization(VmOpenApiOrganization ptvOrganization) {
    if (ptvOrganization == null) {
      return null;
    }
    
    Organization result = new Organization();
    
    result.setId(ptvOrganization.getId());
    result.setParentOrganization(ptvOrganization.getParentOrganization());
    result.setMunicipality(ptvOrganization.getMunicipality());
    result.setType(ptvOrganization.getOrganizationType());
    result.setBusinessCode(ptvOrganization.getBusinessCode());
    result.setBusinessName(ptvOrganization.getBusinessName());
    result.setPublishingStatus(ptvOrganization.getPublishingStatus());
    result.setDisplayNameType(ptvOrganization.getDisplayNameType());
    result.setOid(ptvOrganization.getOid());
    result.setStreetAddressAsPostalAddress(ptvOrganization.getStreetAddressAsPostalAddress());
    result.setDescriptions(translateLocalizedListItems(ptvOrganization.getOrganizationDescriptions()));
    result.setNames(translateLocalizedListItems(ptvOrganization.getOrganizationNames()));
    result.setAddresses(translateAddresses(ptvOrganization.getAddresses()));
    result.setEmailAddresses(translateOrganizationEmails(ptvOrganization.getEmailAddresses()));
    result.setPhoneNumbers(translateOrganizationPhoneNumbers(ptvOrganization.getPhoneNumbers()));
    result.setWebPages(translateWebPages(ptvOrganization.getWebPages()));

    return result;
  }

  public LocalizedListItem translateLocalizedListItem(VmOpenApiLocalizedListItem ptvItem) {
    if (ptvItem == null) {
      return null;
    }
    
    LocalizedListItem result = new LocalizedListItem();
    result.setLanguage(ptvItem.getLanguage());
    result.setType(ptvItem.getType());
    result.setValue(ptvItem.getValue());
    
    return result;
  }

  public Address translateAddress(VmOpenApiAddressWithType ptvAddress) {
    if (ptvAddress == null) {
      return null;
    }
    
    Address result = new Address();
    result.setType(ptvAddress.getType());
    result.setPostalCode(ptvAddress.getPostalCode());
    result.setPostOffice(ptvAddress.getPostOffice());
    result.setPostOfficeBox(ptvAddress.getPostOfficeBox());
    result.setMunicipality(ptvAddress.getMunicipality());
    result.setCountry(ptvAddress.getCountry());
    result.setQualifier(ptvAddress.getQualifier());
    result.setStreetAddress(translateLangaugeItems(ptvAddress.getStreetAddress()));
    result.setAdditionalInformations(translateLangaugeItems(ptvAddress.getAdditionalInformations()));
    
    return result;
  }

  public LanguageItem translateLangaugeItem(VmOpenApiLanguageItem ptvLanguageItem) {
    if (ptvLanguageItem == null) {
      return null;
    }
    
    LanguageItem languageItem = new LanguageItem();
    languageItem.setLanguage(ptvLanguageItem.getLanguage());
    languageItem.setValue(ptvLanguageItem.getValue());
    
    return languageItem;
  }

  public OrganizationEmail translateOrganizationEmail(VmOpenApiOrganizationEmail ptvEmailAddress) {
    if (ptvEmailAddress == null) {
      return null;
    }
    
    OrganizationEmail result = new OrganizationEmail();
    result.setDescriptions(translateLangaugeItems(ptvEmailAddress.getDescriptions()));
    result.setEmail(ptvEmailAddress.getEmail());
    
    return result;
  }

  public OrganizationPhone translatePhoneNumber(VmOpenApiOrganizationPhone ptvPhoneNumber) {
    if (ptvPhoneNumber == null) {
      return null;
    }
    
    OrganizationPhone result = new OrganizationPhone();
    result.setChargeType(ptvPhoneNumber.getChargeType());
    result.setDescriptions(translateLocalizedListItems(ptvPhoneNumber.getDescriptions()));
    result.setNumber(ptvPhoneNumber.getNumber());
    result.setPrefixNumber(ptvPhoneNumber.getPrefixNumber());
    result.setType(ptvPhoneNumber.getType());
    
    return result;
  }

  public WebPage translateWebPage(VmOpenApiWebPage ptvWebPage) {
    if (ptvWebPage == null) {
      return null;
    }
    
    WebPage result = new WebPage();
    result.setDescription(ptvWebPage.getDescription());
    result.setLanguage(ptvWebPage.getLanguage());
    result.setType(ptvWebPage.getType());
    result.setUrl(ptvWebPage.getUrl());
    result.setValue(ptvWebPage.getValue());
    
    return result;
  }

  public Service translateService(VmOpenApiService service) {
    if (service == null) {
      return null;
    }
    
    Service result = new Service();
    result.setId(service.getId());
    result.setType(service.getType());
    result.setStatutoryDescriptionId(service.getStatutoryServiceGeneralDescriptionId());
    result.setServiceClasses(translateFintoItems(service.getServiceClasses()));
    result.setOntologyTerms(translateFintoItems(service.getOntologyTerms()));
    result.setLifeEvents(translateFintoItems(service.getLifeEvents()));
    result.setIndustrialClasses(translateFintoItems(service.getIndustrialClasses()));
    result.setNames(translateLocalizedListItems(service.getServiceNames()));
    result.setDescriptions(translateLocalizedListItems(service.getServiceDescriptions()));
    result.setLanguages(service.getLanguages());
    result.setKeywords(service.getKeywords());
    result.setCoverageType(service.getServiceChargeType());
    result.setMunicipalities(service.getMunicipalities());
    result.setWebPages(translateWebPages(service.getWebPages()));
    result.setRequirements(translateLangaugeItems(service.getRequirements()));
    result.setPublishingStatus(service.getPublishingStatus());
    result.setChargeType(service.getServiceChargeType());
    result.setAdditionalInformations(translateLocalizedListItems(service.getServiceAdditionalInformations()));
    
    return result;
  }

  public Service translateService(IVmOpenApiService service) {
    if (service == null) {
      return null;
    }
    
    Service result = new Service();
    result.setId(service.getId());
    result.setType(service.getType());
    result.setStatutoryDescriptionId(service.getStatutoryServiceGeneralDescriptionId());
    result.setServiceClasses(translateFintoItems(service.getServiceClasses()));
    result.setOntologyTerms(translateFintoItems(service.getOntologyTerms()));
    result.setLifeEvents(translateFintoItems(service.getLifeEvents()));
    result.setIndustrialClasses(translateFintoItems(service.getIndustrialClasses()));
    result.setNames(translateLocalizedListItems(service.getServiceNames()));
    result.setDescriptions(translateLocalizedListItems(service.getServiceDescriptions()));
    result.setLanguages(service.getLanguages());
    result.setKeywords(service.getKeywords());
    result.setCoverageType(service.getServiceChargeType());
    result.setMunicipalities(service.getMunicipalities());
    result.setWebPages(translateWebPages(service.getWebPages()));
    result.setRequirements(translateLangaugeItems(service.getRequirements()));
    result.setPublishingStatus(service.getPublishingStatus());
    result.setChargeType(service.getServiceChargeType());
    result.setAdditionalInformations(translateLocalizedListItems(service.getServiceAdditionalInformations()));
    
    return result;
  }

  private FintoItem translateFintoItem(VmOpenApiFintoItem ptvFintoItem) {
    if (ptvFintoItem == null) {
      return null;
    }
    
    FintoItem result = new FintoItem();
    result.setCode(ptvFintoItem.getCode());
    result.setId(ptvFintoItem.getId());
    result.setName(ptvFintoItem.getName());
    result.setOntologyType(ptvFintoItem.getOntologyType());
    result.setParentId(ptvFintoItem.getParentId());
    result.setParentUri(ptvFintoItem.getParentId());
    result.setUri(ptvFintoItem.getUri());
    
    return result;
  }
}
