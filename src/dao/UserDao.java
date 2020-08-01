package dao;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import entity.User;
import util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//有关用户相关的数据库操作
public class UserDao {
    //登录操作
     public  User login(User loginUser) {
         User  user =null;
         Connection connection = null;//获取连接
         PreparedStatement ps = null;//预编译
         ResultSet rs = null;//拿到结果集的集合
         try {
             String sql = "select  *  from user where username=? and password=?";
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
               return  user;
           }
         } catch (Exception e) {
             e.printStackTrace();
         }finally {
             DBUtils.getClose(connection,ps,rs);
         }
         return  user;

     }

     //注册操作
    public boolean add(User user) {
         Connection connection = null;
         PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String sql = "insert into  user (username,password)  values (?,?)";
            connection = DBUtils.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            int ret = ps.executeUpdate();
            if (ret ==1) {
                System.out.println("插入新用户成功");
                return true;
            }
            System.out.println("插入新用户失败");
            } catch(SQLException e) {
              e.printStackTrace();
        }finally {
          DBUtils.getClose(connection,ps,rs);
        }
        return false;
    }


        //按照名字查找用户
    public boolean selectByName (String username) {
         Connection connection =null;
         PreparedStatement ps = null;
         ResultSet rs = null;
         try{
             String sql = "select * from  user where username = ?";
             connection = DBUtils.getConnection();
             ps = connection.prepareStatement(sql);
             ps.setString(1,username);
             rs = ps.executeQuery();
             if (rs.next()) {
                 return true;//用户已经存在
             }
         }catch(SQLException e) {
         e.printStackTrace();
         }finally {
        DBUtils.getClose(connection,ps,rs);
         }
         return false;
    }

  public static void main(String[] args) {
        /*User user = new User();
        user.setUsername("bit");
        user.setPassword("123");
        User loginUser = login(user);
        System.out.println(loginUser);*/
    }
}
