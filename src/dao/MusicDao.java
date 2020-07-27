package dao;

import com.sun.org.apache.regexp.internal.RE;
import entity.Music;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import sun.dc.pr.PRError;
import util.DBUtils;

import javax.print.DocFlavor;
import java.security.spec.PSSParameterSpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//有关音乐的数据库操作
public class MusicDao {
    //1.7uuu查询全部的歌单
    public static List<Music>  findMusic() {
        Connection connection = null;
        PreparedStatement ps = null ;
        ResultSet  rs = null;
        List<Music> musicList = new ArrayList<>();
        try{
            String sql ="select * from music";
            connection =DBUtils.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {//查询音乐
                   Music music = new Music();
                   music.setId(rs.getInt("id"));
                   music.setSinger(rs.getString("singer"));
                   music.setTime(rs.getDate("time"));
                   music.setTitle(rs.getString("title"));
                   music.setUrl(rs.getString("url"));
                   music.setUserid(rs.getInt("userid"));
                  musicList.add(music);//所有的歌都放到musiclist中
            }
    } catch (SQLException e) {
            e.printStackTrace();
        } finally {
       DBUtils.getClose(connection,ps,rs);
        }
        return musicList;
        }





        /*根据id查找音乐*/
    public static  Music findMusicById (int id ) {

        Connection connection = null;
        PreparedStatement ps  = null;
        ResultSet rs = null;
        Music music = null;
        try {
          String sql = "select * from music where id=?";
          connection = DBUtils.getConnection();
          ps = connection.prepareStatement(sql);
          ps.setInt(1,id);
          rs = ps.executeQuery();
          if (rs.next()) {
              music = new Music();
              music.setId(rs.getInt("id"));
              music.setSinger(rs.getString("singer"));
              music.setTime(rs.getDate("time"));
              music.setTitle(rs.getString("title"));
              music.setUrl(rs.getString("url"));
              music.setUserid(rs.getInt("userid"));

          }
        }catch (SQLException e) {
            e.printStackTrace();

        }finally {
          DBUtils.getClose(connection,ps,rs);
        }
        return music;
    }






    // 根据关键词查询歌单
    public static  List<Music> ifMusic (String str) {
        Connection connection =null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<Music> musicList = new ArrayList<>();
        try {
            String sql ="select * from music where title LIKE '%" +str+ "%'";
            connection = DBUtils.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Music music = new Music();
                music.setId(rs.getInt("id"));
                music.setSinger(rs.getString("singer"));
                music.setTime(rs.getDate("time"));
                music.setTitle(rs.getString("title"));
                music.setUrl(rs.getString("url"));
                music.setUserid(rs.getInt("userid"));
                musicList.add(music);

            }
        }catch (SQLException e) {
            e.printStackTrace();

        }finally {
            DBUtils.getClose(connection,ps,rs);

        }
        return  musicList;
    }
    public static void main(String[] args) {
        List<Music> musicList = ifMusic("蝴蝶");
        System.out.println(musicList);
        Insert("花花的世界","大艺术家","2020-07-27","music\\花花的世界",2);

        }





        //上传音乐
    //1.上传文件本身 2,将音乐信息插入
     public static int Insert(String title, String singer, String time, String url,int userid) {
         Connection connection = null;
         PreparedStatement ps = null;
         ResultSet rs = null;
         try {
           String sql = "insert into music(title, singer, time, url, userid) values (?,?,?,?,?)";
           connection = DBUtils.getConnection();
           ps = connection.prepareStatement(sql);
           ps.setString(1,title);
             ps.setString(2,singer);
             ps.setString(3,time);
             ps.setString(4,url);
             ps.setInt(5,userid);
             int ret =  ps.executeUpdate();//返回值代表当前收到影响的行数
             if (ret==1) {
                 return 1;//成功
             }

         }catch (SQLException e){
            e.printStackTrace();
         }finally {
        DBUtils.getClose(connection,ps,rs);
         }
         return 0;

    }
 }
