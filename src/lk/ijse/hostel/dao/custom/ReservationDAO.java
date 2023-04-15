package lk.ijse.hostel.dao.custom;

import lk.ijse.hostel.dto.ReservationDTO;
import lk.ijse.hostel.entity.Reservation;
import lk.ijse.hostel.entity.Student;
import org.hibernate.Session;

import java.util.List;

public interface ReservationDAO extends SuperDAO {
    void setSession(Session session);

    List<String> getAllRoomTypeIds() throws Exception;

    List<String> getAllStudentIds() throws Exception;

    List<Reservation> getAllReservations() throws Exception;

    boolean save(Reservation reservation) throws Exception;

    boolean update(Reservation reservation) throws Exception;

    boolean delete(Reservation reservation) throws Exception;

    Student findStudent(String studentId) throws Exception;

    String generateNewID() throws Exception;

    Reservation getReservationById(String id) throws Exception;

    List<Reservation> getUnpaidReservations();
}
