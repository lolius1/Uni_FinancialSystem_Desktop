package controller;

import hibernate.FinanceManagementSystemHibernate;
import hibernate.UserHibernate;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.FinanceManagementSystem;
import model.User;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class DeleteUserController {
  public Button btnBack;
  public ComboBox comboID;
  public Button btnRemove;

  private FinanceManagementSystem fmis;
  Alert mainAlert = new Alert(Alert.AlertType.INFORMATION);
  private User currentUser;
  EntityManagerFactory factory = Persistence.createEntityManagerFactory("KursinioHibernate");
  UserHibernate userHibernateControl = new UserHibernate(factory);
  FinanceManagementSystemHibernate financeManagementSystemHibernateControl =
          new FinanceManagementSystemHibernate(factory);

  public void setCurrentUser(FinanceManagementSystem fmis, User user) {
    this.fmis = fmis;
    for(User u: financeManagementSystemHibernateControl.findFinanceManagementSystem("Amazing").getSystemUsers())
    {
      currentUser = u;
    }
    addToIDCombo();
  }

  public void goToLastWindow(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/UserWindow.fxml"));
    Parent root = loader.load();

    UserController userWindow = loader.getController();
    userWindow.setCurrentUser(fmis, currentUser);

    Stage stage = (Stage) btnBack.getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }

  public void deleteUser(ActionEvent actionEvent) {
    if (categorySelected()) {
      for (User u : userHibernateControl.getUserList()) {
        if (comboID.getValue().equals(Integer.toString(u.getUser_ID()))) {
          comboID.getItems().remove(Integer.toString(u.getUser_ID()));
          userHibernateControl.remove(u.getUser_ID());
          mainAlert.setAlertType(Alert.AlertType.INFORMATION);
          mainAlert.setHeaderText("User");
          mainAlert.setContentText("User deleted successfully");
          mainAlert.showAndWait();
          break;
        }
      }
    } else {
      mainAlert.setAlertType(Alert.AlertType.ERROR);
      mainAlert.setHeaderText("Category");
      mainAlert.setContentText("Select a category");
      mainAlert.showAndWait();
    }
  }

  private void addToIDCombo() {
    for (User u : userHibernateControl.getUserList()) {
      if (u.getUser_ID() != currentUser.getUser_ID()){
        comboID.getItems().add(Integer.toString(u.getUser_ID()));
        }
    }
  }

  private boolean categorySelected() {
    if (comboID.getSelectionModel().isEmpty()) {
      return false;
    } else {
      return true;
    }
  }
}
