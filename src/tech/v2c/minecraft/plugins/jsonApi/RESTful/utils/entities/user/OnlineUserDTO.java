package tech.v2c.minecraft.plugins.jsonApi.RESTful.utils.entities.user;

import java.util.UUID;

public class OnlineUserDTO {
    private long id;
    private UUID uid;
    private String name;
    private String displayName;
    private int gamemode;
    private float height;
    private UserPositionDTO position;
    private int maxHealth;
    private float health;
    private int ping;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public int getGamemode() {
        return gamemode;
    }

    public void setGamemode(int gamemode) {
        this.gamemode = gamemode;
    }

    public int getPing() {
        return ping;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public UserPositionDTO getPosition() {
        return position;
    }

    public void setPosition(UserPositionDTO position) {
        this.position = position;
    }
}
