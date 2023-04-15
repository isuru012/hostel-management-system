package lk.ijse.hostel.dao.custom;

import lk.ijse.hostel.entity.User;
import org.hibernate.Session;

public interface UserDAO extends SuperDAO {

    void setSession(Session session);

    User getUserByUsernameAndPassword(String username, String password) throws Exception;
    boolean updateUser(String Username,String Password,String username,String password);

}
