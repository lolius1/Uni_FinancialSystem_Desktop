package controller;

import hibernate.CategoryHibernate;
import hibernate.ExpenditureHibernate;
import hibernate.FinanceManagementSystemHibernate;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class AddExpenditureController {
    public TextField textID;
    public TextField textName;
    public TextField textDesc;
    public TextField textAmount;
    public Button btnBack;
    public Button btnAdd;
    public DatePicker dateExp;
    private FinanceManagementSystem fmis;
    Alert mainAlert = new Alert(Alert.AlertType.INFORMATION);
    private User currentUser;
    private String currentCategory;
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("KursinioHibernate");
    CategoryHibernate categoryHibernateControl = new CategoryHibernate(factory);
    ExpenditureHibernate expenditureHibernateControl = new ExpenditureHibernate(factory);


    public void setCurrentCategory(FinanceManagementSystem fmis, User user, String name) {
        this.fmis = fmis;
        this.currentUser = user;
        this.currentCategory = name;
    }

    public void goToLastWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/fxmlPackage/AddToCategoryWindow.fxml"));
        Parent root = loader.load();

        AddToCategoryController addToCategoryWindow = loader.getController();
        addToCategoryWindow.setCurrentCategory(fmis, currentUser, currentCategory);

        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void addExpenditure(ActionEvent actionEvent) {
        if (emptyFields()) {
            for (Category c : categoryHibernateControl.getCategoryList()) {
                if (c.getName().equals(currentCategory)) {
                    Expenditure expenditure =
                                    new Expenditure(
                                            textName.getText(),
                                            textDesc.getText(),
                                            Integer.parseInt(textAmount.getText()),
                                            dateExp.getValue(),
                                            c);
                    expenditureHibernateControl.create(expenditure);
                    mainAlert.setAlertType(Alert.AlertType.INFORMATION);
                    mainAlert.setHeaderText("Expenditure");
                    mainAlert.setContentText("Expenditure added successfully");
                    mainAlert.showAndWait();
                    break;
                }
            }
        } else {
            mainAlert.setAlertType(Alert.AlertType.ERROR);
            mainAlert.setHeaderText("Fields");
            mainAlert.setContentText("Please fill in the empty fields");
            mainAlert.showAndWait();
        }
    }

    private boolean emptyFields() {
        if (textName.getText().isEmpty()
                || textDesc.getText().isEmpty()
                || textAmount.getText().isEmpty()
                || dateExp.getValue() == null) {
            return false;
        } else {
            return true;
        }
    }
}
