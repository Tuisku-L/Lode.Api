package tech.v2c.minecraft.plugins.lode.RESTful.global.entities.user;

import java.util.UUID;

public class OnlineSimpleUserDTO {
    private long id;
    private String name;
    private String displayName;
    private UserPositionDTO position;
    private int maxHealth;
    private float health;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public UserPositionDTO getPosition() {
        return position;
    }

    public void setPosition(UserPositionDTO position) {
        this.position = position;
    }
}
