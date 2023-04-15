package lk.ijse.hostel.service;


import lk.ijse.hostel.service.custom.SuperBO;
import lk.ijse.hostel.service.custom.impl.ReservationServiceImpl;
import lk.ijse.hostel.service.custom.impl.RoomServiceImpl;
import lk.ijse.hostel.service.custom.impl.StudentServiceImpl;
import lk.ijse.hostel.service.custom.impl.UserServiceImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }
    public static BOFactory getBOFactory(){
        if (boFactory==null){
            boFactory=new BOFactory();
        }
        return boFactory;
    }
    public enum BOTypes{
        ROOM,STUDENT,USER,RESERVATION
    }
    public SuperBO getBOTypes(BOTypes boTypes){
        switch (boTypes){
            case ROOM:
                return new RoomServiceImpl();
            case STUDENT:
                return new StudentServiceImpl();
            case USER:
                return new UserServiceImpl();
            case RESERVATION:
                return new ReservationServiceImpl();
            default:
                return null;
        }
    }
}
