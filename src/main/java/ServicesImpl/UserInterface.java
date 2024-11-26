package ServicesImpl;


import DTO.User;
import java.sql.SQLException;

public interface UserInterface {
    boolean add(User user) throws SQLException, ClassNotFoundException;
    User login(String username, String password) throws SQLException, ClassNotFoundException;
    boolean edit(User user) throws SQLException, ClassNotFoundException;
}
