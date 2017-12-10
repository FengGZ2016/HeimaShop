package service;

import dao.UserDao;
import domain.User;

import java.sql.SQLException;

/**
 *
 * @author Administrator
 * @date 2017/12/9
 */
public class UserService {


    /**
     * 注册
     * @param user
     * @return
     */
    public boolean register(User user) {

        //调用dao层注册
        UserDao userDao=new UserDao();
        int row=0;
        try {
            row=userDao.register(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return row>0?true:false;
    }
}
