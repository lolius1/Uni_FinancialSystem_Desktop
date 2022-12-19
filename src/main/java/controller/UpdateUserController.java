package controller;

import hibernate.UserHibernate;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.FinanceManagementSystem;
import model.User;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class UpdateUserController {
    public TextField textLogin;
    public TextField textPsw;
    public ComboBox comboType;
    public Button btnBack;
    public Button btnUpd;
    public ComboBox comboID;
    private FinanceManagementSystem fmis;
    Alert mainAlert = new Alert(Alert.AlertType.INFORMATION);
    private User currentUser;
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("KursinioHibernate");
    UserHibernate userHibernateControl = new UserHibernate(factory);

    public void setCurrentUser(FinanceManagementSystem fmis, User user) {
        this.fmis = fmis;
        this.currentUser = user;
        comboType.getItems().add("admin");
        comboType.getItems().add("regular");
        addToIDCombo();
    }

    public void goToLastWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/UserWindow.fxml"));
        Parent root = loader.load();

        UserController userWindow = loader.getController();
        userWindow.setCurrentUser(fmis, currentUser);

        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void updateUser(ActionEvent actionEvent) {
        if(emptyFields())
        {
            for(User u: userHibernateControl.getUserList())
            {
                if(u.getUser_ID() == Integer.parseInt(comboID.getValue().toString()))
                {
                    u.setLogin(textLogin.getText());
                    u.setPsw(textPsw.getText());
                    u.setType(comboType.getValue().toString());
                    userHibernateControl.edit(u);
                    mainAlert.setAlertType(Alert.AlertType.INFORMATION);
                    mainAlert.setHeaderText("User");
                    mainAlert.setContentText("User updated successfully");
                    mainAlert.showAndWait();
                }
            }
        }
        else{
            mainAlert.setAlertType(Alert.AlertType.ERROR);
            mainAlert.setHeaderText("Fields");
            mainAlert.setContentText("Please fill in the fields");
            mainAlert.showAndWait();
        }
    }

    public void showUser(ActionEvent actionEvent) {
        for(User u: userHibernateControl.getUserList())
        {
            if(comboID.getValue().equals(Integer.toString(u.getUser_ID())))
            {
                textLogin.setText(u.getLogin());
                textPsw.setText(u.getPsw());
                comboType.setValue(u.getType());
                break;
            }
        }
    }

    private void addToIDCombo()
    {
        for (User u : userHibernateControl.getUserList()) {
            comboID.getItems().add(Integer.toString(u.getUser_ID()));
        }
    }

    private boolean emptyFields()
    {
        if(comboID.getSelectionModel().isEmpty() || textLogin.getText().isEmpty() || textPsw.getText().isEmpty() || comboType.getSelectionModel().isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
