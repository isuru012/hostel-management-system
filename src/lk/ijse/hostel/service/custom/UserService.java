package lk.ijse.hostel.service.custom;

import lk.ijse.hostel.dto.UserDTO;

public interface UserService extends SuperBO {

    UserDTO getUserByUsernameAndPassword(String username, String password);

    boolean updateUser(String username, String password, String text, String text1);
}
