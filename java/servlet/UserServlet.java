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
import java.util.List;

public class UserServlet extends HttpServlet {

    UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String type = req.getParameter("type");

        switch (type) {
            case "1": // save
                addUser(req, resp);
                break;
            case "2": // edit
                editUser(req, resp);
                break;
            case "3":   // delete
                deleteUser(req, resp);
                break;
            case "4":   // verify
                verifyUser(req, resp);
                break;
            default:   // query
                queryUsers(req, resp);
        }
    }

    public void addUser(HttpServletRequest req, HttpServletResponse resp) {
        try {
            User user = new User();
            user.setUserId(req.getParameter("userId"));
            user.setUserName(req.getParameter("userName"));
            user.setUserPassword(req.getParameter("userPassword"));
            String genderStr = req.getParameter("gender");
            String flag = req.getParameter("flag");
            if (genderStr != null) {
                user.setGender(Integer.valueOf(genderStr));
            }
            String roleIdStr = req.getParameter("roleId");
            if (roleIdStr == null) {
                user.setRoleId(2);
            } else {
                user.setRoleId(Integer.valueOf(roleIdStr));
            }
            if ("1".equals(flag)) {  // edit
                userService.editUser(user);
            } else {
                userService.addUser(user);
            }
            // after save, return to the whole list.
            req.getRequestDispatcher("/userServlet?type=0").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editUser(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        try {
            User user = userService.queryUserById(userId);
            req.setAttribute("user", user);
            req.setAttribute("flag", "1");
            req.getRequestDispatcher("/pages/user/addUser.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String userId = req.getParameter("userId");
            if (userId != null && !"".equals(userId)) {
                userService.deleteUser(userId);
            }
            req.getRequestDispatcher("/userServlet?type=0").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verifyUser(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String userId = req.getParameter("userId");
            User user1 = userService.queryUserById(userId);
            if (user1 == null) {
                resp.getWriter().print("1");
            } else {
                resp.getWriter().print("2");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void queryUsers(HttpServletRequest req, HttpServletResponse resp) {
        try {
            User user2 = new User();
            user2.setUserId(req.getParameter("uId"));
            user2.setUserName(req.getParameter("uName"));
            String gender = req.getParameter("gdr");
            if (gender == null) {
                user2.setGender(0);
            } else {
                user2.setGender(Integer.parseInt(gender));
            }
            String pageNum = req.getParameter("pageNum");
            String changeNum = req.getParameter("changeNum");

            // pl -- number of record per page, pn -- current page number, tp -- total page, tn -- total number.
            // cn -- change number or query.
            int pl = 10, pn = 1, cn = 0, tn = userService.queryUserCount(user2);   //计算页数

            int tp = tn / pl + (tn % pl == 0 ? 0 : 1);

            if (tp == 0) {
                tp = 1;
            }
            if (pageNum != null && !"".equals(pageNum)) {
                pn = Integer.parseInt(pageNum);
            }
            if (changeNum != null && !"".equals(changeNum)) {
                cn = Integer.parseInt(changeNum);
            }

            if (!(pn == 1 && cn == -1) && !(pn == tp && cn == 1)) {
                pn = pn + cn;
            }
            if (pn > tp) {
                pn = tp;
            }

            List<User> list = null;
            try {
                list = userService.queryUsers(user2, pn, pl);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            req.setAttribute("user", user2);
            req.setAttribute("pageNum", pn);
            req.setAttribute("totalPageNum", tp);
            req.setAttribute("totalNum", tn);
            req.setAttribute("userList", list);
            req.getRequestDispatcher("/pages/user/userList.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
