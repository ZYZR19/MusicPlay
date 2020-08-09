package serverlet;

import entity.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

//本地上传后还要上传到数据库 跳转到uploadsucess.html这个页面 然后输入歌手名提交
@WebServlet("/upload")
public class UploadMusicServlet extends HttpServlet {
    private final  String SAVEPATH ="/root/java/apache-tomcat-8.5.57/webapps/musicplay/music";//定义一个路径要把music写进music
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        /*resp.setContentType("application/json;charset=utf-8");*/
        //定义一个路径要把music写进music

       User user  = (User) req.getSession().getAttribute("user");
      if (user==null) {
         System.out.println("请登录后再上传音乐!");
         resp.getWriter().write("<h2>请登录后再上传音乐"+"</h2>");

         return;
     }else {
         //上传
         FileItemFactory factory = new DiskFileItemFactory();//工厂一个接口两个类
         ServletFileUpload upload = new ServletFileUpload (factory);
         List<FileItem> fileItems = null;//可会批量上传所以用list
         try{
             fileItems = upload.parseRequest(req);
             }catch (FileUploadException e) {
             e.printStackTrace();
             return;
         }
         System.out.println("fileItems"+ fileItems);
         FileItem fileItem = fileItems.get(0);
         System.out.println("fileItem :"+ fileItem);
         String fileName = fileItem.getName();//当前上传的文件名
          req.getSession().setAttribute("fileName",fileName);
          try {
              fileItem.write(new File(SAVEPATH,fileName));
          } catch (Exception e) {
              e.printStackTrace();
              return;
          }
          //2.上传到数据库中
          resp.sendRedirect("uploadsucess.html");
      }



    }
}
