package web.servlet;

import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import service.UserService;
import utils.CommonsUtils;
import utils.MailUtils;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Administrator
 * @date 2017/12/9
 */
public class RegisterServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        //获取请求参数
        Map<String, String[]> parameterMap = req.getParameterMap();
        User user=new User();

        try {
            //指定一个类型转换器,将String转为Date
            ConvertUtils.register(new Converter() {
                @Override
                public Object convert(Class clazz, Object value) {
                    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                    Date parse=null;
                    try {
                        parse=format.parse(value.toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return parse;
                }
            }, Date.class);

            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //private String uid;
        user.setUid(CommonsUtils.getUUID());
        //private String telephone;
        user.setTelephone(null);
        //private int state;//是否激活
        user.setState(0);
        //private String code;//激活码
        String activeCode = CommonsUtils.getUUID();
        user.setCode(activeCode);

        //调用Service层注册
        UserService userService=new UserService();
        boolean isRegisterSuccess=userService.register(user);

        if (isRegisterSuccess){
            //注册成功,发送激活邮箱
            String emailMsg = "恭喜您注册成功，请点击下面的连接进行激活账户"
                    + "<a href='http://localhost:8080/HeimaShop/active?activeCode="+activeCode+"'>"
                    + "http://localhost:8080/HeimaShop/active?activeCode="+activeCode+"</a>";

            try {
                MailUtils.sendMail(user.getEmail(),emailMsg);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            //跳转到注册成功页面
            resp.sendRedirect(req.getContextPath()+"/registerSuccess.jsp");



        }else {
            //注册失败,跳转到失败的提示页面
            resp.sendRedirect(req.getContextPath()+"/registerFail.jsp");
        }


    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doGet(req,resp);
    }
}
