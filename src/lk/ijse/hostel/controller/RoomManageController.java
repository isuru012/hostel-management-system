package lk.ijse.hostel.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.hostel.dto.RoomDTO;
import lk.ijse.hostel.entity.Room;
import lk.ijse.hostel.service.BOFactory;
import lk.ijse.hostel.service.custom.RoomService;
import lk.ijse.hostel.view.tm.RoomManageTM;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoomManageController {
    public AnchorPane root;
    public TableView tblRoom;
    public TableColumn colRoomId;
    public TableColumn colRoomType;
    public TableColumn colKeyMoney;
    public TableColumn colRoomsQTY;
    public JFXTextField txtRoomID;
    public JFXTextField txtRoomType;
    public JFXTextField txtKeyMoney;
    public JFXTextField txtRoomsQTY;
    public Button btnAdd;
    public Button btnUpdate;
    public Button btnDelete;

    private final RoomService roomService = (RoomService) BOFactory.getBOFactory().getBOTypes(BOFactory.BOTypes.ROOM);
private RoomManageTM selectedRoom;
    public void initialize() {
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("room_type"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("key_money"));
        colRoomsQTY.setCellValueFactory(new PropertyValueFactory<>("room_quantity"));

        loadAllRooms();

        tblRoom.setOnMouseClicked(this::onMouseClickedTable);
    }

    private void loadAllRooms() {
        ObservableList<RoomManageTM> roomManageTMS = FXCollections.observableArrayList();
        try {
            List<RoomDTO> roomDTOs = roomService.getAllRooms();
            for (RoomDTO roomDTO : roomDTOs) {
                RoomManageTM roomManageTM = new RoomManageTM(
                        roomDTO.getRoom_type_id(),
                        roomDTO.getRoom_type(),
                        roomDTO.getKey_money(),
                        roomDTO.getRoom_quantity()
                );
                roomManageTMS.add(roomManageTM);
            }
            tblRoom.setItems(roomManageTMS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void addOnAction(ActionEvent mouseEvent) {
        String RoomID="^[a-zA-Z0-9_.-]*$";
        String keymoney="^[1-9]\\d*([\\,\\.]\\d{2})?$";
        String qty="^[0-9]*$";

        Pattern pattern=Pattern.compile(RoomID);
        Matcher matcher=pattern.matcher(txtRoomID.getText());

        Pattern pattern1=Pattern.compile(keymoney);
        Matcher matcher1=pattern1.matcher(txtKeyMoney.getText());
        Pattern pattern2=Pattern.compile(qty);
        Matcher matcher2=pattern2.matcher(txtRoomsQTY.getText());

        if(!matcher.matches()){
            showAlert(Alert.AlertType.ERROR, "RoomID Incorrect Type", "Check Table Room Id How it is added");
        } if(!matcher1.matches()){
            showAlert(Alert.AlertType.ERROR, "Keymoney Incorrect Type", "Check Table Keymoney How it is added"
                    );
        }if(!matcher2.matches()){
            showAlert(Alert.AlertType.ERROR, "Quantity Incorrect Type", "Check Table Quantity How it is added"
                  );
        }

        if (matcher.matches() && matcher1.matches() && matcher2.matches()) {
            if (txtRoomType.getText().isEmpty() || txtKeyMoney.getText().isEmpty() || txtRoomsQTY.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please fill all the fields to continue").showAndWait();
                return;
            }

            RoomDTO roomDTO = new RoomDTO(txtRoomID.getText(),
                    txtRoomType.getText(),
                    Double.parseDouble(txtKeyMoney.getText()),
                    Integer.parseInt(txtRoomsQTY.getText())
            );
            try {
                boolean result = roomService.saveRoom(roomDTO);
                if (result) {
                    new Alert(Alert.AlertType.INFORMATION, "Room added successfully").showAndWait();
                    loadAllRooms();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to add the room").showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            clearFields();
        }
    }

    public void updateOnAction(ActionEvent mouseEvent) {
        String RoomID="^[a-zA-Z0-9_.-]*$";
        String keymoney="^[1-9]\\d*([\\,\\.]\\d{2})?$";
        String qty="^[0-9]*$";

        Pattern pattern=Pattern.compile(RoomID);
        Matcher matcher=pattern.matcher(txtRoomID.getText());

        Pattern pattern1=Pattern.compile(keymoney);
        Matcher matcher1=pattern1.matcher(txtKeyMoney.getText());
        Pattern pattern2=Pattern.compile(qty);
        Matcher matcher2=pattern2.matcher(txtRoomsQTY.getText());

        if(!matcher.matches()){
            showAlert(Alert.AlertType.ERROR, "RoomID Incorrect Type", "Check Table Room Id How it is added");
        } if(!matcher1.matches()){
            showAlert(Alert.AlertType.ERROR, "Keymoney Incorrect Type", "Check Table Keymoney How it is added"
            );
        }if(!matcher2.matches()){
            showAlert(Alert.AlertType.ERROR, "Quantity Incorrect Type", "Check Table Quantity How it is added"
            );
        }

        if (matcher.matches() && matcher1.matches() && matcher2.matches()) {

            selectedRoom = (RoomManageTM) tblRoom.getSelectionModel().getSelectedItem();
            if (selectedRoom == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a room to update").showAndWait();
                return;
            }

            if (txtRoomType.getText().isEmpty() || txtKeyMoney.getText().isEmpty() || txtRoomsQTY.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please fill all the fields to continue").showAndWait();
                return;
            }

            RoomDTO roomDTO = new RoomDTO(
                    selectedRoom.getRoom_type_id(),
                    txtRoomType.getText(),
                    Double.parseDouble(txtKeyMoney.getText()),
                    Integer.parseInt(txtRoomsQTY.getText())
            );

            try {
                boolean result = roomService.updateRoom(roomDTO);
                if (result) {
                    new Alert(Alert.AlertType.INFORMATION, "Room updated successfully").showAndWait();
                    loadAllRooms();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update the room").showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            clearFields();
        }
    }

    public void deleteOnAction(ActionEvent mouseEvent) {
        String RoomID="^[a-zA-Z0-9_.-]*$";
        String keymoney="^[1-9]\\d*([\\,\\.]\\d{2})?$";
        String qty="^[0-9]*$";

        Pattern pattern=Pattern.compile(RoomID);
        Matcher matcher=pattern.matcher(txtRoomID.getText());

        Pattern pattern1=Pattern.compile(keymoney);
        Matcher matcher1=pattern1.matcher(txtKeyMoney.getText());
        Pattern pattern2=Pattern.compile(qty);
        Matcher matcher2=pattern2.matcher(txtRoomsQTY.getText());

        if(!matcher.matches()){
            showAlert(Alert.AlertType.ERROR, "RoomID Incorrect Type", "Check Table Room Id How it is added");
        } if(!matcher1.matches()){
            showAlert(Alert.AlertType.ERROR, "Keymoney Incorrect Type", "Check Table Keymoney How it is added"
            );
        }if(!matcher2.matches()){
            showAlert(Alert.AlertType.ERROR, "Quantity Incorrect Type", "Check Table Quantity How it is added"
            );
        }

        if (matcher.matches() && matcher1.matches() && matcher2.matches()) {
            selectedRoom = (RoomManageTM) tblRoom.getSelectionModel().getSelectedItem();
            if (selectedRoom == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a room to delete").showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected room?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                try {
                    Room room = new Room();
                    room.setRoom_type_id(selectedRoom.getRoom_type_id());
                    room.setRoom_type(selectedRoom.getRoom_type());
                    room.setRoom_quantity(selectedRoom.getRoom_quantity());
                    room.setKey_money(selectedRoom.getKey_money());
                    boolean res = roomService.deleteRoom(room);
                    if (res) {
                        new Alert(Alert.AlertType.INFORMATION, "Room deleted successfully").showAndWait();
                        loadAllRooms();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete the room").showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            clearFields();
        }
    }

    private void clearFields() {
        txtRoomID.clear();
        txtRoomType.clear();
        txtKeyMoney.clear();
        txtRoomsQTY.clear();
    }

    public void onMouseClickedTable(MouseEvent mouseEvent) {
        if (tblRoom.getSelectionModel().getSelectedItem() != null) {
           selectedRoom = (RoomManageTM) tblRoom.getSelectionModel().getSelectedItem();
            txtRoomID.setText(selectedRoom.getRoom_type_id());
            txtRoomID.setDisable(true);
            txtRoomType.setText(selectedRoom.getRoom_type());
            txtKeyMoney.setText(Double.toString(selectedRoom.getKey_money()));
            txtRoomsQTY.setText(Integer.toString(selectedRoom.getRoom_quantity()));
        }
    }

    public void onmouseclickedhome(MouseEvent mouseEvent) throws IOException {
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
}