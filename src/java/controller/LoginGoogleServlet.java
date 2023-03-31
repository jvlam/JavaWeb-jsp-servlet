/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.GoogleUtils;
import dto.GoogleUserInformation;
import dao.UserDAO;
import dto.UserDTO;
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
@WebServlet(name = "LoginGoogleServlet", urlPatterns = {"/login-google"})
public class LoginGoogleServlet extends HttpServlet {

    private static final String ERROR = "login.jsp";
    private static final String US_PAGE = "home";

    private static final long serialVersionUID = 1L;

    public LoginGoogleServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            String accessToken = GoogleUtils.getToken(code);
            GoogleUserInformation User = GoogleUtils.getUserInfo(accessToken);
            String url = ERROR;
            UserDAO dao = new UserDAO();
            UserDTO user = null;
            String email = User.getEmail();
            try {
                if (email != null) {
                    user = dao.getUserByEmail(email);
                    HttpSession session = request.getSession();
                    if (user != null) {
                        session.setAttribute("LOGIN_USER", user);
                        url = US_PAGE;
                    } else {
                        UserDTO newUser = new UserDTO(User.getEmail(), User.getName(), null, User.getName(), "US", null, null, User.getPicture());
                        boolean check = dao.addNewUser(newUser);
                        if (check) {
                            session.setAttribute("LOGIN_USER", newUser);
                            url = US_PAGE;
                        } else {
                            request.setAttribute("ERROR", "add user error");
                        }
                    }
                } else {
                    request.setAttribute("ERROR", "can not get email from google");
                }
            } catch (Exception e) {
                log("Error at LoginGoogle" + e.toString());
            } finally {
                request.getRequestDispatcher(url).forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
