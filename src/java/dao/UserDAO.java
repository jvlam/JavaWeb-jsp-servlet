/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import sample.utils.DBUtils;

/**
 *
 * @author MY LAPTOP
 */
public class UserDAO {

    private static final String CHECK_USER_EMAIL = "SELECT email, userID, fullName, [password], roleID, [address], phone, avatar FROM Users WHERE email = ?";
    private static final String ADD_USER = "INSERT INTO Users VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String LOGIN = "SELECT email,  userID, fullName, roleID, [password], [address], phone, avatar FROM Users WHERE userID = ? and [password] = ?";
    
    public UserDTO getUserByEmail(String mail) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        UserDTO user = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_USER_EMAIL);
                ptm.setString(1, mail);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String email = rs.getString("email");
                    String userID = rs.getString("userID");
                    String name = rs.getString("fullName");
                    String password = rs.getString("password");
                    String roleID = rs.getString("roleID");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    String avatar = rs.getString("avatar");
                    user = new UserDTO(email, userID, password, name, roleID, address, phone, avatar);
                }
            }
        } catch (ClassNotFoundException | SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return user;
    }

    public boolean addNewUser(UserDTO newUser) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(ADD_USER);
                ptm.setString(1, newUser.getEmail());
                ptm.setString(2, newUser.getUserID());
                ptm.setString(3, newUser.getPassword());
                ptm.setString(4, newUser.getFullName());
                ptm.setString(5, newUser.getRoleID());
                ptm.setString(6, newUser.getAddress());
                ptm.setString(7, newUser.getPhone());
                ptm.setString(8, newUser.getAvatar());
                check = ptm.executeUpdate() > 0;
            }
        } catch (ClassNotFoundException | SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public UserDTO checkLogin(String userId, String password) throws SQLException {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOGIN);
                ptm.setString(1, userId);
                ptm.setString(2, password);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String email = rs.getString("email");
                    String userID = rs.getString("userID");
                    String name = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    String pass = rs.getString("password");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    String avatar = rs.getString("avatar");
                    user = new UserDTO(email, userID, pass, name, roleID, address, phone, avatar);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return user;
    }

    public static void main(String[] args) throws SQLException {
        UserDAO dao = new UserDAO();
        //dao.addNewUser(new UserDTO("vuanhlam000@gmail.com", "vu anh lam", null, "US", null, null, null, "1", null, "blablabla"));
        UserDTO user = dao.checkLogin("admin", "1");
        System.out.println(user);
    }
}
