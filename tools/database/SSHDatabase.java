package tools.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tools.database.model.Model;
import tools.database.model.SSHDB;

public class SSHDatabase {

    public static int AUTH_TYPE_PASSWORD = 1;
    public static int AUTH_TYPE_KEY = 2;
    public static int AUTH_TYPE_PEM = 3;
    
    private Connection conn;

    public SSHDatabase() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sshdb", "root", "");
        Statement statement = this.conn.createStatement();
        
        List<Model> models = new ArrayList<>();
        models.add(new SSHDB());

        for(Model model : models){
            String query = model.getCreateQuery();
            statement.executeUpdate(query);
        }
    }

    public SSHDB getSSHDB(Integer id) throws SQLException{
        
        Statement statement = this.conn.createStatement();
        String query = "SELECT * FROM ssh WHERE id = " + id;
        ResultSet resultSet = statement.executeQuery(query);
        SSHDB sshdb = new SSHDB();
        if(resultSet.next()){
            sshdb.setId(resultSet.getInt("id"));
            sshdb.setName(resultSet.getString("name"));
            sshdb.setIp(resultSet.getString("ip"));
            sshdb.setUsername(resultSet.getString("username"));
            sshdb.setPort(resultSet.getInt("port"));
            sshdb.setAuthType(resultSet.getInt("auth_type"));
            sshdb.setPassword(resultSet.getString("password"));
            return sshdb;
            
        }
        return null;
    }

    public List<SSHDB> getNamesAndIP() throws SQLException{
        Statement statement = this.conn.createStatement();
        String query = "SELECT id, name, ip, port FROM ssh";
        ResultSet resultSet = statement.executeQuery(query);
        List<SSHDB> sshdbs = new ArrayList<>();
        while(resultSet.next()){
            SSHDB sshdb = new SSHDB();
            sshdb.setId(resultSet.getInt("id"));
            sshdb.setName(resultSet.getString("name"));
            sshdb.setIp(resultSet.getString("ip"));
            sshdb.setPort(resultSet.getInt("port"));
            sshdbs.add(sshdb);
        }
        return sshdbs;
    }
    
    public void addSSHDB(SSHDB sshdb) {
        try {
            Statement statement = this.conn.createStatement();
            String query = "INSERT INTO ssh (name, ip, port, username, auth_type, password) VALUES ('" + sshdb.getName() + "', '" + sshdb.getIp() + "', " + sshdb.getPort() + ", '" + sshdb.getUsername() + "', " + sshdb.getAuthType() + ", '" + sshdb.getPassword() + "')";
            // System.out.println(query);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSSHDB(Integer id) {
        try {
            Statement statement = this.conn.createStatement();
            String query = "DELETE FROM ssh WHERE id = " + id;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
