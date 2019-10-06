package tech.v2c.minecraft.plugins.lode.RESTful.global.entities.user;

import java.util.UUID;

public class OnlineUserDTO {
    private long id;
    private UUID uid;
    private String name;
    private String displayName;
    private int gameMode;
    private float height;
    private UserPositionDTO position;
    private int maxHealth;
    private float health;
    private int ping;
    private boolean isOp;
    private int experience;
    private int experienceLevel;

    public boolean isOp() {
        return isOp;
    }

    public void setOp(boolean op) {
        isOp = op;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(int experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

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

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
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
