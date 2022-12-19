package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/** The type Expenditure. */
@Entity
public class Expenditure implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int expenditure_ID;
  private String name;
  private String description;
  private int amount;
  private LocalDate date;
  @ManyToOne
  private Category category;


  public Expenditure() {
  }

  /**
   * Instantiates a new Expenditure.
   *
   * @param name the name
   * @param amount the amount
   */
  public Expenditure(String name, int amount) {
    this.name = name;
    this.amount = amount;
  }

  /**
   * Instantiates a new Expenditure.
   *
   * @param expenditure_ID the expenditure id
   * @param name the name
   * @param description the description
   * @param amount the amount
   * @param date the date
   */
  public Expenditure(
      int expenditure_ID, String name, String description, int amount, LocalDate date) {
    this.expenditure_ID = expenditure_ID;
    this.name = name;
    this.description = description;
    this.amount = amount;
    this.date = date;
  }

  public Expenditure(String name, String description, int amount, LocalDate date, Category category) {
    this.name = name;
    this.description = description;
    this.amount = amount;
    this.date = date;
    this.category = category;
  }



  public int getExpenditure_ID() {
    return expenditure_ID;
  }

  public void setExpenditure_ID(int expenditure_ID) {
    this.expenditure_ID = expenditure_ID;
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

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public void addCategory(Category category)
  {
    this.category = category;
  }

  @Override
  public String toString() {
    return "Expenditure{" + "Name='" + name + '\'' + ", Amount='" + amount + '\'' + '}';
  }
}
