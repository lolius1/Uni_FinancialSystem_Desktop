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
import javafx.stage.Stage;
import model.Category;
import model.FinanceManagementSystem;
import model.User;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class CategoryController {
  public Button btnAdd;
  public Button btnAddTo;
  public Button btnUpd;
  public Button btnRem;
  public Button btnBack;
  public Button btnShow;
  public Button btnDel;
  public ComboBox comboCat;
    public Button btnFilter;
    private FinanceManagementSystem fmis;
  Alert mainAlert = new Alert(Alert.AlertType.INFORMATION);
  private User currentUser;
  EntityManagerFactory factory = Persistence.createEntityManagerFactory("KursinioHibernate");
  CategoryHibernate categoryHibernateControl = new CategoryHibernate(factory);


  public void setCurrentUser(FinanceManagementSystem fmis, User user) {
    this.fmis = fmis;
    this.currentUser = user;

  }

  public void initialize()
  {
    addToCategoryCombo();
  }

  public void addCategoryWindow(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader =
        new FXMLLoader(getClass().getResource("/fxmlPackage/AddCategoryWindow.fxml"));
    Parent root = loader.load();

    AddCategoryController addCategoryWindow = loader.getController();
    addCategoryWindow.setCurrentUser(fmis, currentUser);

    Stage stage = (Stage) btnAdd.getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }

  public void addToCategoryWindow(ActionEvent actionEvent) throws IOException {
    if (categorySelected()) {
      FXMLLoader loader =
          new FXMLLoader(getClass().getResource("/fxmlPackage/AddToCategoryWindow.fxml"));
      Parent root = loader.load();

      AddToCategoryController addToCategoryWindow = loader.getController();
      addToCategoryWindow.setCurrentCategory(fmis, currentUser, comboCat.getValue().toString());

      Stage stage = (Stage) btnAddTo.getScene().getWindow();
      stage.setScene(new Scene(root));
      stage.show();
      }
    else{
      mainAlert.setAlertType(Alert.AlertType.ERROR);
      mainAlert.setHeaderText("Category");
      mainAlert.setContentText("Select a category");
      mainAlert.showAndWait();
    }

  }

  public void updateCategoryWindow(ActionEvent actionEvent) throws IOException {
    if(categorySelected()){
      FXMLLoader loader =
          new FXMLLoader(getClass().getResource("/fxmlPackage/UpdateCategoryWindow.fxml"));
      Parent root = loader.load();

      UpdateCategoryController updateCategoryWindow = loader.getController();
      updateCategoryWindow.setCurrentCategory(fmis, currentUser, comboCat.getValue().toString());

      Stage stage = (Stage) btnUpd.getScene().getWindow();
      stage.setScene(new Scene(root));
      stage.show();
      }
      else{
      mainAlert.setAlertType(Alert.AlertType.ERROR);
      mainAlert.setHeaderText("Category");
      mainAlert.setContentText("Select a category");
      mainAlert.showAndWait();
    }
  }

  public void removeFromCategoryWindow(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/RemoveFromCategoryWindow.fxml"));
    Parent root = loader.load();

    RemoveFromCategoryController removeFromCategoryWindow = loader.getController();
    removeFromCategoryWindow.setCurrentCategory(fmis, currentUser,comboCat.getValue().toString());

    Stage stage = (Stage) btnRem.getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();

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

  public void showCategoriesWindow(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/ShowCategoriesWindow.fxml"));
    Parent root = loader.load();

    ShowCategoriesController showCategoriesWindow = loader.getController();
    showCategoriesWindow.setCurrentUser(fmis, currentUser);

    Stage stage = (Stage) btnShow.getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }

  public void deleteCategory(ActionEvent actionEvent) throws Exception {
    if (categorySelected()) {
      for (Category c : categoryHibernateControl.getCategoryList()) {
        if (c.getName().equals(comboCat.getValue().toString())) {
          categoryHibernateControl.remove(c.getCategory_ID());
          addToCategoryCombo();
          mainAlert.setAlertType(Alert.AlertType.INFORMATION);
          mainAlert.setHeaderText("Category");
          mainAlert.setContentText("Category successfully deleted");
          mainAlert.showAndWait();
          break;
          }
        }
      }
    }

  private void addToCategoryCombo()
  {
    comboCat.getItems().clear();
    for (Category c: categoryHibernateControl.getCategoryList()) {
      if(!comboCat.getItems().contains(c.getName()))
        comboCat.getItems().add(c.getName());
    }
  }


  private boolean categorySelected()
  {
    if(comboCat.getSelectionModel().isEmpty())
    {
      return false;
    }
    else{
      return true;
    }
  }

  public void loadFilterCategories(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/FilterCategoriesWindow.fxml"));
    Parent root = loader.load();


    Stage stage = (Stage) btnFilter.getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }
}
