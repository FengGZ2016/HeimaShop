package service;

import dao.ProductDao;
import domain.Category;
import domain.PageBean;
import domain.Product;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/12/13
 */
public class ProductService {


    /**
     *
     * 获得热门商品
     * @return
     */
    public List<Product> findHotProductList() {
        ProductDao productDao=new ProductDao();
        List<Product> productList=null;

        try {
            productList=productDao.findHotProductList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }


    /**
     * 获取最新的商品
     * @return
     */
    public List<Product> findNewProductList() {
        ProductDao productDao=new ProductDao();
        List<Product> productList=null;

        try {
            productList=productDao.findNewProductList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;

    }


    /**
     * 查询所有产品分类
     * @return
     */
    public List<Category> findAllCategory() {

        ProductDao productDao=new ProductDao();
        List<Category> categoryList=null;

        try {
            categoryList=productDao.findAllCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryList;
    }


    /**
     * 返回分页商品数据
     * @param cid
     * @return
     */
    public PageBean findProductListByCid(String cid,int currentPage,int currentCount) {
        ProductDao productDao=new ProductDao();
        //封装一个PageBean，返回web层
        PageBean<Product> pageBean=new PageBean<>();

        //封装当前页
        pageBean.setCurrentPage(currentPage);
        //封装每页显示的条数
        pageBean.setCurrentCount(currentCount);
        //封装总条数
        int totalCount=0;

        try {
            totalCount=productDao.getCount(cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pageBean.setTotalCount(totalCount);
        //封装总页数
        int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
        pageBean.setTotalPage(totalPage);
        //封装当前页要显示的数据

        //当前页与起始索引index的关系
        int index=(currentPage-1)*currentCount;
        List<Product> list=null;

        try {
            list=productDao.findProductByPage(cid,index,currentCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pageBean.setList(list);

        return pageBean;

    }
}
