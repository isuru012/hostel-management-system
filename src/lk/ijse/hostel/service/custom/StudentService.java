package lk.ijse.hostel.service.custom;

import lk.ijse.hostel.dto.StudentDTO;


import java.util.List;

public interface StudentService extends SuperBO {
    boolean saveStudent(StudentDTO studentDTO) throws Exception;
    boolean updateStudent(StudentDTO studentDTO) throws Exception;
    boolean deleteStudent(StudentDTO student) throws Exception;
    List<StudentDTO> getAllStudents() throws Exception;

    StudentDTO getStudentById(String studentId) throws Exception;
}