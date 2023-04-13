package lk.ijse.hostel.view.tm;

import java.util.Date;

public class ReservationTM {
    String res_id;
    Date date;
    String student_id;
    String student_name;
    String room_type_id;
    String key_money_status;

    public ReservationTM() {
    }

    public ReservationTM(String res_id, Date date, String student_id, String student_name, String room_type_id, String key_money_status) {
        this.res_id = res_id;
        this.date = date;
        this.student_id = student_id;
        this.student_name = student_name;
        this.room_type_id = room_type_id;
        this.key_money_status = key_money_status;
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

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getRoom_type_id() {
        return room_type_id;
    }

    public void setRoom_type_id(String room_type_id) {
        this.room_type_id = room_type_id;
    }

    public String getKey_money_status() {
        return key_money_status;
    }

    public void setKey_money_status(String key_money_status) {
        this.key_money_status = key_money_status;
    }
}

