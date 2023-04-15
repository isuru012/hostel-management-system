package lk.ijse.hostel.service.custom;

import lk.ijse.hostel.dto.ReservationDTO;
import lk.ijse.hostel.dto.StudentDTO;

import java.util.List;

public interface ReservationService extends SuperBO{
    boolean saveReservation(ReservationDTO reservationDTO);

    boolean updateReservation(ReservationDTO reservationDTO);

    boolean deleteReservation(ReservationDTO reservationDTO);

    ReservationDTO getReservationById(String id);

    List<String> getAllRoomTypeIds() throws Exception;

    List<String> getAllStudentIds() throws Exception;

    List<ReservationDTO> getAllReservations() throws Exception;

    StudentDTO findStudent(String selectedStudentId) throws Exception;

    String generateNewID() throws Exception;

    List<ReservationDTO> getUnpaidReservations();
}
