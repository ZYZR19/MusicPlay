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
import java.util.List;

@WebServlet("/findLoveMusic")
public class FindLoveMusicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        String str = req.getParameter("loveMusicName");
        System.out.println("loveMusicName:" +str);
        User user = (User) req.getSession().getAttribute("user");
        int user_id = user.getId();
        MusicDao musicDao = new MusicDao();
        List<Music> musics = null;
        if (str!=null) {
            musics = musicDao.ifMusicLove(str,user_id);

        }else {
            musics = musicDao.findLoveMusic(user_id);
        }
        for (Music music:musics) {
            System.out.println(music.getUrl());

        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(),musics);
    }
}
