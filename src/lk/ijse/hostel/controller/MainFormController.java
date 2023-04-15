package lk.ijse.hostel.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainFormController {

    public AnchorPane root;

    public void manageStudentsClicked(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/hostel/view/StudentManage.fxml"));

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

    public void manageRoomsClicked(MouseEvent mouseEvent) throws IOException {
        Parent  root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/hostel/view/RoomManage.fxml"));

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

    public void viewStudentDetailsClicked(MouseEvent mouseEvent) throws IOException {
        Parent  root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/hostel/view/Reservation.fxml"));

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

    public void onmouseclickeduser(MouseEvent mouseEvent) throws IOException {

        if (!(LoginFormController.Username.equals("") && LoginFormController.Password.equals(""))) {

            Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/hostel/view/LoginDetailsEdit.fxml"));

            if (root != null) {
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.root.getScene().getWindow();
                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();

                /*TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();*/
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), root);
                root.setOpacity(0.6);

                // Set the final opacity to 1 (fully opaque)
                fadeTransition.setToValue(1);

                // Play the fade-in transition
                fadeTransition.play();

            }
        }else{
            showAlert(Alert.AlertType.ERROR, "Can't Go Without Login", "Invalid ");
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
