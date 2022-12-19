package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/** The type Income. */
@Entity
public class Income implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int income_ID;
  private String name;
  private String description;
  private int amount;
  private LocalDate date;
  @ManyToOne
  private Category category;


  public Income() {
  }

  /**
   * Instantiates a new Income.
   *
   * @param name the name
   * @param amount the amount
   */
  public Income(String name, int amount) {
    this.name = name;
    this.amount = amount;
  }

  /**
   * Instantiates a new Income.
   *
   * @param income_ID the income id
   * @param name the name
   * @param description the description
   * @param amount the amount
   * @param date the date
   */
  public Income(int income_ID, String name, String description, int amount, LocalDate date) {
    this.income_ID = income_ID;
    this.name = name;
    this.description = description;
    this.amount = amount;
    this.date = date;
  }

  public Income(String name, String description, int amount, LocalDate date, Category category) {
    this.name = name;
    this.description = description;
    this.amount = amount;
    this.date = date;
    this.category = category;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public int getIncome_ID() {
    return income_ID;
  }

  public void setIncome_ID(int income_ID) {
    this.income_ID = income_ID;
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

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public void addCategory(Category category)
  {
    this.category = category;
  }

  @Override
  public String toString() {
    return "Income{" + "Name='" + name + '\'' + ", Amount='" + amount + '\'' + '}';
  }
}
