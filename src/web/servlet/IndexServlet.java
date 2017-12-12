package web.servlet;

import domain.Product;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/12/13
 */
public class IndexServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ProductService productService=new ProductService();
        //准备热门商品
        List<Product> hotProductList=productService.findHotProductList();

        //准备最新商品
        List<Product> newProductList = productService.findNewProductList();

        req.setAttribute("hotProductList",hotProductList);
        req.setAttribute("newProductList",newProductList);

        req.getRequestDispatcher("/index.jsp").forward(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
