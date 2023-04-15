package lk.ijse.hostel.controller;

import com.jfoenix.controls.*;
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
import lk.ijse.hostel.dto.RoomDTO;
import lk.ijse.hostel.dto.StudentDTO;
import lk.ijse.hostel.service.BOFactory;
import lk.ijse.hostel.service.custom.RoomService;
import lk.ijse.hostel.service.custom.StudentService;
import lk.ijse.hostel.view.tm.StudentManageTM;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentManageController {
    public AnchorPane root;
    public TableView tblStudent;
    public TableColumn colNIC;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colPhoneNumber;
    public TableColumn colRoomType;
    public TableColumn colKeyMoney;
    public TableColumn colCampusId;
    public TableColumn colRegisteredDate;
    public JFXTextField txtNic;
    public JFXTextField txtName;
    public JFXTextField txtSearch;
    public JFXTextField txtAddress;
    public JFXTextField txtPhoneNumber;
    public JFXComboBox cmbboxRoomType;
    public JFXCheckBox checkbxKeyMoney;
    public JFXTextField txtCampusId;

    private final StudentService studentBO = (StudentService) BOFactory.getBOFactory().getBOTypes(BOFactory.BOTypes.STUDENT);
    private final RoomService roomBO = (RoomService) BOFactory.getBOFactory().getBOTypes(BOFactory.BOTypes.ROOM);
    public ToggleGroup gender;
    public JFXDatePicker datepicker;
    public TableColumn colDOBDate;
    public TableColumn colGender;
    public JFXRadioButton maleRadioButton;
    public JFXRadioButton femaleRadioButton;

private StudentManageTM selectedStudent;

    public void initialize() {
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        colDOBDate.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        loadAllStudents();

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            loadStudentsOnSearch(newValue);
});

    }

    private void loadStudentsOnSearch(String newValue) {
        ObservableList<StudentManageTM> observableList=FXCollections.observableArrayList();
        try {
            List<StudentDTO> studentDTOS = studentBO.getAllStudents();
            for (StudentDTO studentDTO : studentDTOS) {
                StudentManageTM studentManageTM = new StudentManageTM(
                        studentDTO.getNic(),
                        studentDTO.getName(),
                        studentDTO.getAddress(),
                        studentDTO.getPhone_number(),
                        studentDTO.getDob(),
                        studentDTO.getGender()
                );
                if (String.valueOf(studentDTO.getPhone_number()).equals(newValue)||studentDTO.getGender().contains(newValue) || studentDTO.getAddress().contains(newValue)|| studentDTO.getName().contains(newValue)) {
                    observableList.add(studentManageTM);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tblStudent.setItems(observableList);

    }

    private void loadAllStudents() {
        ObservableList<StudentManageTM> studentManageTMS = FXCollections.observableArrayList();
        try {
            List<StudentDTO> studentDTOs = studentBO.getAllStudents();
            for (StudentDTO studentDTO : studentDTOs) {
                StudentManageTM studentManageTM = new StudentManageTM(
                        studentDTO.getNic(),
                        studentDTO.getName(),
                        studentDTO.getAddress(),
                        studentDTO.getPhone_number(),
                        studentDTO.getDob(),
                        studentDTO.getGender()
                );
                studentManageTMS.add(studentManageTM);
            }
            tblStudent.setItems(studentManageTMS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtNic.clear();
        txtName.clear();
        txtAddress.clear();
        txtPhoneNumber.clear();
        datepicker.setValue(null);

    }

    public void searchOnAction(KeyEvent keyEvent) {
       /* String searchText = txtSearch.getText();
        ObservableList<StudentManageTM> studentManageTMS = FXCollections.observableArrayList();
        try {
            List<StudentDTO> studentDTOs = studentBO.searchStudent(searchText);
            for (StudentDTO studentDTO : studentDTOs) {
                StudentManageTM studentManageTM = new StudentManageTM(
                        studentDTO.getNic(),
                        studentDTO.getName(),
                        studentDTO.getAddress(),
                        studentDTO.getPhone_number(),
                        studentDTO.getRoom_type(),
                        studentDTO.getKey_money(),
                        studentDTO.getCampus_id(),
                        studentDTO.getRegistered_date()
                );
                studentManageTMS.add(studentManageTM);
            }
            tblStudent.setItems(studentManageTMS);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        loadStudentsOnSearch(txtSearch.getText());

    }
    public void onMouseClicked(MouseEvent mouseEvent) {
        selectedStudent = (StudentManageTM) tblStudent.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            txtNic.setText(String.valueOf(selectedStudent.getNic()));
            txtName.setText(selectedStudent.getName());
            txtAddress.setText(selectedStudent.getAddress());
            txtPhoneNumber.setText(String.valueOf(selectedStudent.getPhone_number()));
            datepicker.setValue(selectedStudent.getDob());
            if(selectedStudent.getGender().equals("Male")){
                maleRadioButton.setSelected(true);
            }else if(selectedStudent.getGender().equals("Female")){
                femaleRadioButton.setSelected(true);
            }


        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void addOnAction(ActionEvent actionEvent) {

        String sid="^[a-zA-Z0-9_.-]*$";
        String name="^[A-Za-z. ]+$";
        String phoneNumber="^[0-9]+$";

        Pattern pattern=Pattern.compile(sid);
        Matcher matcher=pattern.matcher(txtNic.getText());

        Pattern pattern1=Pattern.compile(name);
        Matcher matcher1=pattern1.matcher(txtName.getText());
        Pattern pattern2=Pattern.compile(phoneNumber);
        Matcher matcher2=pattern2.matcher(txtPhoneNumber.getText());

        if(!matcher.matches()){
            showAlert(Alert.AlertType.ERROR, "Student Id Incorrect Type", "Check Table Student Id How it is added");
        } if(!matcher1.matches()){
            showAlert(Alert.AlertType.ERROR, "Student Name Incorrect Type", "Check Table Student Name How it is added"
            );
        }if(!matcher2.matches()){
            showAlert(Alert.AlertType.ERROR, "PhoneNumber Incorrect Type", "Check Table phoneNumber How it is added"
            );
        }
        // Validating user inputs
        if (matcher.matches() && matcher1.matches() && matcher2.matches()) {
            if (txtNic.getText().isEmpty() || txtName.getText().isEmpty() || txtAddress.getText().isEmpty()
                    || txtPhoneNumber.getText().isEmpty() || datepicker.getValue().toString() == null
            ) {
                new Alert(Alert.AlertType.ERROR, "Please fill all the fields to continue").showAndWait();
                return;
            }

            RadioButton radioButton = (RadioButton) gender.getSelectedToggle();

            // Creating a new StudentDTO object
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setNic(txtNic.getText());
            studentDTO.setName(txtName.getText());
            studentDTO.setAddress(txtAddress.getText());
            studentDTO.setPhone_number(Integer.parseInt(txtPhoneNumber.getText()));
            studentDTO.setDob(datepicker.getValue());
            studentDTO.setGender(radioButton.getText());


            // Calling the saveStudent method in the StudentBO to save the new student to the database
            try {
                boolean result = studentBO.saveStudent(studentDTO);
                if (result) {
                    new Alert(Alert.AlertType.INFORMATION, "Student added successfully").showAndWait();
                    clearFields();
                    loadAllStudents();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to add the student").showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "An error occurred while saving the student").showAndWait();
            }
        }
    }
    public void updateOnAction(ActionEvent actionEvent) {

        String sid="^[a-zA-Z0-9_.-]*$";
        String name="^[A-Za-z. ]+$";
        String phoneNumber="^[0-9]+$";

        Pattern pattern=Pattern.compile(sid);
        Matcher matcher=pattern.matcher(txtNic.getText());

        Pattern pattern1=Pattern.compile(name);
        Matcher matcher1=pattern1.matcher(txtName.getText());
        Pattern pattern2=Pattern.compile(phoneNumber);
        Matcher matcher2=pattern2.matcher(txtPhoneNumber.getText());

        if(!matcher.matches()){
            showAlert(Alert.AlertType.ERROR, "Student Id Incorrect Type", "Check Table Student Id How it is added");
        } if(!matcher1.matches()){
            showAlert(Alert.AlertType.ERROR, "Student Name Incorrect Type", "Check Table Student Name How it is added"
            );
        }if(!matcher2.matches()){
            showAlert(Alert.AlertType.ERROR, "PhoneNumber Incorrect Type", "Check Table phoneNumber How it is added"
            );
        }
        if (matcher.matches() && matcher1.matches() && matcher2.matches()) {
           selectedStudent = (StudentManageTM) tblStudent.getSelectionModel().getSelectedItem();
            if (selectedStudent == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a student to update").showAndWait();
                return;
            }

            if (txtNic.getText().isEmpty() || txtName.getText().isEmpty() || txtAddress.getText().isEmpty()
                    || txtPhoneNumber.getText().isEmpty() || datepicker.getValue().toString() == null
            ) {
                new Alert(Alert.AlertType.ERROR, "Please fill all the fields to continue").showAndWait();
                return;
            }
            RadioButton radioButton = (RadioButton) gender.getSelectedToggle();

            StudentDTO studentDTO = new StudentDTO(
                    txtNic.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Integer.parseInt(txtPhoneNumber.getText()),
                    datepicker.getValue(),
                    radioButton.getText()
            );

            try {
                boolean result = studentBO.updateStudent(studentDTO);
                if (result) {
                    new Alert(Alert.AlertType.INFORMATION, "Student updated successfully").showAndWait();
                    loadAllStudents();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update the student").showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            clearFields();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String sid="^[a-zA-Z0-9_.-]*$";
        String name="^[A-Za-z. ]+$";
        String phoneNumber="^[0-9]+$";

        Pattern pattern=Pattern.compile(sid);
        Matcher matcher=pattern.matcher(txtNic.getText());

        Pattern pattern1=Pattern.compile(name);
        Matcher matcher1=pattern1.matcher(txtName.getText());
        Pattern pattern2=Pattern.compile(phoneNumber);
        Matcher matcher2=pattern2.matcher(txtPhoneNumber.getText());

        if(!matcher.matches()){
            showAlert(Alert.AlertType.ERROR, "Student Id Incorrect Type", "Check Table Student Id How it is added");
        } if(!matcher1.matches()){
            showAlert(Alert.AlertType.ERROR, "Student Name Incorrect Type", "Check Table Student Name How it is added"
            );
        }if(!matcher2.matches()){
            showAlert(Alert.AlertType.ERROR, "PhoneNumber Incorrect Type", "Check Table phoneNumber How it is added"
            );
        }
        if (matcher.matches() && matcher1.matches() && matcher2.matches()) {
            selectedStudent = (StudentManageTM) tblStudent.getSelectionModel().getSelectedItem();
            if (selectedStudent == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a student to delete").showAndWait();
                return;
            }

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected student?");
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    StudentDTO studentDTO = new StudentDTO(selectedStudent.getNic(),
                            selectedStudent.getName(),
                            selectedStudent.getAddress(),
                            selectedStudent.getPhone_number(),
                            selectedStudent.getDob(),
                            selectedStudent.getGender()
                    );
                    boolean isDeleted = studentBO.deleteStudent(studentDTO);
                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Student deleted successfully").showAndWait();
                        loadAllStudents();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete the student").showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

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
