package controller;

import hibernate.CategoryHibernate;
import hibernate.FinanceManagementSystemHibernate;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class ShowCategoriesController {
  public ComboBox comboCat;
  public Button btnBack;
  public TextField textID;
  public TextArea textboxDesc;
  public ListView listUsers;
  public ListView listIncome;
  public ListView listExpenditure;
  public ListView listSubcat;
  public TextField textParent;
  private FinanceManagementSystem fmis;
  Alert mainAlert = new Alert(Alert.AlertType.INFORMATION);
  private User currentUser;
  EntityManagerFactory factory = Persistence.createEntityManagerFactory("KursinioHibernate");
  FinanceManagementSystemHibernate financeManagementSystemHibernateControl = new FinanceManagementSystemHibernate(factory);
  CategoryHibernate categoryHibernateControl = new CategoryHibernate(factory);

  public void setCurrentUser(FinanceManagementSystem fmis, User user) {
    this.fmis = fmis;
    this.currentUser = user;
    addToCategoryCombo();
  }

  public void showCategory(ActionEvent actionEvent) {
    listUsers.getItems().clear();
    listIncome.getItems().clear();
    listExpenditure.getItems().clear();
    listSubcat.getItems().clear();
    for (Category c : categoryHibernateControl.getCategoryList()) {
      if (c.getName().equals(comboCat.getValue().toString())) {
        textID.setText(Integer.toString(c.getCategory_ID()));
        textboxDesc.setText(c.getDescription());
        textParent.setText(c.getParent());
        for (User u : c.getResponsibleUsers()) {
            listUsers.getItems().add(u);
        }
        for(Income i: c.getIncome())
        {
          listIncome.getItems().add(i);
        }
        for(Expenditure e: c.getExpenditure())
        {
          listExpenditure.getItems().add(e);
        }
        for(Category s: c.getSubCategories())
        {
          listSubcat.getItems().add(s);
        }
        break;
      }
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

  private void addToCategoryCombo() {
    for (Category c : categoryHibernateControl.getCategoryList()) {
      if (!comboCat.getItems().contains(c.getName())) comboCat.getItems().add(c.getName());
    }
  }
}
