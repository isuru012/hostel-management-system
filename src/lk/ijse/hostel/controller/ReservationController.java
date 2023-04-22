package lk.ijse.hostel.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.hostel.dto.ReservationDTO;
import lk.ijse.hostel.dto.RoomDTO;
import lk.ijse.hostel.dto.StudentDTO;
import lk.ijse.hostel.entity.Reservation;
import lk.ijse.hostel.service.BOFactory;
import lk.ijse.hostel.service.custom.ReservationService;
import lk.ijse.hostel.service.custom.RoomService;
import lk.ijse.hostel.service.custom.StudentService;
import lk.ijse.hostel.view.tm.ReservationTM;
import lk.ijse.hostel.view.tm.RoomManageTM;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

public class ReservationController {
    public AnchorPane root;
    public TableView tblReservation;
    public TableColumn colRes_Id;
    public TableColumn colStudent_Name;
    public TableColumn colDate;
    public TableColumn colStudent_Id;
    public TableColumn colRoomTypeId;
    public TableColumn colKeyMoney;
    public JFXTextField txtSearch;
    public JFXComboBox checkboxStudentid;
    public JFXComboBox checkboxRoomTypeId;
    public JFXRadioButton unpaidradio;
    public ToggleGroup keymoney;
    public JFXRadioButton paidradio;
    public Label lblStudentName;

private ReservationService reservationService= (ReservationService) BOFactory.getBOFactory().getBOTypes(BOFactory.BOTypes.RESERVATION);
private RoomService roomService= (RoomService) BOFactory.getBOFactory().getBOTypes(BOFactory.BOTypes.ROOM);
private StudentService studentService= (StudentService) BOFactory.getBOFactory().getBOTypes(BOFactory.BOTypes.STUDENT);
private  ReservationTM selectedReservation;


    public void initialize() {
        colRes_Id.setCellValueFactory(new PropertyValueFactory<>("res_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStudent_Id.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("key_money_status"));
        colStudent_Name.setCellValueFactory(new PropertyValueFactory<>("student_name"));
        colRoomTypeId.setCellValueFactory(new PropertyValueFactory<>("room_type_id"));

        loadAllReservations();
        loadAllStudentIds();
        loadAllRoomTypeIds();

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            loadStudentsOnSearch(newValue);
        });

    }

