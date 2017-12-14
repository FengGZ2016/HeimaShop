package web.servlet;

import com.google.gson.Gson;
import domain.Category;
import redis.clients.jedis.Jedis;
import service.ProductService;
import utils.JedisPoolUtils;

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

        //先从缓存中查询categoryList，如果缓存中有数据就直接从缓存中取数据，如果没有就从数据库中查询
        Jedis jedis= JedisPoolUtils.getJedis();
        String categoryListJson=jedis.get("categoryListJson");
        //判断缓存中是否有数据
        if (categoryListJson==null){
            System.out.println("缓存中没有数据，要查询数据库！");
            //准备分类数据
            List<Category> categoryList = service.findAllCategory();
            //将list转为json数据
            Gson gson = new Gson();
            categoryListJson= gson.toJson(categoryList);
            //把从数据库中查询到的数据存到缓存中
            jedis.set("categoryListJson",categoryListJson);

        }



        resp.getWriter().write(categoryListJson);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
