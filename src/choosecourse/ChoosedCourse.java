package choosecourse;

import DB.Cno;
import DB.Course;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import querysql.AllCourse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quchwe on 2016/2/19 0019.
 */
public class ChoosedCourse extends HttpServlet {
    private Gson gson;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("--post--");
        req.setCharacterEncoding("UTF-8");
        String str =req.getParameter("Cno");
        System.out.println(str);

        AllCourse allCourse = new AllCourse();
        List<Course> courseList = new ArrayList<Course>();
        List<Course> courseList1 = new ArrayList<Course>();

        gson = new Gson();
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        resp.setContentType("text/html;charset=UTF-8");
        try {
            courseList = allCourse.Query("select * from course ");
            out =resp.getWriter();
            List<Cno> cnoList = gson.fromJson(str,new TypeToken<List<Course>>() {
            }.getType());

            for(int i=0;i<courseList.size();i++){
                if(!cnoList.contains(courseList.get(i).getCno())){
                    courseList1.add(courseList.get(i));
                }
            }
            String course = gson.toJson(courseList1);
            System.out.println(course);
            if (str != null) {
                System.out.println("success");
                out.print(course);
            }else{
                out.print("failed");
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(out!=null){
                out.close();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
