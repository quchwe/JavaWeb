package choosecourse;

import DB.Choose;
import DB.Cno;
import DB.Course;
import DB.SignIn;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import querysql.AllCourse;
import querysql.BackCourse;
import querysql.QueryChoose;
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
 * Created by quchwe on 2016/2/20 0020.
 */
public class HaveChoosedCourse extends HttpServlet{
    private Gson gson;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("--post--");
        req.setCharacterEncoding("UTF-8");
        String haveChoosedCourse =req.getParameter("ChoosedList");
        String backCourses =req.getParameter("BackCourse");
        String chooseCourse = req.getParameter("ChooseCourse");
        String signUp = req.getParameter("SignIn");

        System.out.println(haveChoosedCourse);

       QueryChoose allChoose = new QueryChoose();
        List<Choose> chooseList = new ArrayList<Choose>();
        List<Choose> chooseList1 = new ArrayList<Choose>();
        List<SignIn> sinList = new ArrayList<>();
        List<BackCourse>backCourseList = new ArrayList<BackCourse>();
        gson = new Gson();
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        resp.setContentType("text/html;charset=UTF-8");
        out = resp.getWriter();
        try {
        if(haveChoosedCourse!=null) {

                chooseList = allChoose.Query(haveChoosedCourse);

//                List<Cno> cnoList = gson.fromJson(haveChoosedCourse, new TypeToken<List<Choose>>() {
//                }.getType());

//                for (int i = 0; i < chooseList.size(); i++) {
//                    if (!cnoList.contains(chooseList.get(i).getCno())) {
//                        chooseList1.add(chooseList.get(i));
//                    }
//                }
                String choose = gson.toJson(chooseList);
                System.out.println(choose);

                System.out.println("success");
                out.print(choose);
              }
         else if(backCourses!=null){
            backCourseList = gson.fromJson(backCourses, new TypeToken<List<BackCourse>>() {
            }.getType());
            for(BackCourse ba :backCourseList) {
                if (allChoose.executeCourse("delete  from choosed where cno =" + ba.getCno()
                        + " and sno=" + ba.getSno())) {
                    System.out.println("back success");
                }
                System.out.println(backCourses);
            }
            out.print("back success");
        }else if(chooseCourse!=null){
            AllCourse all = new AllCourse();
            System.out.println(chooseCourse);
            List<Choose>chooseList2 = new ArrayList<Choose>();
            chooseList2 = gson.fromJson(chooseCourse, new TypeToken<List<Choose>>() {
            }.getType());
            List<Course> cs = new ArrayList<>();
            AllCourse q = new AllCourse();
//            cs = q.Query("select *from course where cno = "+)
            for(Choose c :chooseList2){
                System.out.println("insert into" +
                        " choosed(cno,sno,grade,profession,cn) values("+c.getCno()+","+c.getSno()+",'"+c.getGrade()+"','"+c.getProfession()
                        +"','"+c.getCn()+"')");

                if (allChoose.executeCourse("insert into" +
                        " choosed(cno,sno,grade,profession,cn) values("+c.getCno()+","+c.getSno()+",'"+c.getGrade()+"','"+c.getProfession()
                        +"','"+c.getCn()+"')")) {
                    System.out.println("选课成功");
                    List<Integer> nos =  all.QueryChooseCounts("select count (*) as cnos from choosed where sno =   "+c.getSno());
                    int i = nos.get(0);

                    System.out.println(i);

                    String sql = null;
                    if (i==1) {

                            String classname = all.QueryStudentClass("select classname from student where sno = " + c.getSno()).get(0);
                            sql = "insert into analyze (sno,profession,cn1)values(" + c.getSno() + ",'" + classname + "','"
                                    + c.getCn() + "')";
                            System.out.println("i==0" + sql);

                    }else if (i>1){
                        sql = "update analyze set cn"+String.valueOf(i)+"='"+c.getCn()+"' where sno = "+c.getSno();
                        System.out.println("i>0"+sql);
                    }
                   boolean chooseSuccess =  allChoose.executeCourse(sql);
                    if (chooseSuccess){
                        out.print("success");
                    }
                    System.out.println();
                }


                System.out.println("choose success");
            }
        }else if(signUp!=null) {
            System.out.println(signUp);
            sinList = gson.fromJson(signUp, new TypeToken<List<SignIn>>() {

            }.getType());
            for (SignIn s : sinList) {
                if (allChoose.executeCourse("insert into" +
                        " signup(sno,sna,psw,txt,img,loc) values(" + s.getSno() + ",'" + s.getSna() + "','" +s.getPsw()+"','"
                        + s.getInformation() + "','"+s.getImg()+"','"+s.getLocation()+"')"))

                System.out.println("choose success");

            }
        }
        else {
            out.print("failed");
        }}catch (Exception e) {
            e.printStackTrace();
        }finally {
            CloseIo.closeIo(out);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
