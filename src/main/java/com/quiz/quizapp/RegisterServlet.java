package com.quiz.quizapp;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class.getName());
    private static final String DASHBOARD_URL = "dashboard.jsp";
    private static final String REGISTER_URL = "register.jsp?error=true";
    
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        super.init();
        userDao = new UserDao();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();
        try {
            if (userDao.registerUser(name, email, password)) {
                session.setAttribute("email", email);
                resp.sendRedirect(DASHBOARD_URL);
                LOGGER.info("User registered successfully: " + email);
            } else {
                resp.sendRedirect(REGISTER_URL);
                LOGGER.warning("Registration failed for user: " + email);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during registration for user: " + email, e);
            resp.sendRedirect(REGISTER_URL);
        }
    }
}
