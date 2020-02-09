package web.servlet;


import bean.User;
import dao.UserDao;
import dao.impl.UserDaoImpl;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author gyy
 * @date 2020/2/7 - 11:46
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        //获取生成的验证码
        HttpSession session = req.getSession();
        String check_session = (String) session.getAttribute("check_session");
        //删除session中存储的验证码（安全性考虑）,确保一次性
        session.removeAttribute("check_session");
        //获取用户输入的验证码
        String checkCode = req.getParameter("checkCode");
        //判断验证码是否正确
        if(check_session != null && check_session.equalsIgnoreCase(checkCode)){
            //验证码正确
            //判断用户名和密码是否一致
            //获取用户登录信息，存储到loginUser对象中
            System.out.println("验证码正确");
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            User loginUser = new User();
            loginUser.setUsername(username);
            loginUser.setPassword(password);
            System.out.println("loginUser:" + loginUser);
            //创建UserService对象
            UserService userService = new UserServiceImpl();
            //查询是否存在该对象
            User user = userService.findOne(loginUser);
            if(user != null){
                //登陆成功
                System.out.println("登陆成功");
                System.out.println("user:" + user);
                //存储数据
                session.setAttribute("user", user);
                //重定向
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
            }else {
                //登陆失败
                System.out.println("登陆失败,用户名或密码错误");
                req.setAttribute("loginError", "用户名或密码错误");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        }else {
            //验证码错误
            //转发
            System.out.println("验证码错误");
            req.setAttribute("loginError", "验证码错误");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }





    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
