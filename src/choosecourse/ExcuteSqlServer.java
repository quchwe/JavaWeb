package choosecourse;

import DB.Choose;
import DB.Cno;
import DB.SignIn;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import querysql.BackCourse;
import querysql.QueryChoose;
import util.CloseIo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quchwe on 2016/4/20 0020.
 */
public class ExcuteSqlServer extends HttpServlet {
    Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("--post--");
        req.setCharacterEncoding("UTF-8");
        String excuteSqlString  =req.getParameter("ExcuteSQL");


        System.out.println(excuteSqlString);


        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        resp.setContentType("text/html;charset=UTF-8");
        out = resp.getWriter();
        try {
            if (excuteSqlString != null) {
                QueryChoose queryChoose = new QueryChoose();
                List<String> sqlStringList = gson.fromJson(excuteSqlString, new TypeToken<List<String>>() {
                }.getType());
                for (String sql : sqlStringList) {
                    boolean b = queryChoose.executeCourse(sql);
                    if (!b) {
                        System.out.println("数据库语句执行失败" + sql);
                        out.print("false" + sql);
                        return;
                    }
                }

                System.out.println("success");
                out.print("success");
                System.out.println("success2222");
                out.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
            out.print("false");
        }finally {
//            CloseIo.closeIo(out);
        }
    }
}
