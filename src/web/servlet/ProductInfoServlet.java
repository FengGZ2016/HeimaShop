package web.servlet;

import domain.Product;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/12/15
 */
public class ProductInfoServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取当前页
        String currentPage=req.getParameter("currentPage");
        //获取商品类别id
        String cid=req.getParameter("cid");
        //获取商品的id
        String pid=req.getParameter("pid");

        ProductService productService=new ProductService();
        Product product=productService.findProductByPid(pid);

        req.setAttribute("product",product);
        req.setAttribute("currentPage",currentPage);
        req.setAttribute("cid",cid);


        //获取客户端携带cookie名称为pids
        String pids="";
        Cookie[] cookies=req.getCookies();

        if (cookies!=null){
            for (Cookie cookie:cookies){
                if ("pids".equals(cookie.getName())){
                    pids=cookie.getValue();
                    //将cookie拆分成一个数组
                    String[] split=pids.split("-");
                    List<String> asList = Arrays.asList(split);
                    LinkedList<String> linkedList=new LinkedList<>(asList);
                    //判断当前的商品id是否已经在cookie列表中
                    if (linkedList.contains(pid)){
                        linkedList.remove(pid);
                        linkedList.addFirst(pid);
                    }else {
                        linkedList.addFirst(pid);
                    }
                    //将集合[1,3,2]转为1-3-2的字符串
                    StringBuffer stringBuffer=new StringBuffer();
                    //规定只能放7个历史记录
                    for (int i=0;i<linkedList.size()&&i<7;i++){
                        stringBuffer.append(linkedList.get(i));
                        stringBuffer.append("-");
                    }
                    //去掉1-3-2-字符串后面多出来的-
                    pids=stringBuffer.substring(0,stringBuffer.length()-1);
                }
            }
        }else {
            pids=pid;

        }

        Cookie cookie=new Cookie("pids",pids);
        resp.addCookie(cookie);


        req.getRequestDispatcher("/product_info.jsp").forward(req, resp);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
