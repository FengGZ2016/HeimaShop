package web.servlet;

import com.google.gson.Gson;
import domain.Category;
import domain.PageBean;
import domain.Product;
import redis.clients.jedis.Jedis;
import service.ProductService;
import utils.JedisPoolUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * 抽取servlet
 *
 * @author Administrator
 * @date 2017/12/26
 */
public class ProductServlet extends BaseServlet{

    /*
    private final String PRODUCT_LIST="productList";
    private final String CATEGORY_LIST="categoryList";
    private final String INDEX="index";
    private final String PRODUCT_INFO="productInfo";
*/

   /* @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取函数名
        String method=req.getParameter("method");
        if (PRODUCT_LIST.equals(method)){
            productList(req,resp);
        }else if (CATEGORY_LIST.equals(method)){
            categoryList(req,resp);
        }else if (INDEX.equals(method)){
            index(req,resp);
        }else if (PRODUCT_INFO.equals(method)){
            productInfo(req,resp);
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }*/


    /**
     * 显示首页的函数
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ProductService productService=new ProductService();
        //准备热门商品
        List<Product> hotProductList=productService.findHotProductList();

        //准备最新商品
        List<Product> newProductList = productService.findNewProductList();

        req.setAttribute("hotProductList",hotProductList);
        req.setAttribute("newProductList",newProductList);

        req.getRequestDispatcher("/index.jsp").forward(req,resp);

    }


    /**
     * 显示商品的类别
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void categoryList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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


    /**
     * 根据商品的类别显示商品的列表
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void productList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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


    /**
     * 显示商品的详细信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void productInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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
}
