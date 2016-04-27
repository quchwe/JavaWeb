package querysql;

import DB.Choose;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quchwe on 2016/2/20 0020.
 */
public class QueryChoose extends DBconnection{
    private Connection conn = null;
    public QueryChoose(){

        conn = getConnection();
    }
    public List<Choose> Query (String sqlString) throws SQLException
    {
        List<Choose>list =new ArrayList<Choose>();
        Statement statement = null;
        ResultSet rs = null;
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery(sqlString);
            while(rs.next()){
                Choose choose = new Choose();
                choose.setSno(rs.getInt("sno"));
                choose.setCno(rs.getInt("cno"));
                choose.setGrade(rs.getString("grade"));
                choose.setProfession(rs.getString("profession"));
                choose.setCn(rs.getString("cn"));

                list.add(choose);
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
    public boolean executeCourse(String sqlString){
        boolean b =false;
        try {
            Statement statement = conn.createStatement();
            int m = statement.executeUpdate(sqlString);
            if(m!=0){
                b=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

            return b;
    }

}

