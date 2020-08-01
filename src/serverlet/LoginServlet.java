package serverlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.User;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //如何拿到前端的数据
        req.setCharacterEncoding("utf-8");//req请求体设置编码
        resp.setContentType("application/json;charset=utf-8");//响应体编码
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(username+ " " +password);

        User loginUser = new User();
        loginUser.setUsername(username);
        loginUser.setPassword(password);
        UserService userService = new UserService();
        User user = userService.login(loginUser);

        Map<String,Object> return_map = new HashMap<>();//returnmap就是返回给前端的
        //登录成功还是失败
        if (user!=null) {
            System.out.println("登录成功!");//登录成功后要响应一个map表 转换成json
            //登录成功就将用户信息写入session
            req.getSession().setAttribute("user",user);//绑定数据
            return_map.put("msg",true);
        } else{
            System.out.println("登录失败!");
            return_map.put("msg",false);

        }

        //如何将 return_map返回给前端 返回json 如何吧对象变成json串
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(),return_map);//returnmap 写到响应体的流

    }
}
