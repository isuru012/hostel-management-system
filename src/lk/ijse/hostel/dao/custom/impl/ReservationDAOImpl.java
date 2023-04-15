package lk.ijse.hostel.dao.custom.impl;

import lk.ijse.hostel.dao.custom.ReservationDAO;
import lk.ijse.hostel.entity.Reservation;
import lk.ijse.hostel.entity.Room;
import lk.ijse.hostel.entity.Student;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {
    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public List<String> getAllRoomTypeIds() throws Exception {
        List<Room> roomTypes = session.createQuery("FROM Room").list();
        List<String> roomTypeIds = new ArrayList<>();
        for (Room roomType : roomTypes) {
            roomTypeIds.add(roomType.getRoom_type_id());
        }
        return roomTypeIds;
    }
    @Override
    public List<String> getAllStudentIds() throws Exception {
        List<Student> students = session.createQuery("FROM Student").list();
        List<String> studentIds = new ArrayList<>();
        for (Student student : students) {
            studentIds.add(student.getNic());
        }
        return studentIds;
    }

    @Override
    public List<Reservation> getAllReservations() throws Exception {
        return session.createQuery("FROM Reservation").list();
    }

    @Override
    public boolean save(Reservation reservation) throws Exception {
        try {
            session.save(reservation);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Reservation reservation) throws Exception {
        try {
            session.merge(reservation);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Reservation reservation) throws Exception {
        try {
            session.delete(reservation);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    @Override
    public Student findStudent(String studentId) throws Exception {
        Query query = session.createQuery("from Student where nic=:studentId");
        query.setParameter("studentId", studentId);
        Student student = (Student) query.uniqueResult();

        return student;
    }
    @Override
    public String generateNewID() throws Exception {

        String sql="FROM Reservation ORDER BY id DESC";
        Reservation reservation= (Reservation) session.createQuery(sql).setMaxResults(1).uniqueResult();

        if (reservation!=null){
            String lastId=reservation.getRes_id();
            int newCustomerId=Integer.parseInt(lastId.replace("R00-",""))+1;
            return String.format("R00-%03d",newCustomerId);
        }
        return "R00-001";
}
    @Override
    public Reservation getReservationById(String id) throws Exception {

        Reservation reservation = null;
        try {

            Query query = session.createQuery("FROM Reservation WHERE id=:id");
            query.setParameter("id", id);
            reservation = (Reservation) query.uniqueResult();

        } catch (Exception ex) {

            throw ex;
        }
        return reservation;
    }

    @Override
    public List<Reservation> getUnpaidReservations() {
        List<Reservation> reservation = null;
        try {

            Query query = session.createQuery("FROM Reservation WHERE key_money_status=:UNPAID");
            query.setParameter("UNPAID", "UNPAID");
            reservation =query.list();

        } catch (Exception ex) {

            throw ex;
        }
        return reservation;
    }

}
