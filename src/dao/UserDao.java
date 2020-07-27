package dao;

import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;
import entity.User;
import util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//有关用户相关的数据库操作
public class UserDao {
     public static User login(User loginUser) {
         User  user =null;
         Connection connection = null;//获取连接
         PreparedStatement ps = null;//预编译
         ResultSet rs = null;//拿到结果集的集合

         String sql = null;
         try {
             sql = "select  *  from user where username=? and password=?";
             connection = DBUtils.getConnection();
             ps = connection.prepareStatement(sql);
             ps.setString(1,loginUser.getUsername());
             ps.setString(2,loginUser.getPassword());
             //执行sql
           rs =   ps.executeQuery();
           if(rs.next()) {//如果查询的结果有很多条 要用while
               user = new User();
               user.setId(rs.getInt("id"));
               user.setUsername(rs.getString("username"));
               user.setPassword(rs.getString("password" ));
               user.setAge(rs.getInt("age" ));
               user.setGender(rs.getString("gender"));
               user.setEmail(rs.getString("email"));
               return  user;
           }
         } catch (Exception e) {
             e.printStackTrace();
         }finally {
             DBUtils.getClose(connection,ps,rs);
         }
         return  user;

     }

    public static void main(String[] args) {
        User user = new User();
        user.setUsername("bit");
        user.setPassword("123");
        User loginUser = login(user);
        System.out.println(loginUser);
    }
}
