package model;

import com.sun.org.apache.xpath.internal.objects.XString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** The type Category. */
@Entity
public class Category implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int category_ID;

  private boolean subCategory;
  private String parent;
  @ManyToOne
  private Category parentCategory = null;
  private String name;
  private String description;
  private LocalDate dateCreated;
  private LocalDate dateModified;

  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "parentCategory", orphanRemoval = true)
  @OrderBy("category_ID ASC")
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Category> subCategories;

  @ManyToMany(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      mappedBy = "responsibleCategories")
  @OrderBy("user_ID ASC")
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<User> responsibleUsers;

  @OneToMany(cascade = CascadeType.MERGE, mappedBy = "category", orphanRemoval = true)
  @OrderBy("income_ID ASC")
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Income> income;

  @OneToMany(cascade = CascadeType.MERGE, mappedBy = "category", orphanRemoval = true)
  @OrderBy("expenditure_ID ASC")
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Expenditure> expenditure;

  @ManyToOne private FinanceManagementSystem financeMS;

  public Category() {}

  /**
   * Instantiates a new Category.
   *
   * @param category_ID the category id
   * @param subCategory boolean to check if category is a subcategory
   * @param parent the parent name
   * @param name the name
   * @param description the description
   * @param dateCreated the date created
   * @param dateModified the date modified
   * @param subCategories the sub categories
   * @param responsibleUsers the responsible users
   * @param income the income
   * @param expenditure the expenditure
   */
  public Category(
      int category_ID,
      boolean subCategory,
      String parent,
      String name,
      String description,
      LocalDate dateCreated,
      LocalDate dateModified,
      List<Category> subCategories,
      List<User> responsibleUsers,
      List<Income> income,
      List<Expenditure> expenditure) {
    this.category_ID = category_ID;
    this.subCategory = subCategory;
    this.parent = parent;
    this.name = name;
    this.description = description;
    this.dateCreated = dateCreated;
    this.dateModified = dateModified;
    this.subCategories = subCategories;
    this.responsibleUsers = responsibleUsers;
    this.income = income;
    this.expenditure = expenditure;
  }

  public Category(
      boolean subCategory,
      String parent,
      Category parentCategory,
      String name,
      String description,
      LocalDate dateCreated,
      LocalDate dateModified,
      List<Category> subCategories,
      List<User> responsibleUsers,
      List<Income> income,
      List<Expenditure> expenditure,
      FinanceManagementSystem financeMS) {
    this.subCategory = subCategory;
    this.parent = parent;
    this.parentCategory = parentCategory;
    this.name = name;
    this.description = description;
    this.dateCreated = dateCreated;
    this.dateModified = dateModified;
    this.subCategories = subCategories;
    this.responsibleUsers = responsibleUsers;
    this.income = income;
    this.expenditure = expenditure;
    this.financeMS = financeMS;
  }

  public int getCategory_ID() {
    return category_ID;
  }

  public void setCategory_ID(int category_ID) {
    this.category_ID = category_ID;
  }

  public boolean isSubCategory() {
    return subCategory;
  }

  public void setSubCategory(boolean subCategory) {
    this.subCategory = subCategory;
  }

  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(LocalDate dateCreated) {
    this.dateCreated = dateCreated;
  }

  public LocalDate getDateModified() {
    return dateModified;
  }

  public void setDateModified(LocalDate dateModified) {
    this.dateModified = dateModified;
  }

  public List<Category> getSubCategories() {
    return subCategories;
  }

  public void setSubCategories(List<Category> subCategories) {
    this.subCategories = subCategories;
  }

  public List<User> getResponsibleUsers() {
    return responsibleUsers;
  }

  public void setResponsibleUsers(List<User> responsibleUsers) {
    this.responsibleUsers = responsibleUsers;
  }

  public List<Income> getIncome() {
    return income;
  }

  public void setIncome(List<Income> income) {
    this.income = income;
  }

  public List<Expenditure> getExpenditure() {
    return expenditure;
  }

  public void setExpenditure(List<Expenditure> expenditure) {
    this.expenditure = expenditure;
  }

  public void addSubcategory(Category category) {
    subCategories.add(category);
  }

  public void removeSubcategory(Category category) {
    subCategories.remove(category);
  }

  public Category getParentCategory() {
    return parentCategory;
  }

  public void setParentCategory(Category parentCategory) {
    this.parentCategory = parentCategory;
  }

  public FinanceManagementSystem getFinanceMS() {
    return financeMS;
  }

  public void setFinanceMS(FinanceManagementSystem financeMS) {
    this.financeMS = financeMS;
  }

  public void addUser(User user)
  {
    this.getResponsibleUsers().add(user);
  }

  public void removeUser(User user)
  {
    this.getResponsibleUsers().remove(user);
  }

  public void removeSystem(Category category)
  {
    this.financeMS = null;
  }

  public void removeSubCategories(Category category){
    this.subCategories = null;
  }

  public void addExpenditure(Expenditure expenditure)
  {
    this.getExpenditure().add(expenditure);
  }
  public void addIncome(Income income)
  {
    this.getIncome().add(income);
  }



  @Override
  public String toString() {
    return name;
  }
}
