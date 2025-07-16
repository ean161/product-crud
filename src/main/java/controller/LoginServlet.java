package controller;

import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import utils.Lib;

/**
 *
 * @author CE190990 - Nguyen Hoai An
 */
@WebServlet(name="LoginServlet", urlPatterns={"/login"})
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Lib.delCookie(response, "userID");
        Lib.delCookie(response, "userName");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        UserDAO dao = new UserDAO();
        User loggedUser = dao.login(username, Lib.hashMD5(password));
        
        if (loggedUser.getId() == -1) {
            Lib.createCookieMessage(response, "0", "The user or password was wrong");
            response.sendRedirect("login");
        } else {
            Lib.setCookie(response, "userID", loggedUser.getId() + "", 60 * 60);
            Lib.setCookie(response, "userName", loggedUser.getUsername(), 60 * 60);
            response.sendRedirect("product");
        }
    }
}
