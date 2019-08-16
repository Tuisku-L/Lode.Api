package tech.v2c.minecraft.plugins.jsonApi.RESTful.global.entities.server;

public class ServerDTO {
    private int port;
    private String version;
    private int onlinePlayerCount;
    private String ip;
    private int maxPlayerCount;
    private String motd;
    private String subMotd;
    private String NukkitVersion;
    private String ApiVersion;
    private int gameMode;
    private int difficulty;
    private int pluginCount;

    public int getPluginCount() {
        return pluginCount;
    }

    public void setPluginCount(int pluginCount) {
        this.pluginCount = pluginCount;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getOnlinePlayerCount() {
        return onlinePlayerCount;
    }

    public void setOnlinePlayerCount(int onlinePlayerCount) {
        this.onlinePlayerCount = onlinePlayerCount;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getMaxPlayerCount() {
        return maxPlayerCount;
    }

    public void setMaxPlayerCount(int maxPlayerCount) {
        this.maxPlayerCount = maxPlayerCount;
    }

    public String getMotd() {
        return motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    public String getSubMotd() {
        return subMotd;
    }

    public void setSubMotd(String subMotd) {
        this.subMotd = subMotd;
    }

    public String getNukkitVersion() {
        return NukkitVersion;
    }

    public void setNukkitVersion(String nukkitVersion) {
        NukkitVersion = nukkitVersion;
    }

    public String getApiVersion() {
        return ApiVersion;
    }

    public void setApiVersion(String apiVersion) {
        ApiVersion = apiVersion;
    }

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
