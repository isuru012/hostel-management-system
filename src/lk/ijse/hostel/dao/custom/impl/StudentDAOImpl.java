package lk.ijse.hostel.dao.custom.impl;


import lk.ijse.hostel.dao.custom.StudentDAO;
import lk.ijse.hostel.entity.Student;

import org.hibernate.Session;

import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Student student) throws Exception {
            session.save(student);
        return true;

    }

    @Override
    public boolean update(Student student) throws Exception {
            session.update(student);
        return true;

    }

    @Override
    public boolean delete(Student student) throws Exception {
     session.delete(student);
        return true;


    }

    @Override
    public Student get(String studentId) throws Exception {

            return session.get(Student.class, studentId);

    }

    @Override
    public List<Student> getAll() throws Exception {

            return session.createQuery("FROM Student", Student.class).list();

    }

    @Override
    public List<Student> searchStudents(String query) throws Exception {
        return null;
    }
    @Override
    public Student findStudentById(String studentId) {
        try {
            Student student = session.get(Student.class, studentId);
            return student;
        } catch (Exception ex) {

        }
        return null;
    }


   /* @Override
    public List<Student> searchStudents(String query) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return session.createQuery("FROM Student WHERE name LIKE :query OR nic LIKE :query OR phoneNumber LIKE :query OR campusId LIKE :query", Student.class)
                    .setParameter("query", "%" + query + "%")
                    .list();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }*/
}
