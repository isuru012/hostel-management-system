package lk.ijse.hostel.dto;

import java.sql.Date;
import java.time.LocalDate;

public class StudentDTO{
    private String nic;
    private String name;
    private String address;
    private int phone_number;
    private LocalDate dob;
    private String gender;

    public StudentDTO() {
    }

    public StudentDTO(String nic, String name, String address, int phone_number, LocalDate dob, String gender) {
        this.nic = nic;
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
        this.dob = dob;
        this.gender = gender;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
