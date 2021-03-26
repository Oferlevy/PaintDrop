package PaintDrop.Main;

public class Pixel {
    private short x, y;
    private int color;

    public Pixel(int x, int y, int color) {
        this.x = (short)x;
        this.y = (short)y;
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
        x = (short)value;
    }

    public void setY(int value) {
        y = (short)value;
    }

    public void setColor(int value) {
        color = value;
    }
}