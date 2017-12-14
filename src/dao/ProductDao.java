package dao;

import domain.Category;
import domain.Product;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/12/13
 */
public class ProductDao {

    /**
     * 获取热门商品
     * @return
     */
    public List<Product> findHotProductList() throws SQLException {

        QueryRunner queryRunner=new QueryRunner(DataSourceUtils.getDataSource());
        String sqlStr="select * from product where is_hot=? limit ?,?";
        List<Product> list = queryRunner.query(sqlStr, new BeanListHandler<Product>(Product.class), 1, 0, 9);

        return list;
    }


    /**
     * 获取最新的商品
     * @return
     */
    public List<Product> findNewProductList() throws SQLException {
        QueryRunner queryRunner=new QueryRunner(DataSourceUtils.getDataSource());
        String sqlStr="select * from product order by pdate desc limit ?,?";
        List<Product> list = queryRunner.query(sqlStr, new BeanListHandler<Product>(Product.class),  0, 9);

        return list;

    }


    /**
     * 查询所有商品分类
     * @return
     */
    public List<Category> findAllCategory() throws SQLException {
        QueryRunner queryRunner=new QueryRunner(DataSourceUtils.getDataSource());
        String sqlStr="select * from category";
        List<Category> categoryList = queryRunner.query(sqlStr, new BeanListHandler<Category>(Category.class));

        return categoryList;
    }

    /**
     * 返回商品的总数
     * @param cid
     * @return
     */
    public int getCount(String cid) throws SQLException {
        QueryRunner queryRunner=new QueryRunner(DataSourceUtils.getDataSource());
        String sqlStr="select count(*) from product where cid=?";
        Long query = (Long) queryRunner.query(sqlStr, new ScalarHandler(), cid);

        return query.intValue();
    }


    /**
     * 返回当前页要显示的数据
     * @param cid
     * @param index
     * @param currentCount
     * @return
     */
    public List<Product> findProductByPage(String cid, int index, int currentCount) throws SQLException {
    QueryRunner queryRunner=new QueryRunner(DataSourceUtils.getDataSource());
    String sqlStr="select * from product where cid=? limit ?,?";

        List<Product> query = queryRunner.query(sqlStr, new BeanListHandler<Product>(Product.class), cid, index, currentCount);

        return query;
    }
}
