package service;

import dao.ProductDao;
import domain.Category;
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


}
