package choosecourse;

import DB.*;
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
 * Created by quchwe on 2016/4/27 0027.
 */
public class DiaryServer extends HttpServlet{
    Gson gson = new Gson();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("--post--");
        req.setCharacterEncoding("UTF-8");
        String diary = req.getParameter("Diary");

        String messageString = req.getParameter("Inform");

        System.out.println(diary);

        gson = new Gson();
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        resp.setContentType("text/html;charset=UTF-8");
        out = resp.getWriter();
        try {
            if (diary != null) {
                QueryUser user = new QueryUser();
                List<Diary> diaries = new ArrayList<>();
                diaries = user.queryDiary(diary);
                String tuijianJson = gson.toJson(diaries);

                out.print(tuijianJson);
                String choose = gson.toJson(diaries);
                System.out.println(choose);

                System.out.println("success");
                out.print(choose);
            }
            if (messageString!=null){
                QueryUser user = new QueryUser();
                List<Message> messages = new ArrayList<>();

                messages = user.queryInform(messageString);
                out.print(gson.toJson(messages));
            }
        } catch (Exception e) {
            out.print("failed");
            e.printStackTrace();
        }
    }

}
