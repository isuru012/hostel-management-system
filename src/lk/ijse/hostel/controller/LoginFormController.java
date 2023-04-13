package lk.ijse.hostel.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.hostel.dto.UserDTO;
import lk.ijse.hostel.service.BOFactory;
import lk.ijse.hostel.service.custom.UserService;
import lk.ijse.hostel.service.custom.impl.UserServiceImpl;

import java.io.IOException;

public class LoginFormController {

    public JFXTextField txtName;
    public JFXPasswordField txtPassword;
    public JFXCheckBox checkBoxPassword;
    public Button btnLogin;
    public static String Username;
    public static String Password;
    public static int userId;

    private final UserService userService = (UserService) BOFactory.getBOFactory().getBOTypes(BOFactory.BOTypes.USER);
    public AnchorPane root;
    public JFXTextField txtShowPassword;
    public Label lblError;

    public void loginButtonAction(ActionEvent actionEvent) throws IOException {
        try {
            String username = txtName.getText();
            String password = txtShowPassword.getText();

            UserDTO user = userService.getUserByUsernameAndPassword(username, password);
            userId=user.getId();

            if (user != null) {

                Parent  root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/hostel/view/MainForm.fxml"));

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
                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + user.getName());
                Username=username;
                Password=password;
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid Username or Password");
            }
        }catch (Exception e){
            lblError.setText("Invalid Username or Password");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void checkBoxShowPassword(MouseEvent mouseEvent) {
        boolean isSelected = checkBoxPassword.isSelected();
        if (isSelected) {
            txtPassword.setVisible(false);
            txtShowPassword.setVisible(true);
           /* txtPassword.setPromptText(txtPassword.getText());
            txtPassword.setText("");*/

        } else {
            txtPassword.setVisible(true);
            txtShowPassword.setVisible(false);
            /*txtPassword.setText(txtPassword.getPromptText());
            txtPassword.setPromptText("");*/
        }
txtPassword.setText(txtShowPassword.getText());
    }

    public void txtPassOnKeyReleased(KeyEvent keyEvent) {
        txtShowPassword.setText(txtPassword.getText());

    }
}
