package serverlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.regexp.internal.RE;
import dao.MusicDao;
import entity.Music;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/deleteServlet")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        String idStr = req.getParameter("id");//获取请求体传过来的参数
        int id = Integer.parseInt(idStr);//拿到的是字符串要转化成int

        MusicDao musicDao = new MusicDao() ;//调用到层删
        //删除之前先查找是否有
        Music music = musicDao.findMusicById(id);
         if(music ==null) {
             return;
         }

       int delete  =  musicDao.deleteMusicById(id);
        Map<String,Object> return_map = new HashMap<>();
         if (delete==1) {
             //数据库删除 服务器上的还在
             //如何去 删除文件
             File file = new File("/root/java/apache-tomcat-8.5.57/webapps/musicplay/"+ music.getUrl()+".mp3");//拼接当前音乐的url
             if (file.delete()) {//io流返回的是布尔类型的
                 //删除成功需要写到map中
                 return_map.put("msg",true);
                 System.out.println("服务器删除成功");

             }else{
                 return_map.put("msg",false);
                 System.out.println("服务器删除失败");//后端显示

             }
             }else {
             return_map.put("msg",false);//删除失败

         }
         ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(),return_map);//写回给前端







        /*System.out.println("删除指定音乐！");
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        Map<String ,Object> return_map = new HashMap<>();
        String strId = req.getParameter("id");
        int id = Integer.parseInt(strId);
        System.out.println("id:"+ id);

        MusicDao musicDao = new MusicDao();
        Music music = musicDao.findMusicById(id);
        if(music == null){
            return;
        }
        //2、如果有就开始删除库中的音乐
        int delete = musicDao.deleteMusicById(id);
        System.out.println("delete:"+delete);
        if(delete == 1){
            File file = new File("F:\\javacode1\\MusicPlay\\web\\"+music.getUrl()+".mp3");
            System.out.println("文件是否存在："+file.exists());
            System.out.println("file:"+file);
            if(file.delete()){
                //证明删除成功
                return_map.put("msg",true);
                System.out.println("服务器删除文件成功！");
            }else {
                return_map.put("msg",false);
                System.out.println("文件名："+file.getName());
                System.out.println("服务器删除文件失败！");
            }
        }else {
            return_map.put("msg",false);
            System.out.println("数据库数据删除失败！");
        }

        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(resp.getWriter(),return_map);*/
    }
}
