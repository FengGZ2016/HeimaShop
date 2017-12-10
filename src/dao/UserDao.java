package dao;

import domain.User;
import org.apache.commons.dbutils.QueryRunner;
import utils.DataSourceUtils;

import java.sql.SQLException;

/**
 *
 * @author Administrator
 * @date 2017/12/9
 */
public class UserDao {

    /**
     *
     * @param user
     * @return
     */
    public int register(User user) throws SQLException {

        QueryRunner queryRunner=new QueryRunner(DataSourceUtils.getDataSource());
        String sqlStr="insert into user values(?,?,?,?,?,?,?,?,?,?)";
        int row=queryRunner.update(sqlStr,user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode());

        return row;
    }
}
