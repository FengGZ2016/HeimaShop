package dao;

import domain.Product;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
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
}
