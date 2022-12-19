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

public class ShowUserController {
    public Button btnBack;
    public ComboBox comboID;
    public TextField textLogin;
    public TextField textName;
    public TextField textSurname;
    public TextField textType;
    private FinanceManagementSystem fmis;
    private User currentUser;
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("KursinioHibernate");
    UserHibernate userHibernateControl = new UserHibernate(factory);

    public void setCurrentUser(model.FinanceManagementSystem fmis, User user) {
        this.fmis = fmis;
        this.currentUser = user;
        addToIDCombo();
    }

    public void showUser(ActionEvent actionEvent) {
        for(User u: userHibernateControl.getUserList())
        {
            if(comboID.getValue().equals(Integer.toString(u.getUser_ID())))
            {
                textLogin.setText(u.getLogin());
                textName.setText(u.getName());
                textSurname.setText(u.getSurname());
                textType.setText(u.getType());
                break;
            }
        }
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

    private void addToIDCombo()
    {
        for (User u : userHibernateControl.getUserList()) {
                comboID.getItems().add(Integer.toString(u.getUser_ID()));
        }
    }
}
