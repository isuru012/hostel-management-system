package lk.ijse.hostel.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.log.Log;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.hostel.dto.UserDTO;
import lk.ijse.hostel.service.BOFactory;
import lk.ijse.hostel.service.custom.UserService;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String userNameRegex="^[A-Za-z][A-Za-z0-9_]{7,29}$";
        String passwordRegex="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        Pattern pattern = Pattern.compile(userNameRegex);
        Matcher matcher = pattern.matcher( txtName.getText());

        Pattern pattern1=Pattern.compile(passwordRegex);
        Matcher matcher1=pattern1.matcher(txtShowPassword.getText());

        if(!matcher.matches()){
            showAlert(Alert.AlertType.ERROR, "Username Incorrect", "Username need to 8-20 characters long");
        } if(!matcher1.matches()){
            showAlert(Alert.AlertType.ERROR, "Password Incorrect", "Minimum eight characters, at least one uppercase letter, " +
                    "one lowercase letter, one number and one special character");
        }


        if(matcher.matches() && matcher1.matches()) {
            boolean user = userService.updateUser(LoginFormController.Username, LoginFormController.Password,
                    txtName.getText(), txtShowPassword.getText());

            if (user) {
                LoginFormController.Username = txtName.getText();
                LoginFormController.Password = txtShowPassword.getText();

                showAlert(Alert.AlertType.INFORMATION, "Updated credentials", "Success ");
            }
        }

    }

    public void onMouseClickedHome(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/hostel/view/MainForm.fxml"));

        if (root != null) {
            Scene subScene = new Scene(root);
            Stage primaryStage = (Stage) this.root.getScene().getWindow();
            primaryStage.setScene(subScene);
            primaryStage.centerOnScreen();

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), root);
            root.setOpacity(0.6);

            // Set the final opacity to 1 (fully opaque)
            fadeTransition.setToValue(1);

            // Play the fade-in transition
            fadeTransition.play();

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
        if (keyEvent.getCode()== KeyCode.ENTER){
            String userNameRegex="^[A-Za-z][A-Za-z0-9_]{7,29}$";
            String passwordRegex="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
            Pattern pattern = Pattern.compile(userNameRegex);
            Matcher matcher = pattern.matcher( txtName.getText());

            Pattern pattern1=Pattern.compile(passwordRegex);
            Matcher matcher1=pattern1.matcher(txtShowPassword.getText());

            if(!matcher.matches()){
                showAlert(Alert.AlertType.ERROR, "Username Incorrect", "Username need to 8-20 characters long");
            } if(!matcher1.matches()){
                showAlert(Alert.AlertType.ERROR, "Password Incorrect", "Minimum eight characters, at least one uppercase letter, " +
                        "one lowercase letter, one number and one special character");
            }


            if(matcher.matches() && matcher1.matches()) {
                boolean user = userService.updateUser(LoginFormController.Username, LoginFormController.Password,
                        txtName.getText(), txtShowPassword.getText());

                if (user) {
                    LoginFormController.Username = txtName.getText();
                    LoginFormController.Password = txtShowPassword.getText();

                    showAlert(Alert.AlertType.INFORMATION, "Updated credentials", "Success ");
                }
            }
        }
    }
}
