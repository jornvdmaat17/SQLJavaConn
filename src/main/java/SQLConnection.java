import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class SQLConnection {

    private Connection connection;

    public SQLConnection(String url, String user, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");  
        } catch (Exception e){
            System.out.println("com.mysql.jdbc.Driver jar not installed!!!!!");
            e.printStackTrace();
        }
        try {
            String connectionUrl = "jdbc:mysql://" + url + "?useUnicode=true&characterEncoding=UTF-8&user="+ user +"&password=" + password;
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
            ResultSet rs = connection.prepareStatement("show tables").executeQuery();
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
