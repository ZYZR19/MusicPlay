package service;

import dao.UserDao;
import entity.User;

//降低耦合度 例如dao层的删除方法中调用了两个方法
// 可单独写出去在service层连接在一起 尽量将业务进行剥离
public class UserService {
    //要做好前期的准备
       public User login(User loginUser) {
           UserDao userDao = new UserDao();
           User user = userDao.login(loginUser);
           return user;
       }


}
