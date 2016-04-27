package querysql;

import DB.CourseTableData;
import DB.Diary;
import DB.Message;
import DB.Student;
import util.CloseIo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quchwe on 2016/2/19 0019.
 */
public class QueryUser extends DBconnection {
    private Connection conn = null;
    public QueryUser(){

        conn = getConnection();
    }
    public List<Student> Query (String sqlString) throws SQLException{
        List<Student>list =new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery(sqlString);
            while(rs.next()){
              Student s = new Student();
                s.setSno(rs.getInt("sno"));
                s.setSn(rs.getString("sn"));
                s.setAcademy(rs.getString("academy"));
                s.setSex(rs.getString("sex"));
                s.setClassname(rs.getString("classname"));
                s.setInterest(rs.getString("interest"));
                list.add(s);
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
    public List<CourseTableData> QueryCourseTable (String sqlString) throws SQLException{
        List<CourseTableData>list =new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery(sqlString);
            while(rs.next()){
                CourseTableData s = new CourseTableData();
                s.setCn(rs.getString("cn"));
                s.setTn(rs.getString("tn"));
                s.setClasstime(rs.getInt("classtime"));
                s.setDaytime(rs.getInt("daytime"));
                s.setPlace(rs.getString("place"));
                s.setWeek(rs.getString("weektime"));
                list.add(s);
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
    public List<Diary> queryDiary(String sqlString){
        List<Diary>list =new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery(sqlString);
            while(rs.next()){
                Diary d= new Diary();
                d.setSno(rs.getInt("sno"));
                d.setTitle(rs.getString("title"));
                d.setMessage(rs.getString("message"));
                d.setTime(rs.getString("time"));

                list.add(d);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if(statement!=null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if(rs!=null)

            try {
                rs.close();
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    public List<Message> queryInform(String sqlString){
        List<Message>list =new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery(sqlString);
            while(rs.next()){
              Message d = new Message();
                d.setTitle(rs.getString("title"));
                d.setMessage(rs.getString("message"));
                d.setTime(rs.getString("time"));

                list.add(d);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if(statement!=null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if(rs!=null)

                try {
                    rs.close();
                    closeConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return list;
    }

}
