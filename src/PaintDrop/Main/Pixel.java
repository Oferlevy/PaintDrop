package PaintDrop.Main;

public class Pixel {
    private int x, y;
    private int color;

    public Pixel(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color;
    }

    public void setX(int value) {
        x = value;
    }

    public void setY(int value) {
        y = value;
    }

    public void setColor(int value) {
        color = value;
    }
}