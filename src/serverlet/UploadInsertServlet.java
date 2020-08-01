package serverlet;
//将音乐信息写入到数据库

import dao.MusicDao;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/uploadsucess")
public class UploadInsertServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");//响应体编码

        String singer = req.getParameter("singer" );
        String fileName= (String) req.getSession().getAttribute("fileName");
        //拿到的是.map
        String [] string = fileName.split("\\.");
        String title = string[0];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
        String time =simpleDateFormat.format(new Date());

        User user = (User) req.getSession().getAttribute("user");
        int user_id = user.getId();
        String url = "music/"+ title;

        MusicDao musicDao = new MusicDao();
        int ret = musicDao.insert(title,singer,time,url,user_id);
        if (ret==1) {
            resp.sendRedirect("list.html");
        }




    }
}
