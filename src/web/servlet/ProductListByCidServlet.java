package web.servlet;

import domain.PageBean;
import domain.Product;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/12/14
 */
public class ProductListByCidServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      //获得cid
        String cid=req.getParameter("cid");
        String currentPageStr=req.getParameter("currentPage");
        if (currentPageStr==null){
            currentPageStr="1";
        }

        int currentPage = Integer.parseInt(currentPageStr);
        int currentCount = 12;
        ProductService productService=new ProductService();
        PageBean pageBean = productService.findProductListByCid(cid,currentPage,currentCount);


        //定义一个商品信息历史记录的集合
        List<Product> historyProductList=new ArrayList<>();
        //获取客户端携带名称叫pids的cookie
        Cookie[] cookies=req.getCookies();
        if (cookies!=null){
            for (Cookie cookie:cookies){
                if ("pids".equals(cookie.getName())){
                    //3-1-2
                    String pids=cookie.getValue();
                    //拆分成数组{3,2,1}
                    String[] split = pids.split("-");
                    //遍历cookie数组，根据商品id获取所有的商品信息
                    for (String pid:split){
                        Product product=productService.findProductByPid(pid);
                        //将商品添加到记录浏览记录的集合中
                        historyProductList.add(product);
                    }
                }
            }
        }

        //将历史记录集合放到请求域中
        req.setAttribute("historyProductList",historyProductList);

        req.setAttribute("pageBean",pageBean);
        req.setAttribute("cid", cid);

        req.getRequestDispatcher("/product_list.jsp").forward(req,resp);


    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
