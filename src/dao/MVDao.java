package dao;

import entity.MV;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import util.DBUtils;

import java.security.spec.PSSParameterSpec;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MVDao {

    /**     * 查询全部mv     */
    public List<MV> findMv(){
  List<MV> MVs = new ArrayList<>();
        Connection connection =null;
        PreparedStatement ps = null;
        ResultSet rs =null;
        try{
            connection = DBUtils.getConnection();
            ps=connection.prepareStatement("SELECT * FROM mv");
            rs=ps.executeQuery();
            while(rs.next()) {
                MV mv = new MV();
                mv.setId(rs.getInt("id"));
                mv.setTitle(rs.getString("title"));
                mv.setProducer(rs.getString("producer"));
                mv.setTime(rs.getDate("time"));
                mv.setUrl(rs.getString("url"));
                mv.setUserid(rs.getInt("userid"));
            }
            }catch (SQLException e) {
        e.printStackTrace();
        }finally {
     DBUtils.getClose(connection,ps,rs);
        }

             return MVs;
    }





/** * 根据id查找音乐 *
 *  @param id *
 * @return */
        public MV findMvById (int id) {
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            MV mv = null;
            try{
                connection=DBUtils.getConnection();
                ps=connection.prepareStatement("select * from mv where id = ?");
                ps.setInt(1,id);
                rs=ps.executeQuery();
                if (rs.next()) {//查找到的是一个用if
                     mv = new MV();
                    mv.setId(rs.getInt("id"));
                    mv.setTitle(rs.getString("title"));
                    mv.setProducer(rs.getString("producer"));
                    mv.setTime(rs.getDate("time"));
                    mv.setUrl(rs.getString("url"));
                    mv.setUserid(rs.getInt("userid"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                DBUtils.getClose(connection,ps,rs);
            }
            return mv;

         }



         // 根据关键字查询mv 模糊匹配
    public List<MV> ifMv (String str) {
        List<MV> MVs = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            connection = DBUtils.getConnection();
            ps = connection.prepareStatement("select * from mv where title like '%"+str+ "%'");
          rs = ps.executeQuery();
          if (rs.next()) {
              MV mv = new MV();
              mv.setId(rs.getInt("id"));
              mv.setTitle(rs.getString("title"));
              mv.setProducer(rs.getString("producer"));
              mv.setTime(rs.getDate("time"));
              mv.setUrl(rs.getString("url"));
              mv.setUserid(rs.getInt("userid"));
          }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.getClose(connection,ps,rs);
        }
        return MVs;
    }


    //上传mv
    public  int insert(String title, String producer, String time, String url, int userid) {
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                String sql = "insert into mv(title, producer, time, url, userid) values (?,?,?,?,?)";
                connection = DBUtils.getConnection();
                ps = connection.prepareStatement(sql);
                ps.setString(1, title);
                ps.setString(2, producer);
                ps.setString(3, time);
                ps.setString(4, url);
                ps.setInt(5, userid);
                int ret = ps.executeUpdate();//返回值代表当前收到影响的行数
                if (ret == 1) {
                    return 1;//成功
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                DBUtils.getClose(connection,ps,rs);
            }
         return 0;
    }//上传到数据库中


    //删除mv
      public  int deleteMvById (int id) {
            Connection connection =null;
            PreparedStatement ps = null;
            try{
                connection = DBUtils.getConnection();
                ps = connection.prepareStatement("delete from mv where id=?");
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                DBUtils.getClose(connection,ps,null);
            }
            return 0;
      }



}
