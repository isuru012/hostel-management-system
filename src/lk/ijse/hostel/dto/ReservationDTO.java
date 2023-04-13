package lk.ijse.hostel.dto;

import java.util.Date;

public class ReservationDTO {
    private String res_id;
    private Date date;
    private String student_name;
    private String key_money_status;
    private StudentDTO studentDTO;
    private RoomDTO roomDTO;

    public ReservationDTO() {
    }

    public ReservationDTO(String res_id, Date date, String student_name, String key_money_status, StudentDTO studentDTO, RoomDTO roomDTO) {
        this.res_id = res_id;
        this.date = date;
        this.student_name = student_name;
        this.key_money_status = key_money_status;
        this.studentDTO = studentDTO;
        this.roomDTO = roomDTO;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }



    public String getKey_money_status() {
        return key_money_status;
    }

    public void setKey_money_status(String key_money_status) {
        this.key_money_status = key_money_status;
    }

    public StudentDTO getStudentDTO() {
        return studentDTO;
    }

    public void setStudentDTO(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }

    public RoomDTO getRoomDTO() {
        return roomDTO;
    }

    public void setRoomDTO(RoomDTO roomDTO) {
        this.roomDTO = roomDTO;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "res_id='" + res_id + '\'' +
                ", date=" + date +
                ", student_name='" + student_name + '\'' +
                ", key_money_status='" + key_money_status + '\'' +
                ", studentDTO=" + studentDTO +
                ", roomDTO=" + roomDTO +
                '}';
    }
}

