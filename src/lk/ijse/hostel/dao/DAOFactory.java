package lk.ijse.hostel.dao;


import lk.ijse.hostel.dao.custom.SuperDAO;
import lk.ijse.hostel.dao.custom.impl.QueryDAOImpl;
import lk.ijse.hostel.dao.custom.impl.ReservationDAOImpl;
import lk.ijse.hostel.dao.custom.impl.RoomDAOImpl;
import lk.ijse.hostel.dao.custom.impl.StudentDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }
    public static DAOFactory getDaoFactory(){
        if (daoFactory==null){
            daoFactory=new DAOFactory();
        }
        return daoFactory;
    }
    public enum DAOTypes{
        ROOM,STUDENT,RESERVATION,QUERY
    }
    public SuperDAO getDAOTypes(DAOTypes boTypes){
        switch (boTypes){
            case ROOM:
                return new RoomDAOImpl();
            case STUDENT:
                return new StudentDAOImpl();
            case RESERVATION:
                return new ReservationDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            default:
                return null;
        }
    }
}
