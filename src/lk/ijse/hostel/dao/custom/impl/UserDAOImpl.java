package lk.ijse.hostel.dao.custom.impl;

import lk.ijse.hostel.dao.custom.UserDAO;
import lk.ijse.hostel.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UserDAOImpl implements UserDAO {
    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) throws Exception {
        Query<User> query = session.createQuery("FROM User u WHERE u.name=:name AND u.password=:password");
        query.setParameter("name", username);
        query.setParameter("password", password);
        return query.uniqueResult();
    }

    @Override
    public boolean updateUser(String Username1, String Password1, String username2, String password2) {
        Query query = session.createQuery("update User u set u.name = :username2,u.password=:password2 where u.name = :Username1 and u.password = :Password1");
        query.setParameter("username2", username2);
        query.setParameter("password2", password2);
        query.setParameter("Password1", Password1);
        query.setParameter("Username1", Username1);
        int result = query.executeUpdate();

        return (result > 0);
    }

}
