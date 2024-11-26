package ServicesImpl;


import DB_Connection.ConnectionDB;
import DTO.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserImpl implements UserInterface {

    User user = new User();
    @Override
    public boolean add(User user) throws SQLException, ClassNotFoundException {
        String query = "insert into users (firstname, lastname, email, password) values (?,?,?,?)";
        try(var connectionDB = ConnectionDB.getConnection();
            PreparedStatement ps = connectionDB.prepareStatement(query)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2,user.getLastName());
            ps.setString(3,user.getEmail());
            ps.setString(4,hashPassword(user.getPassword()));
            return ps.executeUpdate() > 0;
        }
    }


    @Override
    public User login(String username, String password) throws SQLException, ClassNotFoundException {
        String query = "select * from users where email = ?";
        try (var connectionDB = ConnectionDB.getConnection();
             var ps = connectionDB.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password");
                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                    if (encoder.matches(password, hashedPassword)) {
                        // Populate the user object with data from the result set
                        user.setId(rs.getLong("id"));
                        user.setFirstName(rs.getString("firstname"));
                        user.setLastName(rs.getString("lastname"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(hashedPassword); // Optional, avoid storing passwords in memory
                        return user;
                    }
                }
            }
        }
        return null; // Return null if login fails
    }


    @Override
    public boolean edit(User user) throws SQLException, ClassNotFoundException {
        String query = "update users set firstname = ?,lastname = ?,email = ?,password = ? where id = ?";
        try (var connectionDB = ConnectionDB.getConnection();
             var ps = connectionDB.prepareStatement(query)){
            ps.setString(1,user.getFirstName());
            ps.setString(2,user.getLastName());
            ps.setString(3,user.getEmail());
            ps.setString(4,hashPassword(user.getPassword()));
            ps.setLong(5,user.getId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        }
    }
    private String hashPassword(String plainPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(plainPassword);
    }
}

