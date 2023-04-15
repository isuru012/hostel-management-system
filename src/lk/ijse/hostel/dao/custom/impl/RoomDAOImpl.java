package lk.ijse.hostel.dao.custom.impl;


import lk.ijse.hostel.dao.custom.RoomDAO;
import lk.ijse.hostel.entity.Room;
import org.hibernate.Session;



import java.util.List;

public class RoomDAOImpl implements RoomDAO {
    private Session session;


    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    public RoomDAOImpl() {

    }

    @Override
    public boolean saveRoom(Room room) throws Exception {

            session.save(room);
            return true;

    }

    @Override
    public boolean updateRoom(Room room) throws Exception {
            session.update(room);
            return true;

    }

    @Override
    public boolean deleteRoom(Room room ) throws Exception {

        session.delete(room);
            return true;

    }
    @Override
    public Room getRoomById(String roomId) throws Exception {

            return session.get(Room.class, roomId);

    }

    @Override
    public List<Room> getAllRooms() throws Exception {

            return session.createQuery("FROM Room").list();

    }
    @Override
    public List<Room> getAllRoomTypes() throws Exception {

        return session.createQuery("FROM Room").list();
    }

    @Override
    public double getKeyMoneyByType(String selectedItem) {
        return Double.parseDouble(session.createQuery("SELECT r.key_money FROM Room r WHERE r.room_type = :selectedType", Room.class).getQueryString());
    }

    @Override
    public boolean increaseQty(String typeId) {

        Room room = session.get(Room.class, typeId);
        if (room != null) {
            room.setRoom_quantity(room.getRoom_quantity() + 1);
            session.update(room);
            return true;
        }
       return false;
    }
    @Override
    public boolean decreaseQty(String typeId) {

        Room room = session.get(Room.class, typeId);
        if (room != null) {
            room.setRoom_quantity(room.getRoom_quantity() - 1);
            session.update(room);
            return true;
        }
        return false;
    }
}