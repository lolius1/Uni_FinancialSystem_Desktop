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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Category;
import model.FinanceManagementSystem;
import model.User;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.ArrayList;

public class AddUserController {
  public Button btnBack;
  public Button btnAdd;
  public TextField textID;
  public TextField textName;
  public TextField textSurname;
  public TextField textLogin;
  public TextField textPsw;
  public TextField textEmail;
  public TextField textNum;
  public ComboBox comboType;
  private FinanceManagementSystem fmis;
  Alert mainAlert = new Alert(Alert.AlertType.INFORMATION);
  private User currentUser;
  EntityManagerFactory factory = Persistence.createEntityManagerFactory("KursinioHibernate");
  UserHibernate userHibernateControl = new UserHibernate(factory);

  public void setCurrentUser(FinanceManagementSystem fmis, User user) {
    this.fmis = fmis;
    this.currentUser = user;
    comboType.getItems().add("admin");
    comboType.getItems().add("regular");
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

  public void addUser(ActionEvent actionEvent) {
    if (emptyFields()) {
      User user =
          new User(
              textName.getText(),
              textSurname.getText(),
              textLogin.getText(),
              textPsw.getText(),
              textEmail.getText(),
              textNum.getText(),
              comboType.getValue().toString(),
              new ArrayList<Category>(),
              null);
      userHibernateControl.create(user);
      mainAlert.setAlertType(Alert.AlertType.INFORMATION);
      mainAlert.setHeaderText("User");
      mainAlert.setContentText("User added successfully");
      mainAlert.showAndWait();
    } else {
      mainAlert.setAlertType(Alert.AlertType.ERROR);
      mainAlert.setHeaderText("Fields");
      mainAlert.setContentText("Please fill in the fields");
      mainAlert.showAndWait();
    }
  }

  private boolean emptyFields() {
    if (textName.getText().isEmpty()
        || textSurname.getText().isEmpty()
        || textLogin.getText().isEmpty()
        || textPsw.getText().isEmpty()
        || textEmail.getText().isEmpty()
        || textNum.getText().isEmpty()
        || comboType.getSelectionModel().isEmpty()) {
      return false;
    } else {
      return true;
    }
  }
}
