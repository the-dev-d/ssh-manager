package tools.database.model;

public class SSHDB {

    private Integer id;

    private String name;

    private String username; 

    private String ip;

    private Integer port;

    private Integer authType;

    private String password;

    //generate constructors 
    public SSHDB() {
    }

    public SSHDB(Integer id, String name, String ip, Integer port, Integer authType, String username, String password) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.authType = authType;
        this.username = username;
        this.password = password;
    }


    //generate getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
