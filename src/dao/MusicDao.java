package dao;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.org.apache.regexp.internal.RE;
import entity.Music;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import org.omg.CORBA.PUBLIC_MEMBER;
import sun.dc.pr.PRError;
import sun.security.pkcs11.Secmod;
import util.DBUtils;

import javax.print.DocFlavor;
import javax.xml.bind.annotation.XmlType;
import java.security.spec.PSSParameterSpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*有关音乐的数据库操作*/
public class MusicDao {


    /*1.查询全部的歌单
    * */
    public List<Music> findMusic() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Music> musicList = new ArrayList<>();
        try {
            String sql = "select * from music";
            connection = DBUtils.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {//查询音乐
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
            DBUtils.getClose(connection, ps, rs);
        }
        return musicList;
    }


    /*根据id查找音乐*/
    public  Music findMusicById(int id) {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Music music = null;
        try {
            String sql = "select * from music where id=?";
            connection = DBUtils.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
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
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            DBUtils.getClose(connection, ps, rs);
        }
        return music;
    }


    /*// 根据关键词查询歌单
    * */
    public List<Music> ifMusic(String str) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Music> musicList = new ArrayList<>();
        try {
            String sql = "select * from music where title LIKE '%" + str + "%'";
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
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            DBUtils.getClose(connection, ps, rs);

        }
        return musicList;
    }




    /*//上传音乐
    * */
    //1.上传文件本身 2,将音乐信息插入
    public  int insert(String title, String singer, String time, String url, int userid) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "insert into music(title, singer, time, url, userid) values (?,?,?,?,?)";
            connection = DBUtils.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, singer);
            ps.setString(3, time);
            ps.setString(4, url);
            ps.setInt(5, userid);
            int ret = ps.executeUpdate();//返回值代表当前收到影响的行数
            if (ret == 1) {
                return 1;//成功
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.getClose(connection, ps, rs);
        }
        return 0;

    }

    // 删除音乐
    //批量和单个
    //是根据id删除的
    public int deleteMusicById(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            String sql = "delete from music where id=?";
            connection = DBUtils.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            int ret = ps.executeUpdate();
            if (ret == 1) {
                if (findLoveMusicOnDelete(id)) {
                    int ret2 = removeLoveMusicOnDelete(id);
                    if (ret2 == 1) {
                        return 1;
                    }
                }else {
                        return 1;
                    }
                }
                } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.getClose(connection, ps, null);
        }
        return 0;
    }

    //看中间表是否有该id 的音乐数据
    public boolean findLoveMusicOnDelete(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from lovemusic where music_id = ?";
            connection = DBUtils.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.getClose(connection, ps, null);
        }
        return false;
    }

    //当删除服务器上的音乐时,同时在我喜欢的数据库中进行删除
    public int removeLoveMusicOnDelete(int music_id) {
        Connection connection = null;
        PreparedStatement ps = null;
        int ret = 0;
        try {
            String sql = "delete from  lovemusic where music_id = ?";
            connection = DBUtils.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, music_id);
            ret = ps.executeUpdate();

            if (ret == 1) {
                return ret;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.getClose(connection, ps, null);
        }
        return ret;


    }


    // 添加音乐到“喜欢”列表中 * 用户-》音乐 * 多对多 * 需要中间表
    public boolean insertLoveMusic(int userId, int musicId) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            String sql = "insert into lovemusic(user_id, music_id) values (?,?)";
            connection = DBUtils.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, musicId);
            int ret = ps.executeUpdate();
            if (ret == 1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.getClose(connection, ps, null);
        }
        return false;
    }

    //移除当前用户喜欢的这首音乐 返回受影响的行数
    public int removeLoveMusic(int userId, int musicId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "delete from lovemusic where user_id=? and music_id = ?";
            connection = DBUtils.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, musicId);
            int ret = ps.executeUpdate();
            if (ret == 1) {
                return ret;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.getClose(connection, ps, rs);
        }
        return 0;
    }

    //判断音乐是否存在
    public boolean findMusicByMusicId(int user_id, int musicId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "select * FROM lovemusic WHERE  music_id=? AND user_id=? ";
            connection = DBUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, musicId);
            preparedStatement.setInt(2, user_id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.getClose(connection, preparedStatement, resultSet);
        }
        return false;
    }

    //查询用户喜欢的全部歌单
    public List<Music> findLoveMusic(int user_Id) {
        List<Music> musics = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //String sql = "select * from music where id in (select music_id from lovemusic where user_id=?)";
            String sql = "select m.id as music_id,title,singer,time,url,userid from lovemusic lm,music m where lm.music_id=m.id and user_id=?";

            connection = DBUtils.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, user_Id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Music music = new Music();
                music.setId(rs.getInt("music_id"));
                music.setTitle(rs.getString("title"));
                music.setSinger(rs.getString("singer"));
                music.setTime(rs.getDate("time"));
                music.setUrl(rs.getString("url"));
                music.setUserid(rs.getInt("userid"));
                musics.add(music);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.getClose(connection, ps, rs);
        }
        return musics;
    }


    //根据关键字查询喜欢的歌单
    public List<Music> ifMusicLove(String str, int user_id) {
               Connection connection = null;
               PreparedStatement ps = null;
               ResultSet rs = null;
               List<Music> musicList = new ArrayList<>() ;
               try{
                   String  sql = "select * from music where id = " +
                           "(select music_id from lovemusic where user_id=? and title like '%"+str+"%')";
                   connection = DBUtils.getConnection();
                   ps = connection.prepareStatement(sql);
                   ps.setInt(1,user_id);
                   rs=ps.executeQuery();
                   while (rs.next()) {
                       Music music = new Music();
                       music.setId(rs.getInt("music_id"));
                       music.setTitle(rs.getString("title"));
                       music.setSinger(rs.getString("singer"));
                       music.setTime(rs.getDate("time"));
                       music.setUrl(rs.getString("url"));
                       music.setUserid(rs.getInt("userid"));
                       musicList.add(music);
                   }


               }catch(SQLException e) {
                   e.printStackTrace();
               }finally {
                    DBUtils.getClose(connection,ps,rs);
               }
               return  musicList;
    }

    public static void main(String[] args) {
         /*List<Music> musicList = ifMusicLove("蝴蝶",1);
        System.out.println(musicList);*/
    }


}

