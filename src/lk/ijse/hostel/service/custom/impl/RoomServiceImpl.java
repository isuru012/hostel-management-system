package lk.ijse.hostel.service.custom.impl;

import lk.ijse.hostel.dao.DAOFactory;
import lk.ijse.hostel.dao.custom.RoomDAO;
import lk.ijse.hostel.dto.RoomDTO;
import lk.ijse.hostel.entity.Room;
import lk.ijse.hostel.service.custom.RoomService;
import lk.ijse.hostel.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.ArrayList;
import java.util.List;

public class RoomServiceImpl implements RoomService {

    private RoomDAO roomDAO;
    private Session session;

    public RoomServiceImpl() {
        roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.ROOM);

    }

    @Override
    public boolean saveRoom(RoomDTO roomDTO) throws Exception {
        session = SessionFactoryConfiguration.getInstance()
                .getSession();
        Transaction transaction = session.beginTransaction();

        try {
            roomDAO.setSession(session);
            boolean saveRoom = roomDAO.saveRoom(new Room(roomDTO.getRoom_type_id(), roomDTO.getRoom_type(),
                    roomDTO.getKey_money(), roomDTO.getRoom_quantity()));
            transaction.commit();
            session.close();

            return saveRoom;
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            return false;

        }
    }

    @Override
    public boolean updateRoom(RoomDTO roomDTO) throws Exception {
        session = SessionFactoryConfiguration.getInstance()
                .getSession();
        Transaction transaction = session.beginTransaction();
        try {
            roomDAO.setSession(session);
            Room room = roomDAO.getRoomById(roomDTO.getRoom_type_id());
            room.setRoom_type(roomDTO.getRoom_type());
            room.setRoom_quantity(roomDTO.getRoom_quantity());
            room.setKey_money(roomDTO.getKey_money());
            boolean updateRoom = roomDAO.updateRoom(room);
            transaction.commit();
            session.close();
            return updateRoom;
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            return false;

        }
    }

    @Override
    public boolean deleteRoom(Room room) throws Exception {
        session = SessionFactoryConfiguration.getInstance()
                .getSession();
        Transaction transaction = session.beginTransaction();
        try {
            roomDAO.setSession(session);
            boolean deleteRoom = roomDAO.deleteRoom(room);
            transaction.commit();
            session.close();
            return deleteRoom;
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            return false;

        }
    }

    @Override
    public RoomDTO getRoomById(String roomId) throws Exception {
        Room room = null;
        session = SessionFactoryConfiguration.getInstance()
                .getSession();
        Transaction transaction = session.beginTransaction();
        try {
            roomDAO.setSession(session);
            room = roomDAO.getRoomById(roomId);

        } catch (Exception e) {

            throw e;
        }
        if (room != null) {
            transaction.commit();
            session.close();
            return new RoomDTO(room.getRoom_type_id(), room.getRoom_type(), room.getKey_money(), room.getRoom_quantity());
        } else {
            transaction.rollback();
            session.close();
            return null;
        }
    }

    @Override
    public List<RoomDTO> getAllRooms() throws Exception {
        List<Room> rooms = null;
        session = SessionFactoryConfiguration.getInstance()
                .getSession();
        Transaction transaction = session.beginTransaction();

        roomDAO.setSession(session);

        try {
            rooms = roomDAO.getAllRooms();

        } catch (Exception e) {

            throw e;
        }

        if (rooms != null) {
            List<RoomDTO> roomDTOs = new ArrayList<>();
            for (Room room : rooms) {
                RoomDTO roomDTO = new RoomDTO(room.getRoom_type_id(), room.getRoom_type(), room.getKey_money(), room.getRoom_quantity());
                roomDTOs.add(roomDTO);
            }

            transaction.commit();
            session.close();
            return roomDTOs;
        } else {
            transaction.rollback();
            session.close();
            return null;
        }
    }
    @Override
    public List<RoomDTO> getAllRoomTypes() throws Exception {
        session = SessionFactoryConfiguration.getInstance()
                .getSession();
        Transaction transaction = session.beginTransaction();
        roomDAO.setSession(session);
        try {
            List<Room> rooms = roomDAO.getAllRooms();
            List<RoomDTO> roomDTOs = new ArrayList<>();

            for (Room room : rooms) {
                RoomDTO roomDTO = new RoomDTO();
                roomDTO.setRoom_type_id(room.getRoom_type_id());
                roomDTO.setRoom_type(room.getRoom_type());
                roomDTO.setKey_money(room.getKey_money());
                roomDTOs.add(roomDTO);
            }

            transaction.commit();
            session.close();
            return roomDTOs;

        } catch (Exception e) {
            transaction.rollback();
            session.close();
            throw new Exception("Failed to get room types.", e);
        }
    }

    @Override
    public double getKeyMoneyByType(String selectedItem) {
        session = SessionFactoryConfiguration.getInstance()
                .getSession();
        Transaction transaction = session.beginTransaction();
        roomDAO.setSession(session);
        double keymoney=roomDAO.getKeyMoneyByType(selectedItem);
        transaction.commit();
        session.close();

        return keymoney;
    }
    @Override
    public boolean increaseQuantity(String typeId){
        session = SessionFactoryConfiguration.getInstance()
                .getSession();
        Transaction transaction = session.beginTransaction();
        roomDAO.setSession(session);
        boolean updateQty = roomDAO.increaseQty(typeId);
        transaction.commit();
        session.close();
        return updateQty;


    }
    @Override
    public boolean decreaseQuantity(String typeId){
        session = SessionFactoryConfiguration.getInstance()
                .getSession();
        Transaction transaction = session.beginTransaction();
        roomDAO.setSession(session);
        boolean updateQty = roomDAO.decreaseQty(typeId);
        transaction.commit();
        session.close();
        return updateQty;

    }


}

