package controller;

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
import model.Income;
import model.User;

import java.io.IOException;

public class RemoveIncomeController {
    public Button btnBack;
    public ComboBox comboID;
    public Button btnRemove;
    private FinanceManagementSystem fmis;
    Alert mainAlert = new Alert(Alert.AlertType.INFORMATION);
    private User currentUser;
    private String currentCategory;

    public void setCurrentCategory(FinanceManagementSystem fmis, User user, String name) {
        this.fmis = fmis;
        this.currentUser = user;
        this.currentCategory = name;
        addToIncomeCombo();
    }

    public void goToLastWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/fxmlPackage/RemoveFromCategoryWindow.fxml"));
        Parent root = loader.load();

        RemoveFromCategoryController removeFromCategoryWindow = loader.getController();
        removeFromCategoryWindow.setCurrentCategory(fmis, currentUser, currentCategory);

        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void removeIncome(ActionEvent actionEvent) {
        if (categorySelected()) {
            A: for (Category c : fmis.getCategories()) {
                if (c.getName().equals(currentCategory)) {
                    for (Income i: c.getIncome()) {
                        if (i.getIncome_ID() == Integer.parseInt(comboID.getValue().toString())) {
                            c.getIncome().remove(i);
                            comboID.getItems().remove(Integer.toString(i.getIncome_ID()));
                            mainAlert.setAlertType(Alert.AlertType.INFORMATION);
                            mainAlert.setHeaderText("Income");
                            mainAlert.setContentText("Income removed successfully");
                            mainAlert.showAndWait();
                            break A;
                        }
                    }
                }
            }
        } else {
            mainAlert.setAlertType(Alert.AlertType.ERROR);
            mainAlert.setHeaderText("Income");
            mainAlert.setContentText("Select income ID");
            mainAlert.showAndWait();
        }
    }

    private boolean categorySelected()
    {
        if(comboID.getSelectionModel().isEmpty())
        {
            return false;
        }
        else{
            return true;
        }
    }

    private void addToIncomeCombo()
    {
        for (Category c : fmis.getCategories()) {
            if(c.getName().equals(currentCategory))
            {
                for(Income i: c.getIncome())
                {
                    comboID.getItems().add(Integer.toString(i.getIncome_ID()));
                }
                break;
            }
        }
    }


}
