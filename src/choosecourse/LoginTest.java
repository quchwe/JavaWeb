package choosecourse;

import DB.Student;
import com.google.gson.Gson;
import querysql.QueryUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * �жϿͻ��˵�¼�û��Ƿ����
 * Created by quchwe on 2016/2/19 0019.
 */
public class LoginTest extends HttpServlet{
    private String response = "failed";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String sql =req.getParameter("SQL");
        System.out.println(sql);

        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;

        resp.setContentType("text/html;charset=UTF-8");
        try {


            QueryUser queryUser = new QueryUser();
            List<Student>studentList = queryUser.Query(sql);
            Gson gson = new Gson();

            out =resp.getWriter();
            out.print(gson.toJson(studentList));

        }catch (Exception e){
            e.printStackTrace();
            out.print(response);
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
