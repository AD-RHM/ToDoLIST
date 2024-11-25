package Impl;


import DB_Connection.ConnectionDB;
import Interfaces.UserInterface;
import DTO.User;

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
            ps.setString(4,user.getPassword());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        }
    }


    @Override
    public User login(String username, String password) throws SQLException, ClassNotFoundException {

        String query = "select * from users where email = ? and password = ?";
        try(var connectionDB = ConnectionDB.getConnection();
        var ps = connectionDB.prepareStatement(query)) {
            ps.setString(1,username);
            ps.setString(2,password);
            try(ResultSet rs = ps.executeQuery(query)) {
                while(rs.next()) {
                    user.setId(rs.getLong("id"));
                    user.setFirstName(rs.getString("firstname"));
                    user.setLastName(rs.getString("lastname"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                }
            }
        }
        return user;
    }

    @Override
    public boolean edit(User user) throws SQLException, ClassNotFoundException {
        String query = "update users set firstname = ?,lastname = ?,email = ?,password = ? where id = ?";
        try (var connectionDB = ConnectionDB.getConnection();
             var ps = connectionDB.prepareStatement(query)){
            ps.setString(1,user.getFirstName());
            ps.setString(2,user.getLastName());
            ps.setString(3,user.getEmail());
            ps.setString(4,user.getPassword());
            ps.setLong(5,user.getId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        }
    }
}
