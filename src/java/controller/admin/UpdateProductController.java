/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

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
@WebServlet(name = "UpdateProductController", urlPatterns = {"/UpdateProductController"})
public class UpdateProductController extends HttpServlet {

    private static final String SUCCESS = "SearchController";
    private static final String ERROR = "SearchController";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        String pId = request.getParameter("pId");
        String pName = request.getParameter("pName");
        String image = request.getParameter("image");
        String price = request.getParameter("price");
        String quantity = request.getParameter("quantity");
        String cId = request.getParameter("cId");
        ProductDAO dao = new ProductDAO();
        try {
            Category category = dao.getCategoryByID(Integer.parseInt(cId));
            if(category == null) {
                url = ERROR;
                request.setAttribute("ERROR", "Category not exsit in database");
            } else {
                Product p = new Product(
                        pId, 
                        pName, 
                        image, 
                        Double.parseDouble(price),
                        Integer.parseInt(quantity),
                        category
                );
                boolean check = dao.updateProduct(p);
                if(check) {
                    url = SUCCESS;
                    request.setAttribute("SUCCESS", "Update Successful");
                }else{
                    url = ERROR;
                    request.setAttribute("ERROR", "Update Fail");
                }
            }
        } catch (NumberFormatException | SQLException e) {
            log("Error at Update Product Controller" + e.toString());
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
