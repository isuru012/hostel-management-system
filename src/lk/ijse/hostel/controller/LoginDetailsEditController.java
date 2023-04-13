package lk.ijse.hostel.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.log.Log;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.hostel.dto.UserDTO;
import lk.ijse.hostel.service.BOFactory;
import lk.ijse.hostel.service.custom.UserService;

import java.io.IOException;

public class LoginDetailsEditController {

    public AnchorPane root;
    public JFXTextField txtName;
    public JFXPasswordField txtPassword;
    public JFXCheckBox checkBoxPassword;

    private final UserService userService = (UserService) BOFactory.getBOFactory().getBOTypes(BOFactory.BOTypes.USER);
    public JFXTextField txtShowPassword;

    public void initialize() {
        txtName.setText(LoginFormController.Username);
        txtPassword.setText(LoginFormController.Password);
        txtShowPassword.setText(LoginFormController.Password);
    }

    public void checkBoxShowPassword(MouseEvent mouseEvent) {
        boolean isSelected = checkBoxPassword.isSelected();
        if (isSelected) {
            txtPassword.setVisible(false);
            txtShowPassword.setVisible(true);


        } else {
            txtPassword.setVisible(true);
            txtShowPassword.setVisible(false);

        }
        txtPassword.setText(txtShowPassword.getText());
    }

    public void onClickedUpdateButton(ActionEvent actionEvent) {
        boolean user = userService.updateUser(LoginFormController.Username, LoginFormController.Password,
                txtName.getText(),txtShowPassword.getText());

        if(user){
            LoginFormController.Username=txtName.getText();
            LoginFormController.Password=txtShowPassword.getText();

            showAlert(Alert.AlertType.INFORMATION, "Updated credentials", "Success ");
        }

    }

    public void onMouseClickedHome(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/hostel/view/MainForm.fxml"));

        if (root != null) {
            Scene subScene = new Scene(root);
            Stage primaryStage = (Stage) this.root.getScene().getWindow();
            primaryStage.setScene(subScene);
            primaryStage.centerOnScreen();

            TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
            tt.setFromX(-subScene.getWidth());
            tt.setToX(0);
            tt.play();

        }

    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void txtPassOnKeyReleased(KeyEvent keyEvent) {
        txtShowPassword.setText(txtPassword.getText());
    }
}
