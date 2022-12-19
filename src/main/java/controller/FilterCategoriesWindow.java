package controller;

import hibernate.CategoryHibernate;
import hibernate.FinanceManagementSystemHibernate;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Category;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class FilterCategoriesWindow {
    public Button btnFilter;
    public ListView listViewCategories;
    public ComboBox comboParent;
    public DatePicker dateAfter;
    public Button btnBack;
    Alert mainAlert = new Alert(Alert.AlertType.INFORMATION);
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("KursinioHibernate");
    CategoryHibernate categoryHibernateControl = new CategoryHibernate(factory);
    private ArrayList<Category> categories = new ArrayList<Category>();

    public void initialize()
    {
        categories.clear();
        categories.addAll(categoryHibernateControl.getCategoryList());
        addToParentCombo();
        updateCategoriesListView(categories);
    }

    private void updateCategoriesListView(ArrayList<Category> category) {
        if (listViewCategories.getItems().size() > 0) listViewCategories.getItems().clear();

        category.forEach(c -> {
            String userinfo ="ID: " + c.getCategory_ID() + " Name: " +  c.getName() + " Description: " + c.getDescription();

            listViewCategories.getItems().add(userinfo);
        });

    }

    public void filterCategories(ActionEvent actionEvent) {
        if((comboParent.getSelectionModel().isEmpty() || comboParent.getValue().equals(""))&& dateAfter.getValue() == null)
        {
            mainAlert.setAlertType(Alert.AlertType.ERROR);
            mainAlert.setHeaderText("Parent");
            mainAlert.setContentText("Select a parent");
            mainAlert.showAndWait();
        }
        else if(comboParent.getSelectionModel().isEmpty() || comboParent.getValue().equals(""))
        {
            filterCategoriesByDate();
        }
        else if(dateAfter.getValue() == null)
        {
            filterCategoriesByParent();
        }
        else
        {
            filterCategoriesByDateAndParent();
        }
    }

    public void filterCategoriesByParent() {
        categories.clear();
        categories.addAll(categoryHibernateControl.getFilteredParentCategoryList(comboParent.getValue().toString()));
        updateCategoriesListView(categories);
    }

    public void filterCategoriesByDate() {
        categories.clear();
        categories.addAll(categoryHibernateControl.getFilteredDateCategoryList(dateAfter.getValue()));
        updateCategoriesListView(categories);
    }

    public void filterCategoriesByDateAndParent() {
        categories.clear();
        categories.addAll(categoryHibernateControl.getFilteredCategoryList(comboParent.getValue().toString(),dateAfter.getValue()));
        updateCategoriesListView(categories);
    }

    private void addToParentCombo()
    {
        comboParent.getItems().add("");
        for (Category c : categoryHibernateControl.getCategoryList()) {
            comboParent.getItems().add(c.getName());
        }
    }

    public void goToLastWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/CategoryWindow.fxml"));
        Parent root = loader.load();


        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
