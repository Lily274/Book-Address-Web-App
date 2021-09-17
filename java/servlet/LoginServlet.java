package servlet;

import service.UserService;
import service.impl.UserServiceImpl;
import vo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        java.lang.String username = req.getParameter("username");
        java.lang.String password = req.getParameter("password");
        // check database to verify the user and jump to system main page
        // default username: admin, password: admin
        try {
            UserService userService = new UserServiceImpl();
            User user = userService.queryUserById(username);
            if ((user != null && user.getUserPassword().equals(password)) || ("admin".equals(username) && "admin".equals(password))) {
                // to system main page
                req.setAttribute("username", username);
                req.getRequestDispatcher("/userServlet?type=0").forward(req, resp);
            } else {
                // error!
                req.setAttribute("username", username);
                req.setAttribute("error", "username or password error!");
                req.getRequestDispatcher("/").forward(req, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
