package lk.ijse.hostel.dao.custom;

import lk.ijse.hostel.entity.Student;
import org.hibernate.Session;

import java.util.List;

public interface StudentDAO extends SuperDAO {

    void setSession(Session session);

    boolean save(Student student) throws Exception;

    boolean update(Student student) throws Exception;

    boolean delete(Student student) throws Exception;

    Student get(String studentId) throws Exception;

    List<Student> getAll() throws Exception;

    List<Student> searchStudents(String query) throws Exception;

    Student findStudentById(String studentId);
}
