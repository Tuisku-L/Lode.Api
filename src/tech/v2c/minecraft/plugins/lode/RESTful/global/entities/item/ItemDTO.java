package tech.v2c.minecraft.plugins.lode.RESTful.global.entities.item;

public class ItemDTO {
    private int Id;
    private String name;
    private boolean hasMeta;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasMeta() {
        return hasMeta;
    }

    public void setHasMeta(boolean hasMeta) {
        this.hasMeta = hasMeta;
    }
}
