package serverlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import dao.UserDao;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet  extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password" );
        System.out.println(username+""+password);
        System.out.println("password："+password);

        UserDao dao = new UserDao();

        Map<String ,Object> return_map = new HashMap<>();

        User loginUser =new User();  //创建一个数据库实体类对象
        loginUser.setUsername(username);
        loginUser.setPassword(password);
        try {
            User user = dao.login(loginUser);
            if(user != null) {
                req.getSession().setAttribute("user", user);//绑定数据
                return_map.put("msg",true);
                System.out.println("发发发发发 ");
            }else {
                System.out.println("密码错误！");
                return_map.put("msg",false);
            }

        } catch (Exception e) {
        e.printStackTrace();
    }        ObjectMapper mapper = new ObjectMapper();
    //利用Jackson将map转化为json对象
    mapper.writeValue(resp.getWriter(),return_map);
}
}
