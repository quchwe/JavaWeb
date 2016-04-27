package choosecourse;

import DB.Choose;
import DB.Course;
import DB.SignIn;
import DB.Student;
import com.google.gson.Gson;
import querysql.BackCourse;
import querysql.QueryChoose;
import querysql.QueryUser;
import weka.TESTWEKA;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quchwe on 2016/4/26 0026.
 */
public class TuiJianCourseServer extends HttpServlet {
    Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("--post--");
        req.setCharacterEncoding("UTF-8");
        String tuijianString = req.getParameter("TUIJIAN");


        System.out.println(tuijianString);

        QueryChoose allChoose = new QueryChoose();
        List<Choose> chooseList = new ArrayList<Choose>();
        List<Choose> chooseList1 = new ArrayList<Choose>();
        List<SignIn> sinList = new ArrayList<>();
        List<BackCourse> backCourseList = new ArrayList<BackCourse>();
        gson = new Gson();
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        resp.setContentType("text/html;charset=UTF-8");
        out = resp.getWriter();
        try {
            if (tuijianString != null) {
                QueryUser user = new QueryUser();
                Student s = user.Query(tuijianString).get(0);
                chooseList = allChoose.Query("select *from choosed where choose.sno = " + s.getSno());

                List<Course> tuijianList = new ArrayList<>();
                TESTWEKA testweka = new TESTWEKA();
                tuijianList = testweka.getTuiJianCourseList(s, chooseList);
//                List<Cno> cnoList = gson.fromJson(haveChoosedCourse, new TypeToken<List<Choose>>() {
//                }.getType());

//                for (int i = 0; i < chooseList.size(); i++) {
//                    if (!cnoList.contains(chooseList.get(i).getCno())) {
//                        chooseList1.add(chooseList.get(i));
//                    }
//                }
                if (tuijianList.size() == 0) {
                    out.print("failed");
                    return;
                }
                String tuijianJson = gson.toJson(tuijianList);

                out.print(tuijianJson);
                String choose = gson.toJson(chooseList);
                System.out.println(choose);

                System.out.println("success");
                out.print(choose);
            }
        } catch (Exception e) {
            out.print("failed");
            e.printStackTrace();
        }
    }
}
