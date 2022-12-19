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
import javafx.stage.Stage;
import model.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class RemoveResponsiblePersonController {
  public Button btnBack;
  public ComboBox comboID;
  public Button btnRemove;
  private FinanceManagementSystem fmis;
  Alert mainAlert = new Alert(Alert.AlertType.INFORMATION);
  private User currentUser;
  private String currentCategory;
  EntityManagerFactory factory = Persistence.createEntityManagerFactory("KursinioHibernate");
  CategoryHibernate categoryHibernateControl = new CategoryHibernate(factory);

  public void setCurrentCategory(FinanceManagementSystem fmis, User user, String name) {
    this.fmis = fmis;
    this.currentUser = user;
    this.currentCategory = name;
    addToResponsiblePersonCombo();
  }

  public void goToLastWindow(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader =
        new FXMLLoader(getClass().getResource("/fxmlPackage/RemoveFromCategoryWindow.fxml"));
    Parent root = loader.load();

    RemoveFromCategoryController removeFromCategoryWindow = loader.getController();
    removeFromCategoryWindow.setCurrentCategory(fmis, currentUser, currentCategory);

    Stage stage = (Stage) btnBack.getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }

  public void removeResponsiblePerson(ActionEvent actionEvent) throws Exception {
    if (categorySelected()) {
     A: for (Category c : categoryHibernateControl.getCategoryList()) {
        if (c.getName().equals(currentCategory)) {
          for (User u : c.getResponsibleUsers()) {
            if (u.getUser_ID() == Integer.parseInt(comboID.getValue().toString())) {
              categoryHibernateControl.RemoveResponsibleUserToCategory(c,u);
              comboID.getItems().remove(Integer.toString(u.getUser_ID()));
              mainAlert.setAlertType(Alert.AlertType.INFORMATION);
              mainAlert.setHeaderText("Responsible person");
              mainAlert.setContentText("Responsible person removed successfully");
              mainAlert.showAndWait();
              break A;
            }
          }
        }
      }
    } else {
      mainAlert.setAlertType(Alert.AlertType.ERROR);
      mainAlert.setHeaderText("Responsible person");
      mainAlert.setContentText("Select responsible person ID");
      mainAlert.showAndWait();
    }
  }

  private boolean categorySelected() {
    if (comboID.getSelectionModel().isEmpty()) {
      return false;
    } else {
      return true;
    }
  }

  private void addToResponsiblePersonCombo()
  {
    for (Category c : categoryHibernateControl.getCategoryList()) {
      if(c.getName().equals(currentCategory))
      {
        for(User u: c.getResponsibleUsers())
        {
          comboID.getItems().add(Integer.toString(u.getUser_ID()));
        }
        break;
      }
    }
  }
}
