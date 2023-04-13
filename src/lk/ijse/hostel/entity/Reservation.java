package lk.ijse.hostel.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    private
    String res_id;
    private Date date;
    private String student_name;
    private String key_money_status;

    @ManyToOne
    @JoinColumn(name = "nic")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private Room room;


    public Reservation() {
    }

    public Reservation(String res_id, Date date, String student_id, String student_name, String room_type_id, String key_money_status) {
        this.setRes_id(res_id);
        this.setDate(date);
        this.getStudent().setNic(student_id);
        this.setStudent_name(student_name);
        this.getRoom().setRoom_type_id(room_type_id);
        ;
        this.setKey_money_status(key_money_status);
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}

