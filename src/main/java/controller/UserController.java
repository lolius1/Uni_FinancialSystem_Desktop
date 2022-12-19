package controller;

import hibernate.FinanceManagementSystemHibernate;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.FinanceManagementSystem;
import model.User;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class UserController {
    public Button btnAdd;
    public Button btnBack;
    public Button btnDel;
    public Button btnUpd;
    public Button btnShow;
    public Button btnFilter;
    private FinanceManagementSystem fmis;
    Alert mainAlert = new Alert(Alert.AlertType.INFORMATION);
    private User currentUser;
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("KursinioHibernate");
    FinanceManagementSystemHibernate financeManagementSystemHibernateControl =
            new FinanceManagementSystemHibernate(factory);

    public void initialize()
    {
        for(User u: financeManagementSystemHibernateControl.findFinanceManagementSystem("Amazing").getSystemUsers())
        {
        currentUser = u;
        }
    }

    public void setCurrentUser(FinanceManagementSystem fmis, User user) {
        this.fmis = fmis;
        this.currentUser = user;
    }

    public void addUserWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/AddUserWindow.fxml"));
        Parent root = loader.load();

        AddUserController addUserWindow = loader.getController();
        addUserWindow.setCurrentUser(fmis, currentUser);

        Stage stage = (Stage) btnAdd.getScene().getWindow();
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

    public void deleteUserWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/DeleteUserWindow.fxml"));
        Parent root = loader.load();

        DeleteUserController deleteUserWindow = loader.getController();
        deleteUserWindow.setCurrentUser(fmis, currentUser);

        Stage stage = (Stage) btnDel.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void updateUserWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/UpdateUserWindow.fxml"));
        Parent root = loader.load();

        UpdateUserController updateUserWindow = loader.getController();
        updateUserWindow.setCurrentUser(fmis, currentUser);

        Stage stage = (Stage) btnUpd.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void showUsersWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/ShowUserWindow.fxml"));
        Parent root = loader.load();

        ShowUserController showUserWindow = loader.getController();
        showUserWindow.setCurrentUser(fmis, currentUser);

        Stage stage = (Stage) btnShow.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void loadFilterUsers(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/FilterUsersWindow.fxml"));
        Parent root = loader.load();


        Stage stage = (Stage) btnFilter.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
