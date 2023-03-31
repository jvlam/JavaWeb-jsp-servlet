/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dto.ProductError;
import dao.ProductDAO;
import dto.Category;
import dto.Product;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MY LAPTOP
 */
@WebServlet(name = "CreateController", urlPatterns = {"/CreateController"})
public class CreateController extends HttpServlet {
    
    private static final String SUCCESS = "create.jsp";
    private static final String ERROR = "create.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        ProductDAO dao = new ProductDAO();
        ProductError pError = new ProductError();
        try {
            String pid = request.getParameter("pID");
            String pName = request.getParameter("pName");
            String image = request.getParameter("image");
            String price = request.getParameter("price");
            String quantity = request.getParameter("quantity");
            String cid = request.getParameter("cid");
            
            boolean Valid = true;
            Product checkPID = dao.getProductByPId(pid);
            if(checkPID != null) {
                pError.setPid("id can not duplicated");
                Valid = false;
            }
            if(pName.length() < 2 || pName.length() > 50) {
                pError.setpName("name must be [2,50]");
                Valid = false;
            }
            Category checkCID = dao.getCategoryByID(Integer.parseInt(cid));
            if(checkCID == null) {
                pError.setCid("category id not exsit in database");
                Valid = false;
            }
            if(Integer.parseInt(quantity) <= 0) {
                pError.setQuantity("quantity must >= 1");
            }
            if(Double.parseDouble(price) <= 0) {
                pError.setPrice("price must be > 0");
                Valid = false;
            }
            if(Valid) {
                Product pSuccess = new Product(
                        pid, 
                        pName, 
                        image, 
                        Double.parseDouble(price), 
                        Integer.parseInt(quantity), 
                        checkCID
                );
                boolean check = dao.addProduct(pSuccess);
                if(check) {
                    request.setAttribute("SUCCESS", "Add Successful");
                    url = SUCCESS;
                }
            } else {
                request.setAttribute("ERROR", pError);
                url = SUCCESS;
            }
        } catch (NumberFormatException | SQLException e) {
            log("Error at Create Controller" + e.toString());
        }finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
