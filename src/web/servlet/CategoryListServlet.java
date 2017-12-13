package web.servlet;

import com.google.gson.Gson;
import domain.Category;
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
public class CategoryListServlet extends HttpServlet{


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        ProductService service = new ProductService();
        //准备分类数据
        List<Category> categoryList = service.findAllCategory();
        //将list转为json数据
        Gson gson = new Gson();
        String json = gson.toJson(categoryList);

        resp.getWriter().write(json);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
