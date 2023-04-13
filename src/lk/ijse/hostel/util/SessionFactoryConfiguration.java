package lk.ijse.hostel.util;


import lk.ijse.hostel.entity.Reservation;
import lk.ijse.hostel.entity.Room;
import lk.ijse.hostel.entity.Student;
import lk.ijse.hostel.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SessionFactoryConfiguration {

    private static SessionFactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private SessionFactoryConfiguration(){

        try {
            Configuration configuration = new Configuration();
            ServiceRegistry serviceRegistry
                    = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            configuration.addAnnotatedClass(User.class).addAnnotatedClass(Room.class)
                    .addAnnotatedClass(Student.class).addAnnotatedClass(Reservation.class);
           sessionFactory=configuration
                    .buildSessionFactory(serviceRegistry);
        }catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("There is issue in hibernate util");
        }
    }

    public static SessionFactoryConfiguration getInstance() {
        return (null == factoryConfiguration) ?
                factoryConfiguration = new SessionFactoryConfiguration() : factoryConfiguration;
    }

    public Session getSession() throws HibernateException {

        Session session = sessionFactory.openSession();

        return session;
    }

}
