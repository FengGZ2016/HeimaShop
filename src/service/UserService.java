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


    /**
     * 邮箱激活
     * @param activeCode
     */
    public boolean active(String activeCode) {
        //调用dao层激活
        UserDao userDao=new UserDao();
        int row=0;
        try {
            row= userDao.active(activeCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return row>0?true:false;
    }


    /**
     * 检查用户是否已存在
     * @param username
     * @return
     */
    public boolean checkUserName(String username) {
        UserDao userDao=new UserDao();
        Long isExist=0L;

        try {
            isExist=userDao.checkUserName(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isExist>0?true:false;

    }
}
