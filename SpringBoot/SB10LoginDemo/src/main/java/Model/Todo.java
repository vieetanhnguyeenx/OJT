package Model;

public class Todo {
    public int id;
    public String tile;
    public String detail;

    public Todo() {
    }

    public Todo(int id, String tile, String detail) {
        this.id = id;
        this.tile = tile;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
