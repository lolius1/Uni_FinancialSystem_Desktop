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
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Category;
import model.User;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.ArrayList;

public class FilterUsersController {
  public Button btnFilter;
  public ListView listViewUsers;
  public ComboBox comboType;
  public Button btnBack;
  Alert mainAlert = new Alert(Alert.AlertType.INFORMATION);
  EntityManagerFactory factory = Persistence.createEntityManagerFactory("KursinioHibernate");
  UserHibernate userHibernateControl = new UserHibernate(factory);
  private ArrayList<User> users = new ArrayList<User>();

  public void initialize() {
    users.clear();
    users.addAll(userHibernateControl.getUserList());
    updateUsersListView(users);
    comboType.getItems().add("admin");
    comboType.getItems().add("regular");
  }

  public void filterCategories(ActionEvent actionEvent) {
    if(!comboType.getSelectionModel().isEmpty())
    {
      filterUsersByType();
    }
    else
    {
      mainAlert.setAlertType(Alert.AlertType.ERROR);
      mainAlert.setHeaderText("Type");
      mainAlert.setContentText("Select a type");
      mainAlert.showAndWait();
    }
  }

  private void updateUsersListView(ArrayList<User> user) {
    if (listViewUsers.getItems().size() > 0) listViewUsers.getItems().clear();

    user.forEach(
        u -> {
          String userinfo =
              "ID: "
                  + u.getUser_ID()
                  + " Name: "
                  + u.getName()
                  + " Surname: "
                  + u.getSurname()
                  + " Login: "
                  + u.getLogin()
                  + " Password: "
                  + u.getPsw();

          listViewUsers.getItems().add(userinfo);
        });
  }

  public void filterUsersByType() {
    users.clear();
    users.addAll(userHibernateControl.getFilteredTypeUserList(comboType.getValue().toString()));
    updateUsersListView(users);
  }

  public void goToLastWindow(ActionEvent actionEvent) throws IOException {

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/UserWindow.fxml"));
    Parent root = loader.load();

    Stage stage = (Stage) btnBack.getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }
}
