package lk.ijse.hostel.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {
    @Id
    String room_type_id;
    String room_type;
    double key_money;
    int room_quantity;

    @OneToMany(mappedBy = "room",cascade={CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    public Room() {
    }

    public Room(String room_type_id, String room_type, double key_money, int room_quantity) {
        this.room_type_id = room_type_id;
        this.room_type = room_type;
        this.key_money = key_money;
        this.room_quantity = room_quantity;
    }

    public String getRoom_type_id() {
        return room_type_id;
    }

    public void setRoom_type_id(String room_type_id) {
        this.room_type_id = room_type_id;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public double getKey_money() {
        return key_money;
    }

    public void setKey_money(double key_money) {
        this.key_money = key_money;
    }

    public int getRoom_quantity() {
        return room_quantity;
    }

    public void setRoom_quantity(int room_quantity) {
        this.room_quantity = room_quantity;
    }
}
