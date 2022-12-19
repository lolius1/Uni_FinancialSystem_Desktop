package controller;

import hibernate.CategoryHibernate;
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
import model.Category;
import model.FinanceManagementSystem;
import model.User;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class AddResponsiblePersonController {
  public Button btnBack;
  public Button btnAdd;
  public ComboBox cmbUsers;
  private FinanceManagementSystem fmis;
  Alert mainAlert = new Alert(Alert.AlertType.INFORMATION);
  private User currentUser;
  private String currentCategory;
  EntityManagerFactory factory = Persistence.createEntityManagerFactory("KursinioHibernate");
  CategoryHibernate categoryHibernateControl = new CategoryHibernate(factory);
  UserHibernate userHibernateControl = new UserHibernate(factory);


  public void setCurrentCategory(FinanceManagementSystem fmis, User user, String name) {
    this.fmis = fmis;
    this.currentUser = user;
    this.currentCategory = name;
    addToUserCombo();
  }

  public void goToLastWindow(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader =
        new FXMLLoader(getClass().getResource("/fxmlPackage/AddToCategoryWindow.fxml"));
    Parent root = loader.load();

    AddToCategoryController addToCategoryWindow = loader.getController();
    addToCategoryWindow.setCurrentCategory(fmis, currentUser, currentCategory);

    Stage stage = (Stage) btnBack.getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }

  public void addResponsiblePerson(ActionEvent actionEvent) throws Exception {
    A: for (Category c : categoryHibernateControl.getCategoryList()) {
      if (c.getName().equals(currentCategory)) {
        for (User u : userHibernateControl.getUserList()) {
          if (u.getUser_ID() == Integer.parseInt(cmbUsers.getValue().toString())) {
            categoryHibernateControl.AddResponsibleUserToCategory(c,u);
            break A;
          }
        }
      }
    }
    mainAlert.setAlertType(Alert.AlertType.INFORMATION);
    mainAlert.setHeaderText("Responsible person");
    mainAlert.setContentText("Responsible person added successfully");
    mainAlert.showAndWait();
  }

  private void addToUserCombo()
  {
    for (User u: userHibernateControl.getUserList()) {
      if(!cmbUsers.getItems().contains(u.getUser_ID()))
        cmbUsers.getItems().add(u.getUser_ID());
    }
  }
}
