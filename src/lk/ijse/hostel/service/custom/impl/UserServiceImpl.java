package lk.ijse.hostel.service.custom.impl;

import lk.ijse.hostel.dao.custom.UserDAO;
import lk.ijse.hostel.dao.custom.impl.UserDAOImpl;
import lk.ijse.hostel.dto.UserDTO;
import lk.ijse.hostel.entity.User;
import lk.ijse.hostel.service.custom.UserService;
import lk.ijse.hostel.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserServiceImpl implements UserService {
    private Session session;
    private final UserDAO userDao = new UserDAOImpl();

    @Override
    public UserDTO getUserByUsernameAndPassword(String username, String password) {
        session = SessionFactoryConfiguration.getInstance()
                .getSession();
        Transaction transaction = session.beginTransaction();
        try{
            userDao.setSession(session);
            User user = userDao.getUserByUsernameAndPassword(username, password);
            transaction.commit();
            session.close();
            if (user != null) {
                return new UserDTO(user.getId(), user.getName(), user.getPassword());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
            session.close();
        }

        return null;
    }

    @Override
    public boolean updateUser(String username, String password, String text, String text1) {
        boolean success = false;
        session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            userDao.setSession(session);
            success = userDao.updateUser(username, password, text, text1);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
        return success;
    }

}
