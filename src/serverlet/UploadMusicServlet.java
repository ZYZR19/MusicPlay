package serverlet;

import entity.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

//本地上传后还要上传到数据库 跳转到uploadsucess.html这个页面 然后输入歌手名提交

public class UploadMusicServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
       User user  = (User) req.getSession().getAttribute("user");
     if (user==null) {
         System.out.println("请登录后再上传音乐!");
         resp.getWriter().write("<h2>请登录后再上传"+"</h2>");

         return;
     }else {
         //上传
         FileItemFactory factory = new DiskFileItemFactory();
         ServletFileUpload upload = new ServletFileUpload();
         List<FileItem> fileItems = null;//可会批量上传
         try{
             fileItems = upload.parseRequest(req);

         }catch (FileUploadException e) {
             e.printStackTrace();
             return;
         }
         System.out.println("fileItems"+fileItems);
         FileItem fileItem = fileItems.get(0);
         System.out.println("fileItem"+fileItem);
         //fileItem.write(new File());
     }

     //固定模式

    }
}
