package web.servlet;

import domain.PageBean;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        req.setAttribute("pageBean",pageBean);
        req.setAttribute("cid", cid);

        req.getRequestDispatcher("/product_list.jsp").forward(req,resp);


    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
