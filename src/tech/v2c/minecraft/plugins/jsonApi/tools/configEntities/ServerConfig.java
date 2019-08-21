package tech.v2c.minecraft.plugins.jsonApi.tools.configEntities;

public class ServerConfig {
    private Authentication Authentication;
    private String IP;
    private int HttpPort;
    private int WsPort;

    public ServerConfig.Authentication getAuthentication() {
        return Authentication;
    }

    public void setAuthentication(ServerConfig.Authentication authentication) {
        Authentication = authentication;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getHttpPort() {
        return HttpPort;
    }

    public void setHttpPort(int httpPort) {
        HttpPort = httpPort;
    }

    public int getWsPort() {
        return WsPort;
    }

    public void setWsPort(int wsPort) {
        WsPort = wsPort;
    }

    public static class Authentication{
        private String UserName;
        private String Password;

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            Password = password;
        }
    }
}
