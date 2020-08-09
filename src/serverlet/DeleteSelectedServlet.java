package serverlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MusicDao;
import entity.Music;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/deleteSelMusicServlet")
//获取前端选中的id数组
public class DeleteSelectedServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset = utf-8");

        String [] values = req.getParameterValues("id[]");
        System.out.println("deleteServlet :" + Arrays.toString(values));
        //删除掉
        int sum = 0;
        Map<String,Object> map = new HashMap<>();

        MusicDao musicDao = new MusicDao();
        for (int i=0;i<values.length ; i++) {//遍历删除
              int j = Integer.parseInt(values[i]);//把字符串转换成int
            System.out.println("选中音乐的id" +j);
            //调用dao层删除
            Music music = musicDao.findMusicById(j);//先查找后删除
            int delete = musicDao.deleteMusicById(j);
            //sum=sum+delete
            if (delete==1) {
                //数据库删除之后检查服务器否存在
                File file  = new File("F:\\javacode1\\MusicPlay\\web\\"+music.getUrl()+".mp3");
                System.out.println("文件是否存在:" +file.exists());
                System.out.println("file" + file);
                if (file.delete()) {
                    //删除成功

                    System.out.println("文件名" + file.getName());
                    System.out.println("删除文件成功");
                    sum = sum+delete;
                }else {
                    System.out.println("文件名" + file.getName());
                    System.out.println("删除文件失败");
                }
            }
;
        }
        System.out.println("sum:" +sum);
        //sum = values.length 说明选中元素删除成功
        if (sum==values.length) {
            map.put("msg",true);
        }else {
            map.put("msg",false);
        }
        //将map转化成json 写回给前端
        ObjectMapper mapper =new ObjectMapper();
        mapper.writeValue(resp.getWriter(),map);

    }
}
