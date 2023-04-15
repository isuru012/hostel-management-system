package lk.ijse.hostel.service.custom.impl;

import lk.ijse.hostel.dao.DAOFactory;

import lk.ijse.hostel.dao.custom.StudentDAO;

import lk.ijse.hostel.dto.StudentDTO;
import lk.ijse.hostel.entity.Student;

import lk.ijse.hostel.service.custom.StudentService;
import lk.ijse.hostel.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private Session session;
    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.STUDENT);

    @Override
    public boolean saveStudent(StudentDTO dto) throws Exception {
        session = SessionFactoryConfiguration.getInstance()
                .getSession();
        Transaction transaction = session.beginTransaction();

        studentDAO.setSession(session);

        Student student = new Student(
                dto.getNic(),
                dto.getName(),
                dto.getAddress(),
                dto.getPhone_number(),
                dto.getDob(),
                dto.getGender()
        );
        boolean save = studentDAO.save(student);
        transaction.commit();
        session.close();
        return save;
    }

    @Override
    public boolean updateStudent(StudentDTO dto) throws Exception {
        session = SessionFactoryConfiguration.getInstance()
                .getSession();
        Transaction transaction = session.beginTransaction();
        studentDAO.setSession(session);
        Student student = new Student(
                dto.getNic(),
                dto.getName(),
                dto.getAddress(),
                dto.getPhone_number(),
                dto.getDob(),
                dto.getGender()
        );
        boolean update = studentDAO.update(student);
        transaction.commit();
        session.close();
        return update;
    }

    @Override
    public boolean deleteStudent(StudentDTO student) throws Exception {
        session = SessionFactoryConfiguration.getInstance()
                .getSession();
        Transaction transaction = session.beginTransaction();
        studentDAO.setSession(session);
        Student student1=new Student(
          student.getNic(),
          student.getName(),
          student.getAddress(),
          student.getPhone_number(),
          student.getDob(),
          student.getGender()
        );
        boolean delete = studentDAO.delete(student1);
        transaction.commit();
        session.close();
        return delete;
    }

    @Override
    public List<StudentDTO> getAllStudents() throws Exception {

        session = SessionFactoryConfiguration.getInstance()
                .getSession();
        Transaction transaction = session.beginTransaction();
        studentDAO.setSession(session);

        List<Student> studentList = studentDAO.getAll();
        List<StudentDTO> dtoList = new ArrayList<>();
        for (Student student : studentList) {
            dtoList.add(new StudentDTO(
                    student.getNic(),
                    student.getName(),
                    student.getAddress(),
                    student.getPhone_number(),
                    student.getDob(),
                    student.getGender()
            ));
        }

        transaction.commit();
        session.close();
        return dtoList;
    }

    @Override
    public StudentDTO getStudentById(String studentId) throws Exception {
        session = SessionFactoryConfiguration.getInstance()
                .getSession();
        Transaction transaction = session.beginTransaction();
        studentDAO.setSession(session);

        Student student = studentDAO.get(studentId);

        StudentDTO studentDTO=new StudentDTO();

        studentDTO.setNic(student.getNic());
        studentDTO.setName(student.getName());
        studentDTO.setPhone_number(student.getPhone_number());
        studentDTO.setAddress(student.getAddress());
        studentDTO.setDob(student.getDob());
        studentDTO.setGender(student.getGender());


        transaction.commit();
        session.close();
        return studentDTO;
    }

}
