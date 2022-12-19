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
import javafx.stage.Stage;
import model.Category;
import model.FinanceManagementSystem;
import model.User;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportController {
  public Button btnExpUsers;
  public Button btnExpCat;
  public Button btnBack;
  private FinanceManagementSystem fmis;
  Alert mainAlert = new Alert(Alert.AlertType.INFORMATION);
  private User currentUser;
  EntityManagerFactory factory = Persistence.createEntityManagerFactory("KursinioHibernate");

  CategoryHibernate categoryHibernateControl = new CategoryHibernate(factory);
  UserHibernate userHibernateControl = new UserHibernate(factory);

  public void setCurrentUser(FinanceManagementSystem fmis, User user) {
    this.fmis = fmis;
    this.currentUser = user;
  }

  public void exportUsers(ActionEvent actionEvent) throws IOException {
    File Obj = new File("exportedUsers.txt");
    Obj.createNewFile();
    try {

      FileWriter Writer = new FileWriter("exportedUsers.txt");

      for (User u : userHibernateControl.getUserList()) {
        Writer.write(u + "\n");
      }

      Writer.close();
      mainAlert.setAlertType(Alert.AlertType.INFORMATION);
      mainAlert.setHeaderText("Export");
      mainAlert.setContentText("Users exported successfully");
      mainAlert.showAndWait();

    } catch (IOException e) {
      mainAlert.setAlertType(Alert.AlertType.INFORMATION);
      mainAlert.setHeaderText("Export");
      mainAlert.setContentText("Export failed, ERROR: " + e.getMessage());
      mainAlert.showAndWait();
    }
  }

  public void exportCategories(ActionEvent actionEvent) throws IOException {
    File Obj = new File("exportedCategories.txt");
    Obj.createNewFile();
    try {
      FileWriter Writer = new FileWriter("exportedCategories.txt");

      for (Category c : categoryHibernateControl.getCategoryList()) {
        Writer.write(
            "Category ID: "
                + c.getCategory_ID()
                + "\n"
                + "Is category a subcategory: "
                + c.isSubCategory()
                + "\n"
                + "Parent: "
                + c.getParent()
                + "\n"
                + "Name: "
                + c.getName()
                + "\n"
                + "Description: "
                + c.getDescription()
                + "\n"
                + "Date created: "
                + c.getDateCreated()
                + "\n"
                + "Date modified: "
                + c.getDateModified()
                + "\n"
                + "Subcategories: "
                + c.getSubCategories()
                + "\n"
                + "Responsible users: "
                + c.getResponsibleUsers()
                + "\n"
                + "Income: "
                + c.getIncome()
                + "\n"
                + "Expenditure: "
                + c.getExpenditure()
                + "\n"
                + "-------------------------"
                + "\n");
      }
      Writer.close();
      mainAlert.setAlertType(Alert.AlertType.INFORMATION);
      mainAlert.setHeaderText("Export");
      mainAlert.setContentText("Categories exported successfully");
      mainAlert.showAndWait();
    } catch (IOException e) {
      mainAlert.setAlertType(Alert.AlertType.INFORMATION);
      mainAlert.setHeaderText("Export");
      mainAlert.setContentText("Export failed, ERROR: " + e.getMessage());
      mainAlert.showAndWait();
    }
  }

  public void goToLastWindow(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/MainWindow.fxml"));
    Parent root = loader.load();

    MainWindowController mainWindow = loader.getController();
    mainWindow.setCurrentUser(fmis, currentUser);

    Stage stage = (Stage) btnBack.getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }
}
