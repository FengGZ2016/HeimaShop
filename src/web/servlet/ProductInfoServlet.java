package web.servlet;

import domain.Product;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        req.getRequestDispatcher("/product_info.jsp").forward(req, resp);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
