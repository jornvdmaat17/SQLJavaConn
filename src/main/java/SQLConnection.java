import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class SQLConnection {

    private static final String SHOW_TABLES_QUERY = "SHOW TABLES";
    private static final String COM_MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    private Connection connection;

    public SQLConnection(String url, String user, String password) {
        try {
            Class.forName(COM_MYSQL_DRIVER);  
        } catch (Exception e){
            System.out.println(COM_MYSQL_DRIVER + "jar not installed!!!!!");
            e.printStackTrace();
        }
        try {
            final String connectionUrl = "jdbc:mysql://" + url + "?useUnicode=true&characterEncoding=UTF-8&user="+ user +"&password=" + password;
            connection = DriverManager.getConnection(connectionUrl);
            System.out.println("Connected");
        } catch (Exception e){
            System.out.println("Could not connect to " + url);
            e.printStackTrace();
        }
    }

    public List<String> getAllTables(){
        List<String> tables = new ArrayList<String>();
        try  {
            ResultSet rs = connection.prepareStatement(SHOW_TABLES_QUERY).executeQuery();
            while(rs.next()){
                tables.add(rs.getString(1));
            }
        } catch (Exception e){
            e.printStackTrace();
        }        
        return tables;
    }

    public ResultSet executeQuery(String query){
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            return rs;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public int getLengthOfResultSet(ResultSet rs){
        int size = 0;
        try {
            if (rs != null){
                rs.last();    
                size = rs.getRow();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return size;
    }
}
