import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by quchwe on 2016/2/6 0006.
 */
public class TestUrl extends HttpServlet{
    private String message;
    @Override
    public void init() throws ServletException {
       message = "I Love Li Jing";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println("<h1>"+message+"</h1>");

    }

    @Override
    public void destroy() {
        super.destroy();

    }
}
