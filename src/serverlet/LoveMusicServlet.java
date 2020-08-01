package serverlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MusicDao;
import entity.Music;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//添加喜欢的音乐
@WebServlet("/loveMusicServlet")
public class LoveMusicServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json,charset=utf-8");
        String strId = req.getParameter("id");
        int musicId = Integer.parseInt(strId);
        System.out.println("musicID" + musicId);

        User user = (User) req.getSession().getAttribute("user");
     int user_id = user.getId();
        MusicDao musicDao = new MusicDao();

        Map<String,Object> map = new HashMap<>();
        //添加之前需要查看该音乐是否已经被添加到喜欢列表
        boolean effect = musicDao.findMusicByMusicId(user_id,musicId);
        if (effect) {
            map.put("msg",false);//有
        }else {
            boolean flg = musicDao.insertLoveMusic(user_id,musicId);
            if (flg) {
                map.put("msg",true);
            }else {
                map.put("msg",false);
            }
        }
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(resp.getWriter(),map);
    }
}
