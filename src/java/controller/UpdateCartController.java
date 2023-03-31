/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProductDAO;
import dto.BuyItem;
import dto.CartDTO;
import dto.Product;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MY LAPTOP
 */
@WebServlet(name = "UpdateCartController", urlPatterns = {"/UpdateCartController"})
public class UpdateCartController extends HttpServlet {

    private static final String SUCCESS = "shoping-cart.jsp";
    private static final String ERROR = "shoping-cart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        String id = request.getParameter("pid");
        String quantity_raw = request.getParameter("quantity");
        ProductDAO dao = new ProductDAO();
        try {
            HttpSession session = request.getSession();
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            if (cart != null) {
                if (cart.getCart().containsKey(id)) {
                    Product product = dao.getProductByPId(id);
                    double price = cart.getCart().get(id).getPrice();
                    int newQuantity = Integer.parseInt(quantity_raw);
                    if (newQuantity > dao.getProductByPId(id).getQuantity()) { //nếu cập nhật quá số lượng trong kho sẽ báo lỗi
                        request.setAttribute("ERROR", "số lượng mua đã vượt quá trong kho");
                    } else {
                        BuyItem p = new BuyItem(product, newQuantity, price);
                        boolean check = cart.update(id, p);
                        double total = cart.getTotalMoney();
                        if (check) {
                            session.setAttribute("CART", cart);
                            session.setAttribute("TOTAL_CART_MONEY", total);
                            url = SUCCESS;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log("Error at Update Controller" + e.toString());
        } finally {
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