    private void loadStudentsOnSearch(String newValue) {
        ObservableList<ReservationTM> observableList=FXCollections.observableArrayList();
        try {
            List<ReservationDTO> allReservations = reservationService.getAllReservations();
            for (ReservationDTO reservation : allReservations) {
                ReservationTM reservationTM = new ReservationTM(reservation.getRes_id(),
                        reservation.getDate(),
                        reservation.getStudentDTO().getNic(),
                        reservation.getStudent_name(),
                        reservation.getRoomDTO().getRoom_type_id(),
                        reservation.getKey_money_status());

                if (reservation.getRes_id().contains(newValue) || reservation.getKey_money_status().contains(newValue)|| reservation.getStudent_name().contains(newValue)) {
                    observableList.add(reservationTM);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tblReservation.setItems(observableList);
    }

    private void loadAllRoomTypeIds() {
        try {
            List<String> roomTypeIds = reservationService.getAllRoomTypeIds();
            checkboxRoomTypeId.getItems().addAll(roomTypeIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllStudentIds() {
        try {
            List<String> studentIds = reservationService.getAllStudentIds();
            checkboxStudentid.getItems().addAll(studentIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllReservations() {
        try {
            ObservableList<ReservationTM> reservationTMS = FXCollections.observableArrayList();

            List<ReservationDTO> allReservations = reservationService.getAllReservations();
            tblReservation.getItems().clear();

            for (ReservationDTO reservation : allReservations) {
                ReservationTM reservationTM = new ReservationTM(reservation.getRes_id(),
                        reservation.getDate(),
                        reservation.getStudentDTO().getNic(),
                        reservation.getStudent_name(),
                        reservation.getRoomDTO().getRoom_type_id(),
                        reservation.getKey_money_status());
                reservationTMS.add(reservationTM);

            }
            tblReservation.setItems(reservationTMS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onMouseClicked(MouseEvent mouseEvent) {
        selectedReservation = (ReservationTM) tblReservation.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            checkboxStudentid.setValue(selectedReservation.getStudent_id());
            checkboxRoomTypeId.setValue(selectedReservation.getRoom_type_id());
            if (selectedReservation.getKey_money_status().equals("UNPAID")) {
//                paidradio.setSelected(true);
                unpaidradio.setSelected(true);
            } else {
                paidradio.setSelected(true);
//                unpaidradio.setSelected(true);
            }
            lblStudentName.setText(selectedReservation.getStudent_name());
        }
    }

    public void searchOnAction(KeyEvent keyEvent) {
        loadStudentsOnSearch(txtSearch.getText());
    }

    public void checkboxStudentidOnAction(ActionEvent actionEvent) {
        String selectedStudentId = (String) checkboxStudentid.getValue();

        if (selectedStudentId != null) {
            try {
                StudentDTO selectedStudent = reservationService.findStudent(selectedStudentId);

                if (selectedStudent != null) {
                    lblStudentName.setText(selectedStudent.getName());
                } else {
                    lblStudentName.setText("");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            lblStudentName.setText("");
        }
    }

    public void addOnAction(ActionEvent actionEvent) {
        try {
            String resId = reservationService.generateNewID();
            Date date = Date.valueOf(LocalDate.now());
            String studentId = (String) checkboxStudentid.getSelectionModel().getSelectedItem();
            String studentName = lblStudentName.getText();
            String roomTypeId = (String) checkboxRoomTypeId.getSelectionModel().getSelectedItem();
            String keyMoneyStatus = (paidradio.isSelected()) ? "PAID" : "UNPAID";

            StudentDTO studentDTO = studentService.getStudentById(studentId);
            RoomDTO roomDTO = roomService.getRoomById(roomTypeId);

            ReservationDTO reservation = new ReservationDTO();
            reservation.setRes_id(resId);
            reservation.setDate(date);
            reservation.setStudentDTO(studentDTO);
            reservation.setStudent_name(studentName);
            reservation.setRoomDTO(roomDTO);
            reservation.setKey_money_status(keyMoneyStatus);



            boolean saveReservation = reservationService.saveReservation(reservation);

            boolean decreaseQuantity = roomService.decreaseQuantity(roomTypeId);

            if(decreaseQuantity && saveReservation) {
                new Alert(Alert.AlertType.INFORMATION, "Reservation has been saved successfully").show();
                loadAllReservations();
                clearFields();
            }else{
                new Alert(Alert.AlertType.ERROR, "Reservation has not been saved successfully").show();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save the reservation").show();
        }
    }

    private void clearFields() {
        checkboxStudentid.getSelectionModel().clearSelection();
        checkboxRoomTypeId.getSelectionModel().clearSelection();
        lblStudentName.setText("");

    }

    public void updateOnAction(ActionEvent actionEvent) {

        try {
//            ReservationTM selectedReservation = (ReservationTM) tblReservation.getSelectionModel().getSelectedItem();

            ReservationDTO reservationDTO = new ReservationDTO();
            reservationDTO.setRes_id(selectedReservation.getRes_id());
            reservationDTO.setDate(selectedReservation.getDate());

            StudentDTO studentDTO = studentService.getStudentById((String) checkboxStudentid.getSelectionModel().getSelectedItem());
            RoomDTO roomDTO = roomService.getRoomById((String) checkboxRoomTypeId.getSelectionModel().getSelectedItem());


            reservationDTO.setStudentDTO(studentDTO);
            reservationDTO.setRoomDTO(roomDTO);
            reservationDTO.setStudent_name(lblStudentName.getText());

            reservationDTO.setKey_money_status(keymoney.getSelectedToggle().equals(paidradio) ? "PAID" : "UNPAID");


            if(!selectedReservation.getRoom_type_id().equals((String)checkboxRoomTypeId.getSelectionModel().getSelectedItem())){
                roomService.increaseQuantity(selectedReservation.getRoom_type_id());
                roomService.decreaseQuantity((String) checkboxRoomTypeId.getSelectionModel().getSelectedItem());
            }
            boolean updateReservation = reservationService.updateReservation(reservationDTO);

if (updateReservation) {
    new Alert(Alert.AlertType.INFORMATION, "Reservation updated successfully").show();
    loadAllReservations();
    clearFields();
}else{
    new Alert(Alert.AlertType.ERROR, "Failed to update the reservation").show();
}
        } catch (Exception e) {

            e.printStackTrace();
        }
    }



    public void deleteOnAction(ActionEvent actionEvent) {
        ReservationTM selectedReservation = (ReservationTM) tblReservation.getSelectionModel().getSelectedItem();

        if (selectedReservation == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a reservation to delete!").showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete the selected reservation?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
            try {
                ReservationDTO reservationDTO=new ReservationDTO();
                reservationDTO.setRes_id(selectedReservation.getRes_id());
                reservationDTO.setDate(selectedReservation.getDate());

                StudentDTO studentDTO = studentService.getStudentById(selectedReservation.getStudent_id());
                RoomDTO roomDTO = roomService.getRoomById(selectedReservation.getRoom_type_id());


                reservationDTO.setRoomDTO(roomDTO);
                reservationDTO.setStudentDTO(studentDTO);


                reservationDTO.setStudent_name(selectedReservation.getStudent_name());
                reservationDTO.setKey_money_status(selectedReservation.getKey_money_status());

                boolean deleteReservation = reservationService.deleteReservation(reservationDTO);

                boolean increase = roomService.increaseQuantity(selectedReservation.getRoom_type_id());

                if(increase && deleteReservation) {
                    new Alert(Alert.AlertType.INFORMATION, "Reservation has been Deleted successfully").show();
                    clearFields();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Reservation has not been Deleted successfully").show();

                }

                loadAllReservations();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete the reservation!").showAndWait();
                e.printStackTrace();
            }
        }
    }

    public void onMouseClickHouse(MouseEvent mouseEvent) throws IOException {
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

    public void getUnpaidOnAction(ActionEvent actionEvent) {
        try {
            ObservableList<ReservationTM> reservationTMS = FXCollections.observableArrayList();

            List<ReservationDTO> allReservations = reservationService.getUnpaidReservations();
            tblReservation.getItems().clear();

            for (ReservationDTO reservation : allReservations) {
                ReservationTM reservationTM = new ReservationTM(reservation.getRes_id(),
                        reservation.getDate(),
                        reservation.getStudentDTO().getNic(),
                        reservation.getStudent_name(),
                        reservation.getRoomDTO().getRoom_type_id(),
                        reservation.getKey_money_status());
                reservationTMS.add(reservationTM);

            }
            tblReservation.setItems(reservationTMS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onClose(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void refreshTable(MouseEvent mouseEvent) {
        loadAllReservations();
    }
}
