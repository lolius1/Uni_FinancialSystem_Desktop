package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.FinanceManagementSystem;
import model.User;

import java.io.IOException;

public class RemoveFromCategoryController {
    public Button btnBack;
    public Button btnResp;
    public Button btnIncome;
    public Button btnExp;
    private FinanceManagementSystem fmis;
    Alert mainAlert = new Alert(Alert.AlertType.INFORMATION);
    private User currentUser;
    private String currentCategory;

    public void setCurrentCategory(FinanceManagementSystem fmis, User user, String name)
    {
        this.fmis = fmis;
        this.currentUser = user;
        this.currentCategory = name;
    }


    public void goToLastWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/CategoryWindow.fxml"));
        Parent root = loader.load();

        CategoryController categoryControllerWindow = loader.getController();
        categoryControllerWindow.setCurrentUser(fmis,currentUser);

        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void RemoveResponsiblePersonWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/RemoveResponsiblePersonWindow.fxml"));
        Parent root = loader.load();

        RemoveResponsiblePersonController removeResponsiblePersonWindow = loader.getController();
        removeResponsiblePersonWindow.setCurrentCategory(fmis,currentUser,currentCategory);

        Stage stage = (Stage) btnResp.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void RemoveIncomeWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/RemoveIncomeWindow.fxml"));
        Parent root = loader.load();

        RemoveIncomeController removeIncomeWindow = loader.getController();
        removeIncomeWindow.setCurrentCategory(fmis,currentUser,currentCategory);

        Stage stage = (Stage) btnIncome.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void RemoveExpenditureWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/RemoveExpenditureWindow.fxml"));
        Parent root = loader.load();

        RemoveExpenditureController removeExpenditureWindow = loader.getController();
        removeExpenditureWindow.setCurrentCategory(fmis,currentUser,currentCategory);

        Stage stage = (Stage) btnExp.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
