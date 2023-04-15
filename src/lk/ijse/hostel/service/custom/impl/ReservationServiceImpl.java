package lk.ijse.hostel.service.custom.impl;

import lk.ijse.hostel.dao.DAOFactory;

import lk.ijse.hostel.dao.custom.ReservationDAO;

import lk.ijse.hostel.dao.custom.RoomDAO;
import lk.ijse.hostel.dao.custom.StudentDAO;
import lk.ijse.hostel.dto.ReservationDTO;
import lk.ijse.hostel.dto.RoomDTO;
import lk.ijse.hostel.dto.StudentDTO;
import lk.ijse.hostel.entity.Reservation;
import lk.ijse.hostel.entity.Room;
import lk.ijse.hostel.entity.Student;
import lk.ijse.hostel.service.custom.ReservationService;
import lk.ijse.hostel.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationServiceImpl implements ReservationService {
    private Session session;
    private final ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.RESERVATION);
    private final RoomDAO roomDao = (RoomDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.ROOM);
    private final StudentDAO studentDao = (StudentDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.STUDENT);

    @Override
    public boolean saveReservation(ReservationDTO reservationDTO) {
        boolean success = false;
        session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            System.out.println(reservationDTO);
            reservationDAO.setSession(session);

            Reservation reservation = new Reservation();

            reservation.setRes_id(reservationDTO.getRes_id());
            reservation.setDate(reservationDTO.getDate());

            System.out.println(reservationDTO.getStudentDTO().getNic() + "tyhthnthnth");

            Student student = new Student();
            student.setNic(reservationDTO.getStudentDTO().getNic());
            student.setAddress(reservationDTO.getStudentDTO().getAddress());
            student.setDob(reservationDTO.getStudentDTO().getDob());
            student.setGender(reservationDTO.getStudentDTO().getGender());
            student.setName(reservationDTO.getStudentDTO().getName());
            student.setPhone_number(reservationDTO.getStudentDTO().getPhone_number());

            Room room = new Room();

            room.setRoom_type_id(reservationDTO.getRoomDTO().getRoom_type_id());
            room.setRoom_type(reservationDTO.getRoomDTO().getRoom_type());
            room.setRoom_quantity(reservationDTO.getRoomDTO().getRoom_quantity());
            room.setKey_money(reservationDTO.getRoomDTO().getKey_money());


            reservation.setStudent(student);
            reservation.setStudent_name(reservationDTO.getStudent_name());
            reservation.setRoom(room);
            reservation.setKey_money_status(reservationDTO.getKey_money_status());

            success = reservationDAO.save(reservation);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
        return success;
    }

    @Override
    public boolean updateReservation(ReservationDTO reservationDTO) {
        boolean success = false;
        session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            reservationDAO.setSession(session);

            Reservation reservation = reservationDAO.getReservationById(reservationDTO.getRes_id());

            reservation.setDate(reservationDTO.getDate());

            Student student = new Student();
            student.setNic(reservationDTO.getStudentDTO().getNic());
            student.setAddress(reservationDTO.getStudentDTO().getAddress());
            student.setDob(reservationDTO.getStudentDTO().getDob());
            student.setGender(reservationDTO.getStudentDTO().getGender());
            student.setName(reservationDTO.getStudentDTO().getName());
            student.setPhone_number(reservationDTO.getStudentDTO().getPhone_number());

            Room room = new Room();

            room.setRoom_type_id(reservationDTO.getRoomDTO().getRoom_type_id());
            room.setRoom_type(reservationDTO.getRoomDTO().getRoom_type());
            room.setRoom_quantity(reservationDTO.getRoomDTO().getRoom_quantity());
            room.setKey_money(reservationDTO.getRoomDTO().getKey_money());


            reservation.setStudent(student);
            reservation.setStudent_name(reservationDTO.getStudent_name());
            reservation.setRoom(room);

            reservation.setKey_money_status(reservationDTO.getKey_money_status());

            success = reservationDAO.update(reservation);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
        return success;
    }

    @Override
    public boolean deleteReservation(ReservationDTO reservationDTO) {
        boolean success = false;
        session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            reservationDAO.setSession(session);
            Reservation reservation = new Reservation();
            reservation.setRes_id(reservationDTO.getRes_id());
            reservation.setDate(reservationDTO.getDate());

            Student student = new Student();
            student.setNic(reservationDTO.getStudentDTO().getNic());
            student.setAddress(reservationDTO.getStudentDTO().getAddress());
            student.setDob(reservationDTO.getStudentDTO().getDob());
            student.setGender(reservationDTO.getStudentDTO().getGender());
            student.setName(reservationDTO.getStudentDTO().getName());
            student.setPhone_number(reservationDTO.getStudentDTO().getPhone_number());

            Room room = new Room();

            room.setRoom_type_id(reservationDTO.getRoomDTO().getRoom_type_id());
            room.setRoom_type(reservationDTO.getRoomDTO().getRoom_type());
            room.setRoom_quantity(reservationDTO.getRoomDTO().getRoom_quantity());
            room.setKey_money(reservationDTO.getRoomDTO().getKey_money());


            reservation.setStudent(student);
            reservation.setRoom(room);
            reservation.setStudent_name(reservationDTO.getStudent_name());
            reservation.setKey_money_status(reservationDTO.getKey_money_status());


            success = reservationDAO.delete(reservation);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
        return success;
    }

    @Override
    public ReservationDTO getReservationById(String id) {
        session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            reservationDAO.setSession(session);

            Reservation reservation = reservationDAO.getReservationById(id);
            transaction.commit();
            session.close();

            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setRoom_type_id(reservation.getRoom().getRoom_type_id());
            roomDTO.setRoom_type(reservation.getRoom().getRoom_type());
            roomDTO.setRoom_quantity(reservation.getRoom().getRoom_quantity());
            roomDTO.setKey_money(reservation.getRoom().getKey_money());

            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setNic(reservation.getStudent().getNic());
            studentDTO.setGender(reservation.getStudent().getGender());
            studentDTO.setDob(reservation.getStudent().getDob());
            studentDTO.setAddress(reservation.getStudent().getAddress());
            studentDTO.setPhone_number(reservation.getStudent().getPhone_number());
            studentDTO.setName(reservation.getStudent().getName());

            if (reservation != null) {
                return new ReservationDTO(reservation.getRes_id(), reservation.getDate(), reservation.getStudent().getName(),
                        reservation.getKey_money_status(), studentDTO, roomDTO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
            session.close();
        }
        return null;
    }

    @Override
    public List<String> getAllRoomTypeIds() throws Exception {

        session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        roomDao.setSession(session);
        List<Room> roomTypeDTOs = roomDao.getAllRoomTypes();
        List<String> roomTypeIds = new ArrayList<>();
        for (Room room : roomTypeDTOs) {
            roomTypeIds.add(room.getRoom_type_id());
        }
        transaction.commit();
        session.close();
        return roomTypeIds;
    }

    @Override
    public List<String> getAllStudentIds() throws Exception {
        session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        studentDao.setSession(session);
        List<Student> studentDTOs = studentDao.getAll();
        List<String> studentIds = new ArrayList<>();
        for (Student studentDTO : studentDTOs) {
            studentIds.add(studentDTO.getNic());
        }
        transaction.commit();
        session.close();
        return studentIds;
    }

    @Override
    public List<ReservationDTO> getAllReservations() throws Exception {
        session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        reservationDAO.setSession(session);
        List<ReservationDTO> reservationDTOs = reservationDAO.getAllReservations().stream()
                .map(reservation -> {
                    RoomDTO roomDTO = new RoomDTO();
                    if (reservation.getRoom()!=null) {
                        roomDTO.setRoom_type_id(reservation.getRoom().getRoom_type_id());
                        roomDTO.setRoom_type(reservation.getRoom().getRoom_type());
                        roomDTO.setRoom_quantity(reservation.getRoom().getRoom_quantity());
                        roomDTO.setKey_money(reservation.getRoom().getKey_money());
                    }

                    StudentDTO studentDTO = new StudentDTO();
                    if (reservation.getStudent()!=null) {
                        studentDTO.setNic(reservation.getStudent().getNic());
                        studentDTO.setGender(reservation.getStudent().getGender());
                        studentDTO.setDob(reservation.getStudent().getDob());
                        studentDTO.setAddress(reservation.getStudent().getAddress());
                        studentDTO.setPhone_number(reservation.getStudent().getPhone_number());
                        studentDTO.setName(reservation.getStudent().getName());
                    }
                    return new ReservationDTO(
                            reservation.getRes_id(),
                            reservation.getDate(),
                            reservation.getStudent_name(),
                            reservation.getKey_money_status(),
                            studentDTO,
                            roomDTO

                    );
                })
                .collect(Collectors.toList());
        transaction.commit();
        session.close();
        return reservationDTOs;
    }

    @Override
    public StudentDTO findStudent(String selectedStudentId) throws Exception {
        session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            studentDao.setSession(session);
            Student student = studentDao.findStudentById(selectedStudentId);
            transaction.commit();
            if (student != null) {
                return new StudentDTO(student.getNic(), student.getName(), student.getAddress(),
                        student.getPhone_number(), student.getDob(), student.getGender());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public String generateNewID() throws Exception {
        session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        reservationDAO.setSession(session);

        String generateNewID = reservationDAO.generateNewID();

        transaction.commit();
        session.close();
        return generateNewID;
    }

    @Override
    public List<ReservationDTO> getUnpaidReservations() {
        session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        reservationDAO.setSession(session);

        List<ReservationDTO> reservationDTOs = reservationDAO.getUnpaidReservations().stream()
                .map(reservation -> {
                    RoomDTO roomDTO = new RoomDTO();
                    roomDTO.setRoom_type_id(reservation.getRoom().getRoom_type_id());
                    roomDTO.setRoom_type(reservation.getRoom().getRoom_type());
                    roomDTO.setRoom_quantity(reservation.getRoom().getRoom_quantity());
                    roomDTO.setKey_money(reservation.getRoom().getKey_money());

                    StudentDTO studentDTO = new StudentDTO();
                    studentDTO.setNic(reservation.getStudent().getNic());
                    studentDTO.setGender(reservation.getStudent().getGender());
                    studentDTO.setDob(reservation.getStudent().getDob());
                    studentDTO.setAddress(reservation.getStudent().getAddress());
                    studentDTO.setPhone_number(reservation.getStudent().getPhone_number());
                    studentDTO.setName(reservation.getStudent().getName());

                    return new ReservationDTO(
                            reservation.getRes_id(),
                            reservation.getDate(),
                            reservation.getStudent_name(),
                            reservation.getKey_money_status(),
                            studentDTO,
                            roomDTO

                    );
                })
                .collect(Collectors.toList());
        transaction.commit();
        session.close();
        return reservationDTOs;
    }


}

