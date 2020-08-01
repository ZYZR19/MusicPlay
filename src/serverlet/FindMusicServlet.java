package serverlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MusicDao;
import entity.Music;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/findMusic")
public class FindMusicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");//req请求体设置编码
        resp.setContentType("application/json;charset=utf-8");//响应体编码
        //传到后端的musicname 这个可能在数据库中有也可能没有  如果没有默认找全部的歌曲
        String musicName =  req.getParameter("musicName");

        MusicDao musicDao = new MusicDao();//用dao层的ifmusic 和findmusic进行查找
        List<Music> musicList = new ArrayList<>();
        if (musicName !=null) {//要根据歌名进行模糊查询 且ifmusic返回的是list
           musicList =  musicDao.ifMusic(musicName);

        }else{
            //查询所有的
            musicList=musicDao.findMusic();

        }

        //把musiclist全部返回给前端

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(),musicList);//returnmap 写到响应体的流

    }
}
