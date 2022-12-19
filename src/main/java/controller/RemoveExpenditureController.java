package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

public class RemoveExpenditureController {
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
        addToExpenditureCombo();
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

    public void removeExpenditure(ActionEvent actionEvent) {
        if (categorySelected()) {
            A: for (Category c : fmis.getCategories()) {
                if (c.getName().equals(currentCategory)) {
                    for (Expenditure e: c.getExpenditure()) {
                        if (e.getExpenditure_ID() == Integer.parseInt(comboID.getValue().toString())) {
                            c.getExpenditure().remove(e);
                            comboID.getItems().remove(Integer.toString(e.getExpenditure_ID()));
                            mainAlert.setAlertType(Alert.AlertType.INFORMATION);
                            mainAlert.setHeaderText("Expenditure");
                            mainAlert.setContentText("Expenditure removed successfully");
                            mainAlert.showAndWait();
                            break A;
                        }
                    }
                }
            }
        } else {
            mainAlert.setAlertType(Alert.AlertType.ERROR);
            mainAlert.setHeaderText("Expenditure");
            mainAlert.setContentText("Select expenditure ID");
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

    private void addToExpenditureCombo()
    {
        for (Category c : fmis.getCategories()) {
            if(c.getName().equals(currentCategory))
            {
                for(Expenditure e: c.getExpenditure())
                {
                    comboID.getItems().add(e.getExpenditure_ID());
                }
                break;
            }
        }
    }


}
