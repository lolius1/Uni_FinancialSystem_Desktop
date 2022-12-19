package model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/** The type User. */
@Entity
public class User implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int user_ID;

  private String name;
  private String surname;
  private String login;
  private String psw;
  private String email;
  private String phoneNum;
  private String type;
  @ManyToMany @LazyCollection(LazyCollectionOption.FALSE) private List<Category> responsibleCategories;
  @ManyToOne private FinanceManagementSystem financeMS;

  public User() {}

  /**
   * Instantiates a new User.
   *
   * @param login the login
   * @param psw the password
   */
  public User(String login, String psw) {
    this.login = login;
    this.psw = psw;
  }

  /**
   * Instantiates a new User.
   *
   * @param user_ID the user id
   * @param name the name
   * @param surname the surname
   * @param login the login
   * @param psw the password
   * @param email the email
   * @param phoneNum the phone number
   * @param type the type
   */
  public User(
      int user_ID,
      String name,
      String surname,
      String login,
      String psw,
      String email,
      String phoneNum,
      String type) {
    this.user_ID = user_ID;
    this.name = name;
    this.surname = surname;
    this.login = login;
    this.psw = psw;
    this.email = email;
    this.phoneNum = phoneNum;
    this.type = type;
  }

  public User(
      String name,
      String surname,
      String login,
      String psw,
      String email,
      String phoneNum,
      String type,
      List<Category> responsibleCategories,
      FinanceManagementSystem financeMS) {
    this.name = name;
    this.surname = surname;
    this.login = login;
    this.psw = psw;
    this.email = email;
    this.phoneNum = phoneNum;
    this.type = type;
    this.responsibleCategories = responsibleCategories;
    this.financeMS = financeMS;
  }

  public int getUser_ID() {
    return user_ID;
  }

  public void setUser_ID(int user_ID) {
    this.user_ID = user_ID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPsw() {
    return psw;
  }

  public void setPsw(String psw) {
    this.psw = psw;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "User{" + "login='" + login + '\'' + ", psw='" + psw + '\'' + '}';
  }

  public List<Category> getResponsibleCategories() {
    return responsibleCategories;
  }

  public void setResponsibleCategories(List<Category> responsibleCategories) {
    this.responsibleCategories = responsibleCategories;
  }

  public FinanceManagementSystem getFinanceMS() {
    return financeMS;
  }

  public void setFinanceMS(FinanceManagementSystem financeMS) {
    this.financeMS = financeMS;
  }

  public void addCategory(Category category)
  {
    this.getResponsibleCategories().add(category);
  }


  public void removeCategory(Category category) {
    this.getResponsibleCategories().remove(category);
  }
}
