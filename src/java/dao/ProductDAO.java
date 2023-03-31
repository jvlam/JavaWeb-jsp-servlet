package dao;

import dto.Category;
import dto.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import sample.utils.DBUtils;

/**
 *
 * @author MY LAPTOP
 */
public class ProductDAO {

    public static final String GET_ALL_PRODUCT = "SELECT productID, productName, [image], price, quantity, cateGoryID FROM Products";
    public static final String SEARCH_ID = "SELECT categoryID, categoryName FROM Categories WHERE categoryID = ?";
    public static final String GET_ALL_CATEGORY = "SELECT categoryID, categoryName FROM Categories";
    public static final String SEARCH_PRODUCT_BY_PID = "SELECT productID, productName, [image], price, quantity, cateGoryID FROM Products WHERE productID = ?";
    public static final String SEARCH_PRODUCT = "SELECT productID, productName, [image], price, quantity, cateGoryID from Products WHERE productName like ?";
    public static final String ADD_PRODUCT = "INSERT INTO Products VALUES (?, ?, ?, ?, ?, ?)";
    public static final String DELETE_PRODUCT_ADMIN = "DELETE FROM Products WHERE productID = ?";
    public static final String UPDATE_PRODUCT = "UPDATE Products SET productName = ?, [image] = ?, price = ?, quantity = ?, cateGoryID = ? WHERE productID = ?";

    public List<Product> getAllProduct() throws SQLException, ClassNotFoundException {
        List<Product> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ALL_PRODUCT);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("productID");
                    String name = rs.getString("productName");
                    String image = rs.getString("image");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    Category category = this.getCategoryByID(rs.getInt("categoryID"));
                    list.add(new Product(id, name, image, price, quantity, category));
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
        return list;
    }

    public Category getCategoryByID(int categoryID) throws SQLException {
        Category category = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_ID);
                ptm.setInt(1, categoryID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    category = new Category(rs.getInt("categoryID"), rs.getString("categoryName"));
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
        return category;
    }

    public Product getProductByPId(String productID) throws SQLException {
        Product p = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_PRODUCT_BY_PID);
                ptm.setString(1, productID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String id = rs.getString("productID");
                    String name = rs.getString("productName");
                    String image = rs.getString("image");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    Category category = this.getCategoryByID(rs.getInt("categoryID"));
                    p = new Product(id, name, image, price, quantity, category);
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
        return p;
    }

    public List<Product> searchProduct(String search) throws SQLException {
        List<Product> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_PRODUCT);
                ptm.setString(1, "%" + search + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("productID");
                    String name = rs.getString("productName");
                    String image = rs.getString("image");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    Category category = this.getCategoryByID(rs.getInt("categoryID"));
                    list.add(new Product(id, name, image, price, quantity, category));
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
        return list;
    }

    public boolean addProduct(Product pSuccess) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(ADD_PRODUCT);
                ptm.setString(1, pSuccess.getId());
                ptm.setString(2, pSuccess.getProductName());
                ptm.setString(3, pSuccess.getImage());
                ptm.setDouble(4, pSuccess.getPrice());
                ptm.setInt(5, pSuccess.getQuantity());
                ptm.setInt(6, pSuccess.getCategory().getCategoryID());
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

    public boolean delete(String pId) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(DELETE_PRODUCT_ADMIN);
                ptm.setString(1, pId);
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

    public boolean updateProduct(Product p) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_PRODUCT);
                ptm.setString(1, p.getProductName());
                ptm.setString(2, p.getImage());
                ptm.setDouble(3, p.getPrice());
                ptm.setInt(4, p.getQuantity());
                ptm.setInt(5, p.getCategory().getCategoryID());
                ptm.setString(6, p.getId());
                check = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
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

    // for testing only
    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        ProductDAO dao = new ProductDAO();
//        int p = dao.getNumberOfProduct();
//        List<Category> list = dao.getAllCategory();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
//        Date date1 = (Date) formatter.parse("2022-10-19");
//        Date date2 = (Date) formatter.parse("2022-10-05");
//          Date date1 = Date.valueOf("2022-10-19");
//          Date date2 = Date.valueOf("2022-10-05");
//        Category c = new Category(1, "meme");
//        boolean p = dao.addProduct(pSuceess);
//        System.out.println(p);
//        System.out.println(listP);
//        System.out.println(p);

    }

}
