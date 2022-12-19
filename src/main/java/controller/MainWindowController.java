package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.FinanceManagementSystem;
import model.User;

import java.io.IOException;

public class MainWindowController {
    public Button btnCat;
    public Button btnUser;
    public Button btnExport;
    public Button btnLogout;
    private FinanceManagementSystem fmis;
    private User currentUser;

    public void setCurrentUser(FinanceManagementSystem fmis, User user)
    {
        this.fmis = fmis;
        this.currentUser = user;
    }

    public void loadCategoryWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/CategoryWindow.fxml"));
        Parent root = loader.load();

        CategoryController categoryWindow = loader.getController();
        categoryWindow.setCurrentUser(fmis,currentUser);

        Stage stage = (Stage) btnCat.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void loadUserWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/UserWindow.fxml"));
        Parent root = loader.load();

        UserController userWindow = loader.getController();
        userWindow.setCurrentUser(fmis,currentUser);

        Stage stage = (Stage) btnUser.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void loadExportWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/ExportWindow.fxml"));
        Parent root = loader.load();

        ExportController exportWindow = loader.getController();
        exportWindow.setCurrentUser(fmis,currentUser);

        Stage stage = (Stage) btnExport.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void loginWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/LoginWindow.fxml"));
        Parent root = loader.load();

        LoginController loginWindow = loader.getController();
        loginWindow.setFinanceManagementSystem(fmis);

        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }
}
