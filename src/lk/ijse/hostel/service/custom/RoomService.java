package lk.ijse.hostel.service.custom;

import lk.ijse.hostel.dto.RoomDTO;
import lk.ijse.hostel.entity.Room;

import java.util.List;

public interface RoomService extends SuperBO {

    boolean saveRoom(RoomDTO roomDTO) throws Exception;

    boolean updateRoom(RoomDTO roomDTO) throws Exception;

    boolean deleteRoom(Room room) throws Exception;

    RoomDTO getRoomById(String roomId) throws Exception;

    List<RoomDTO> getAllRooms() throws Exception;

    List<RoomDTO> getAllRoomTypes() throws Exception;


    double getKeyMoneyByType(String selectedItem);

    boolean increaseQuantity(String typeId);

    boolean decreaseQuantity(String typeId);
}

