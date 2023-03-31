/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.BuyItem;
import dto.CartDTO;
import dto.UserDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.naming.NamingException;
import sample.utils.DBUtils;

/**
 *
 * @author MY LAPTOP
 */
public class OrderDAO {

    public static final String INSERT_ORDER = "INSERT INTO Orders VALUES(?, ?, ?)";
    public static final String GET_ID_FROM_ORDER = "SELECT TOP 1 orderID FROM Orders ORDER BY orderID DESC";
    public static final String INSERT_ORDERDETAIL = "INSERT INTO OrderDetails VALUES(?, ?, ?, ?)";
    public static final String UPDATE_QUANTITY = "UPDATE Products SET quantity = ? WHERE productID = ?";

    public boolean addOrder(UserDTO users, CartDTO cart) throws SQLException {
        boolean check = false;
        LocalDate curDate = java.time.LocalDate.now();
        Date date = Date.valueOf(curDate.toString());
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(INSERT_ORDER);
                ptm.setDate(1, date);
                ptm.setDouble(2, cart.getTotalMoney());
                ptm.setString(3, users.getEmail());
                boolean valid = ptm.executeUpdate() > 0;
                if (valid) {
                    check = this.insertOrderDetails(cart);
                }
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

    public int getIdOrder() throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        int result = 0;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ID_FROM_ORDER);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("OrderID");
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
        return result;
    }

    public boolean insertOrderDetails(CartDTO cart) throws SQLException {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            int orderId = this.getIdOrder();
            if(conn != null) {
                for (BuyItem p : cart.getCart().values()) {
                    ptm = conn.prepareStatement(INSERT_ORDERDETAIL);
                    ptm.setInt(1, orderId);
                    ptm.setString(2, p.getProduct().getId());
                    ptm.setInt(3, p.getQuantity());
                    ptm.setDouble(4, p.getPrice() * p.getQuantity());
                    boolean check = ptm.executeUpdate() > 0;
                    if (check) {
                        ProductDAO pDao = new ProductDAO();
                        int warehouseQuantity = pDao.getProductByPId(p.getProduct().getId()).getQuantity() - p.getQuantity();
                        success = this.updateQuantity(warehouseQuantity, p.getProduct().getId());
                    }
                }
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
        return success;
    }

    public boolean updateQuantity(int quantity, String id) throws SQLException {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_QUANTITY);
                ptm.setInt(1, quantity);
                ptm.setString(2, id);
                success = ptm.executeUpdate() > 0;
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
        return success;
    }
}
