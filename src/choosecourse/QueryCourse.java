package choosecourse;

import DB.Choose;
import DB.Cno;
import DB.Course;
import DB.CourseTableData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import querysql.AllCourse;
import querysql.QueryChoose;
import querysql.QueryUser;
import util.CloseIo;

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
 * Created by quchwe on 2016/2/19 0019.
 */
public class QueryCourse extends HttpServlet {
    private Gson gson;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("--post--");
        req.setCharacterEncoding("UTF-8");
        String sqlString = req.getParameter("SQL");
        String courseTableString = req.getParameter("table");
        String chooseCourse = req.getParameter("ChooseCourse");
        System.out.println(sqlString);

        AllCourse allCourse = new AllCourse();
        List<Course> courseList = new ArrayList<Course>();
        QueryChoose allChoose = new QueryChoose();

        gson = new Gson();
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        resp.setContentType("text/html;charset=UTF-8");
        if (sqlString!=null){
            try {
                courseList = allCourse.Query(sqlString);
                out = resp.getWriter();
                String response = gson.toJson(courseList);
                System.out.println(response);
                out.print(response);
                out.flush();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if (courseTableString!=null){
            try {
                QueryUser queryUser = new QueryUser();
                List<CourseTableData> courseTableDatas = queryUser.QueryCourseTable(courseTableString);
                out = resp.getWriter();
                String response = gson.toJson(courseTableDatas);
                System.out.println(response);
                out.print(response);
                out.flush();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(chooseCourse!=null) {
            AllCourse all = new AllCourse();
            System.out.println(chooseCourse);
            List<Choose> chooseList2 = new ArrayList<Choose>();
            chooseList2 = gson.fromJson(chooseCourse, new TypeToken<List<Choose>>() {
            }.getType());

            for (Choose c : chooseList2) {
                System.out.println("insert into" +
                        " choosed(cno,sno,grade,profession,cn) values(" + c.getCno() + "," + c.getSno() + ",'" + c.getGrade() + "','" + c.getProfession()
                        + "','" + c.getCn() + "')");

                if (allChoose.executeCourse("insert into" +
                        " choosed(cno,sno,grade,profession,cn) values(" + c.getCno() + "," + c.getSno() + ",'" + c.getGrade() + "','" + c.getProfession()
                        + "','" + c.getCn() + "')")) {
                    List<Integer> nos = null;
                    try {
                        nos = all.QueryChooseCounts("select count (*) as cnos from choosed where sno =   " + c.getSno());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    int i = nos.get(0);
                    String sql = null;
                    if (i == 0) {
                        sql = "insert into analyze (sno,profession,cn1)values(" + c.getSno() + ",'" + c.getProfession() + "','"
                                + c.getCn() + "'";
                    } else if (i > 0) {
                        sql = "update analyze set cn" + String.valueOf(i + 1) + "='" + c.getCn() + "' where sno = " + c.getSno();
                    }
                    allChoose.executeCourse(sql);
                    System.out.println();
                }


                System.out.println("choose success");
            }
        }

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
