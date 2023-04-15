package lk.ijse.hostel.dao.custom;

import lk.ijse.hostel.entity.Room;
import org.hibernate.Session;

import java.util.List;

public interface RoomDAO extends SuperDAO {

    void setSession(Session session);

    boolean saveRoom(Room room) throws Exception;

    boolean updateRoom(Room room) throws Exception;

    boolean deleteRoom(Room room) throws Exception;

    Room getRoomById(String roomId) throws Exception;

    List<Room> getAllRooms() throws Exception;

    List<Room> getAllRoomTypes() throws Exception;

    double getKeyMoneyByType(String selectedItem);


    boolean increaseQty(String typeId);

    boolean decreaseQty(String typeId);
}
