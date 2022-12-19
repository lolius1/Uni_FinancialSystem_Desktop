package controller;

import hibernate.CategoryHibernate;
import hibernate.FinanceManagementSystemHibernate;
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

public class UpdateCategoryController {
  public TextField textName;
  public TextField textDescription;
  public Button btnUpdate;
  public Button btnBack;
  private FinanceManagementSystem fmis;
  private boolean alreadyExists = false;
  Alert mainAlert = new Alert(Alert.AlertType.INFORMATION);
  private User currentUser;
  private String currentCategory;
  EntityManagerFactory factory = Persistence.createEntityManagerFactory("KursinioHibernate");
  FinanceManagementSystemHibernate financeManagementSystemHibernateControl = new FinanceManagementSystemHibernate(factory);
  CategoryHibernate categoryHibernateControl = new CategoryHibernate(factory);

  public void setCurrentCategory(FinanceManagementSystem fmis, User user, String name) {
    this.fmis = fmis;
    this.currentUser = user;
    this.currentCategory = name;
    textName.setText(name);
    for (Category c : categoryHibernateControl.getCategoryList()) {
      if (c.getName().equals(name)) {
        textDescription.setText(c.getDescription());
        break;
      }
    }
  }

  public void updateCategory(ActionEvent actionEvent) {
    if (!textName.getText().isEmpty() && !textDescription.getText().isEmpty()) {
      for (Category c : categoryHibernateControl.getCategoryList()) {
        if (c.getName().equals(currentCategory)) {
          for (Category o : categoryHibernateControl.getCategoryList()) {
            if (textName.getText().equals(o.getName()) && !textName.getText().equals(currentCategory)) {
              alreadyExists = true;
              break;
            }
          }
          if (!alreadyExists) {
            c.setName(textName.getText());
            c.setDescription(textDescription.getText());
            categoryHibernateControl.edit(c);
            currentCategory = textName.getText();
            mainAlert.setAlertType(Alert.AlertType.INFORMATION);
            mainAlert.setHeaderText("Category");
            mainAlert.setContentText("Category updated successfully");
            mainAlert.showAndWait();
            break;
          } else {
            mainAlert.setAlertType(Alert.AlertType.ERROR);
            mainAlert.setHeaderText("Category");
            mainAlert.setContentText("Such category already exists");
            mainAlert.showAndWait();
            alreadyExists = false;
            break;
          }
        }
      }
    } else {
      mainAlert.setAlertType(Alert.AlertType.ERROR);
      mainAlert.setHeaderText("Empty");
      mainAlert.setContentText("Please fill in the empty fields");
      mainAlert.showAndWait();
    }
  }

  public void goToLastWindow(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader =
        new FXMLLoader(getClass().getResource("/fxmlPackage/CategoryWindow.fxml"));
    Parent root = loader.load();

    CategoryController categoryControllerWindow = loader.getController();
    categoryControllerWindow.setCurrentUser(fmis, currentUser);

    Stage stage = (Stage) btnBack.getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }
}
