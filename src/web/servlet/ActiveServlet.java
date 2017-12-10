package web.servlet;

import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 激活邮箱的servlet
 * @author Administrator
 * @date 2017/12/10
 */
public class ActiveServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收激活码
        String activeCode=req.getParameter("activeCode");

        //调用service层激活
        UserService userService=new UserService();
        boolean active = userService.active(activeCode);

        if (active){
            //激活成功
            //跳转到激活成功页面
            resp.sendRedirect(req.getContextPath()+"/activeSuccess.jsp");

            //跳转到登录页面
           // resp.sendRedirect(req.getContextPath()+"/login.jsp");
        }else {

            //激活失败,跳转到失败的提示页面
            resp.sendRedirect(req.getContextPath()+"/activeFail.jsp");

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
