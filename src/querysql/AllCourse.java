package querysql;

import DB.Course;
import util.CloseIo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quchwe on 2016/2/18 0018.
 */
public class AllCourse extends DBconnection {
    private Connection conn = null;
    public AllCourse(){

        conn = getConnection();
    }
    public List<Course> Query (String sqlString) throws SQLException
    {
        List<Course>list =new ArrayList<Course>();
        Statement statement = null;
        ResultSet rs = null;
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery(sqlString);
            while(rs.next()){
                Course course = new Course();
                course.setCno(rs.getInt("cno"));
                course.setFenlei(rs.getString("fenlei"));
                course.setCn(rs.getString("cn"));
                course.setClasstime(rs.getInt("classtime"));
                course.setCredit(rs.getString("credit"));
                course.setDaytime(rs.getInt("daytime"));
                course.setTno(rs.getInt("tno"));
                course.setPlace(rs.getString("place"));
                course.setType(rs.getString("type"));
                course.setProfession(rs.getString("profession"));
                course.setWeek(rs.getString("weektime"));
                list.add(course);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if(statement!=null)
                statement.close();
            if(rs!=null)
                rs.close();
            closeConnection();
        }
        return list;
    }
    public void insert(String sqlString ) throws SQLException{


}
    public List<Integer> QueryChooseCounts (String sqlString) throws SQLException
    {
        List<Integer>list =new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery(sqlString);
            while(rs.next()){
               int i ;
                i = rs.getInt("cnos");

                list.add(i);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if(statement!=null)
                statement.close();
            if(rs!=null)
                rs.close();
            closeConnection();
        }
        return list;
    }
    public List<String> QueryStudentClass (String sqlString) throws SQLException
    {
        List<String>list =new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery(sqlString);
            while(rs.next()){
                String i;
                i = rs.getString("classname");

                list.add(i);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if(statement!=null)
                statement.close();
            if(rs!=null)
                rs.close();
            closeConnection();
        }
        return list;
    }
}
