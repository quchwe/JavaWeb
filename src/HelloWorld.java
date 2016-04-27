import DB.Course;
import querysql.AllCourse;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quchwe on 2016/2/6 0006.
 */
public class HelloWorld extends HttpServlet{
    private String message;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        message = "this is my house";

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      System.out.println("--get--");
        req.setCharacterEncoding("UTF-8");
        String loginName =req.getParameter("LoginName");
        String loginPassword = req.getParameter("LoginPassword");
        System.out.println(loginName);
        System.out.println(loginPassword);
        String str =  req.getParameter("UID");

        AllCourse allCourse = new AllCourse();
        List<Course> courseList = new ArrayList<>();
        try {
            courseList = allCourse.Query("select * from course ");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        gson = new Gson();

        String course = gson.toJson(courseList);

        System.out.println(course);

        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        resp.setContentType("text/html;charset=UTF-8");
        try {
                out =resp.getWriter();

            if (loginName != null) {
                System.out.println("success");
                out.print(course);
            }else{
                out.print("failed");
            }
//            resp.setContentType("text/html");
//            Gson go = new Gson();
//          String string =  go.toJson(str,User.class);
//        PrintWriter out =resp.getWriter();
//        out.println("<h1>"+string+"</h1>");
//
////        resp.setContentType("text/html");
////        PrintWriter out = resp.getWriter();
////        out.println("<h1>"+message+"</h1>");
        }catch (Exception e){
           e.printStackTrace();
        }finally {
            if(out!=null){
                out.close();
            }
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       System.out.println("--post--");
        String str = req.getParameter("UID");
        if(str!=null){
            System.out.println(str);
//            resp.setContentType("");
//
//            gson = new Gson();
//            User user = gson.fromJson(str,User.class);
//            System.out.print(user.getUser());


        }

//     resp.setContentType("application/json");

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
