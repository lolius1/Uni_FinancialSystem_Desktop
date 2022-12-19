package model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** The type Finance management system. */
@Entity
public class FinanceManagementSystem implements Serializable {
  @Id
  private String company;
  private String name;
  private LocalDate dateCreated;
  private String systemVersion;
  @OneToMany(mappedBy = "financeMS", cascade= CascadeType.MERGE, orphanRemoval=true)
  @OrderBy("category_ID ASC")
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Category> categories = new ArrayList<Category>();
  @OneToMany(mappedBy = "financeMS", cascade= CascadeType.MERGE, orphanRemoval=true)
  @OrderBy("user_ID ASC")
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<User> systemUsers = new ArrayList<User>();

  /** Instantiates a new Finance management system. */
  public FinanceManagementSystem() {}

  public FinanceManagementSystem(String company, String name, LocalDate dateCreated, String systemVersion) {
    this.company = company;
    this.name = name;
    this.dateCreated = dateCreated;
    this.systemVersion = systemVersion;
  }

  /**
   * Instantiates a new Finance management system.
   *
   * @param company the company
   * @param dateCreated the date created
   * @param systemVersion the system version
   */
  public FinanceManagementSystem(String company, LocalDate dateCreated, String systemVersion) {
    this.company = company;
    this.dateCreated = dateCreated;
    this.systemVersion = systemVersion;
  }

  public FinanceManagementSystem(String company, String name, LocalDate dateCreated, String systemVersion, List<Category> categories, List<User> systemUsers) {
    this.company = company;
    this.name = name;
    this.dateCreated = dateCreated;
    this.systemVersion = systemVersion;
    this.categories = categories;
    this.systemUsers = systemUsers;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public LocalDate getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(LocalDate dateCreated) {
    this.dateCreated = dateCreated;
  }

  public String getSystemVersion() {
    return systemVersion;
  }

  public void setSystemVersion(String systemVersion) {
    this.systemVersion = systemVersion;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public List<User> getSystemUsers() {
    return systemUsers;
  }

  public void setSystemUsers(List<User> systemUsers) {
    this.systemUsers = systemUsers;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
