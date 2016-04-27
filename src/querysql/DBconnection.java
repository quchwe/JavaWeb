package querysql;

import java.sql.*;

/**
 * Created by quchwe on 2016/2/18 0018.
 */
public class DBconnection {
    static Connection conn=null;
    public Connection getConnection(){
        String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=Choose_course;user=sa;password=1234";//sa身份连接

        // Declare the JDBC objects.

        try{
            // Establish the connection.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url);
        }catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }
    public void closeConnection() throws SQLException {
        if(conn!= null){
            try{
                conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
}}
