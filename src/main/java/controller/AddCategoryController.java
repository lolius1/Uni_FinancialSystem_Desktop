package controller;

import hibernate.CategoryHibernate;
import hibernate.FinanceManagementSystemHibernate;
import hibernate.UserHibernate;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddCategoryController {

  public TextField textID;
  public TextField textName;
  public TextField textDescription;
  public Button btnBack;
  public Button btnAdd;
  public ComboBox comboParent;
  private FinanceManagementSystem fmis;
  Alert mainAlert = new Alert(Alert.AlertType.INFORMATION);
  private User currentUser;
  EntityManagerFactory factory = Persistence.createEntityManagerFactory("KursinioHibernate");
  FinanceManagementSystemHibernate financeManagementSystemHibernateControl = new FinanceManagementSystemHibernate(factory);
  CategoryHibernate categoryHibernateControl = new CategoryHibernate(factory);

  public void setCurrentUser(FinanceManagementSystem fmis, User user) {
    this.fmis = fmis;
    this.currentUser = user;
    addToParentCombo();
  }

  public void goToLastWindow(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/CategoryWindow.fxml"));
    Parent root = loader.load();

    CategoryController categoryControllerWindow = loader.getController();
    categoryControllerWindow.setCurrentUser(fmis,currentUser);

    Stage stage = (Stage) btnBack.getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }

  public void addCategory(ActionEvent actionEvent) throws Exception {

    if (emptyFields()) {
      if (!comboParent.getSelectionModel().isEmpty()) {
        for (Category c : categoryHibernateControl.getCategoryList()) {
          if (c.getName().equals(comboParent.getValue().toString())) {
            Category category = new Category(
                    true,
                    comboParent.getValue().toString(),
                    c,
                    textName.getText(),
                    textDescription.getText(),
                    LocalDate.now(),
                    LocalDate.now(),
                    new ArrayList<Category>(),
                    new ArrayList<User>(),
                    new ArrayList<Income>(),
                    new ArrayList<Expenditure>(),
                    financeManagementSystemHibernateControl.findFinanceManagementSystem("Amazing"));
            categoryHibernateControl.create(category);
            for(User u: financeManagementSystemHibernateControl.findFinanceManagementSystem("Amazing").getSystemUsers())
            {
              currentUser = u;
            }
            categoryHibernateControl.AddResponsibleUserToCategory(category,currentUser);
            mainAlert.setAlertType(Alert.AlertType.INFORMATION);
            mainAlert.setHeaderText("Subcategory");
            mainAlert.setContentText("Subcategory added successfully");
            mainAlert.showAndWait();
            break;
          }
        }
      } else {
        Category category =
                new Category(
                    false,
                    "none",
                    null,
                    textName.getText(),
                    textDescription.getText(),
                    LocalDate.now(),
                    LocalDate.now(),
                    new ArrayList<Category>(),
                        new ArrayList<User>(),
                    new ArrayList<Income>(),
                    new ArrayList<Expenditure>(),
                    financeManagementSystemHibernateControl.findFinanceManagementSystem("Amazing"));
        categoryHibernateControl.create(category);
        categoryHibernateControl.AddResponsibleUserToCategory(category,currentUser);
        mainAlert.setAlertType(Alert.AlertType.INFORMATION);
        mainAlert.setHeaderText("Category");
        mainAlert.setContentText("Category added successfully");
        mainAlert.showAndWait();
      }
      addToParentCombo();
    }
    else
    {
      mainAlert.setAlertType(Alert.AlertType.ERROR);
      mainAlert.setHeaderText("Fields");
      mainAlert.setContentText("Please fill in the fields correctly");
      mainAlert.showAndWait();
    }
  }

  private void addToParentCombo()
  {
    comboParent.getItems().clear();
    for (Category c : categoryHibernateControl.getCategoryList()) {
      if(!comboParent.getItems().contains(c.getName()))
          comboParent.getItems().add(c.getName());
    }
  }

  private boolean emptyFields()
  {
    if(textName.getText().isEmpty()
            || textDescription.getText().isEmpty())
    {
      return false;
    }
    else{
      return true;
    }
  }

}
