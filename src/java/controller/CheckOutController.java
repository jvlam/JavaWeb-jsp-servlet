/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.OrderDAO;
import dto.CartDTO;
import dto.UserDTO;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
@WebServlet(name = "CheckOut", urlPatterns = {"/CheckOutController"})
public class CheckOutController extends HttpServlet {

    private static final String ERROR = "checkout.jsp";
    public static final String SUCCESS = "home";

    public static final String HOST_NAME = "smtp.gmail.com";

    public static final int SSL_PORT = 465; // Port for SSL

    public static final int TSL_PORT = 587; // Port for TLS/STARTTLS

    public static final String APP_EMAIL = "vuanhlam000@gmail.com";

    public static final String APP_PASSWORD = "siqruyemznwvjjdh";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        String email = request.getParameter("email");
        String totalMoney = request.getParameter("totalMoney");

        try {
            HttpSession session = request.getSession();
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            if (cart == null) {
                cart = new CartDTO();
            }
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
            if (user != null) {
                OrderDAO dao = new OrderDAO();
                boolean check = dao.addOrder(user, cart);
                if (check) {
                    url = SUCCESS;
                    session.removeAttribute("CART");
                    
                    // 1) get the session object
                    Properties props = new Properties();
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.host", HOST_NAME);
                    props.put("mail.smtp.socketFactory.port", SSL_PORT);
                    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    props.put("mail.smtp.port", SSL_PORT);

                    Session session1 = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(APP_EMAIL, APP_PASSWORD);
                        }
                    });

                    // 2) compose message
                    try {
                        MimeMessage message = new MimeMessage(session1);
                        message.setFrom(new InternetAddress(APP_EMAIL));
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                        
                        LocalDate curDate = java.time.LocalDate.now();
                        Date date = Date.valueOf(curDate.toString());
                        // 3) create HTML content
                        message.setSubject("[YOUR ORDER]");
                        String htmlContent = "<span>Order User: </span>"+ user.getFullName() +"<br><span>Total Money: </span> " + totalMoney +"<br><span>Order Date: </span>" + date;
                        message.setContent(htmlContent, "text/html");

                        // 4) send message
                        Transport.send(message);

                        System.out.println("Message sent successfully");
                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                    }
                }else {
                    request.setAttribute("ERROR", "add product fail");
                }
            }
        } catch (Exception e) {
            log("Error at Check Out Controller" + e.toString());
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
