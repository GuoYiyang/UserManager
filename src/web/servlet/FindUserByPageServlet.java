package web.servlet;

import bean.PageBean;
import bean.User;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author gyy
 * @date 2020/2/9 - 16:06
 */
@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");

        //获取当前页码
        String currentPage = request.getParameter("currentPage");
        //获取每页要展示的行数
        String rows = request.getParameter("rows");

        if(currentPage == null || "".equals(currentPage) || Integer.parseInt(currentPage) <= 0){
            currentPage = "1";
            rows = "5";
        }
        if(rows == null || "".equals(rows)){
            currentPage = "1";
            rows = "5";
        }

        //获取条件查询的参数
        Map<String, String[]> condition_map = request.getParameterMap();



        //调用service进行查询
        UserService userService = new UserServiceImpl();
        PageBean<User> pb = userService.findUserByPage(currentPage, rows, condition_map);

        int totalPage = pb.getTotalPage();
        if(Integer.parseInt(currentPage) >= totalPage){
            pb.setCurrentPage(totalPage );
        }



        //存储PageBean对象
        request.setAttribute("pb", pb);

        //存储map查询条件
        request.setAttribute("condition", condition_map);

        //转发到list.jsp
        request.getRequestDispatcher("/list.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
