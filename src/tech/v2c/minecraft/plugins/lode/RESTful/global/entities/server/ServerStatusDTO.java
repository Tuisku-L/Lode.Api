package tech.v2c.minecraft.plugins.lode.RESTful.global.entities.server;

public class ServerStatusDTO {
    private int OnlinePlayer;
    private int MaxPlayer;

    public int getOnlinePlayer() {
        return OnlinePlayer;
    }

    public void setOnlinePlayer(int onlinePlayer) {
        OnlinePlayer = onlinePlayer;
    }

    public int getMaxPlayer() {
        return MaxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        MaxPlayer = maxPlayer;
    }
}
