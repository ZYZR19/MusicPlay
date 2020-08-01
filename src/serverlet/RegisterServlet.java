package serverlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UserDao;
import entity.User;
import javafx.print.PaperSource;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        //把前端获取到的user 和 password 给后端 写入数据库
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        System.out.println("用户名:" + username+ " 密码:" + password);
        //构造一个user
        User  registerUser= new User();
        registerUser .setPassword(password);
        registerUser .setUsername(username);
        UserDao userDao = new UserDao();
        boolean effect =  userDao.selectByName(username);
        Map<String,Object> return_map = new HashMap<>();//returnmap就是返回给前端的
        //登录成功还是失败
        if (effect) {
            System.out.println("注册失败!");//登录成功后要响应一个map表 转换成json
            //登录成功就将用户信息写入session
            //绑定数据
            return_map.put("msg",false);
        } else{
            boolean flg = userDao.add(registerUser);
            if (flg) {
                System.out.println("注册成功!");
                return_map.put("msg",true);
            }else{
                return_map.put("msg",false);
            }
            }

        //如何将 return_map返回给前端 返回json 如何吧对象变成json串
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(),return_map);//returnmap 写到响应体的流


    }
}
