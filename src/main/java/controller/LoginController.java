package controller;

import hibernate.FinanceManagementSystemHibernate;
import hibernate.UserHibernate;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Category;
import model.FinanceManagementSystem;
import model.User;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController {
  public Button btnLogin;
  public TextField textUser;
  public TextField textPsw;
  public Button btnSign;
  private FinanceManagementSystem fmis =
      new FinanceManagementSystem(
          "Amazing",
          "Amazing system",
          LocalDate.now(),
          "V1",
          new ArrayList<Category>(),
          new ArrayList<User>());
  private User currentUser;
  Alert mainAlert = new Alert(Alert.AlertType.INFORMATION);
  EntityManagerFactory factory = Persistence.createEntityManagerFactory("KursinioHibernate");
  FinanceManagementSystemHibernate financeManagementSystemHibernateControl =
      new FinanceManagementSystemHibernate(factory);
  UserHibernate userHibernateControl = new UserHibernate(factory);

  public void setFinanceManagementSystem(FinanceManagementSystem fmis) {
    this.fmis = fmis;
  }

  public void loginCheck(ActionEvent actionEvent) throws Exception {
    if (financeManagementSystemHibernateControl.findFinanceManagementSystem("Amazing") == null){
      financeManagementSystemHibernateControl.create(fmis);
    }
    User u =
        userHibernateControl.getUserList().stream()
            .filter(
                user ->
                    user.getLogin().equals(textUser.getText())
                        && user.getPsw().equals(textPsw.getText()))
            .findFirst()
            .orElse(null);
    if (u != null) {
      currentUser = u;
      //remove system user
      for(User cu: userHibernateControl.getUserList())
      {
          cu.setFinanceMS(null);
          userHibernateControl.edit(cu);
      }
      //add new system user
      for(User cu: userHibernateControl.getUserList())
      {
        if(cu.getUser_ID() == currentUser.getUser_ID())
        {
          cu.setFinanceMS(financeManagementSystemHibernateControl.findFinanceManagementSystem("Amazing"));
          userHibernateControl.edit(cu);
          break;
        }
      }
      loadMainWindow();
    } else {
      unsuccessfulLogin(mainAlert);
    }
  }

  public void userSignUp(ActionEvent actionEvent) {
    if (!textUser.getText().isEmpty() && !textPsw.getText().isEmpty()) {
      successfulSignUp(mainAlert);
    } else if (textUser.getText().isEmpty()) {
      emptyUsername(mainAlert);
    } else if (textPsw.getText().isEmpty()) {
      emptyPassword(mainAlert);
    }
  }

  private void successfulSignUp(Alert alert) {
    User user = new User(textUser.getText(), textPsw.getText());
    userHibernateControl.create(user);
    alert.setAlertType(Alert.AlertType.INFORMATION);
    alert.setHeaderText("Sign up");
    alert.setContentText(
        "Sign up successful, account has been created. Your ID is: " + user.getUser_ID());
    alert.showAndWait();
  }

  private void emptyUsername(Alert alert) {
    alert.setAlertType(Alert.AlertType.ERROR);
    alert.setHeaderText("Sign up");
    alert.setContentText("Sign up failed, please write your username");
    alert.showAndWait();
  }

  private void emptyPassword(Alert alert) {
    alert.setAlertType(Alert.AlertType.ERROR);
    alert.setHeaderText("Sign up");
    alert.setContentText("Sign up failed, please write your password");
    alert.showAndWait();
  }

  private void unsuccessfulLogin(Alert alert) {
    alert.setAlertType(Alert.AlertType.ERROR);
    alert.setHeaderText("Login");
    alert.setContentText("Login failed, no such user");
    alert.showAndWait();
  }

  private void loadMainWindow() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/MainWindow.fxml"));
    Parent root = loader.load();

    MainWindowController mainFinanceWindow = loader.getController();
    mainFinanceWindow.setCurrentUser(fmis, currentUser);

    Stage stage = (Stage) btnLogin.getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }
}
