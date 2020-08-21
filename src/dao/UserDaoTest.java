package dao;

import entity.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDaoTest {

    @Test
    public void login() {
        //登录操作（查找数据库中的用户）
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUsername("bit");
        user.setPassword("123");
        User  loginUser =userDao.login(user);
        System.out.println(loginUser);

    }

    @Test
    public void add() {
        //注册，插入新用户
        UserDao userDao  = new UserDao();
        User user = new User();
        user.setUsername("jenny");
        user.setPassword("123");
        boolean flag = userDao.add(user);
        System.out.println(flag);

    }

    @Test
    public void selectByName() {
      //按照名字查找用户看用户是否存在（用于注册功能）
        UserDao userDao = new UserDao();
        boolean ret = userDao.selectByName("jenny");
        System.out.println(ret);
    }
}