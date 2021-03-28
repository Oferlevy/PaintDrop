package PaintDrop.Main;

public class Rect {
	private short x;
	private short y;
	private short w;
	private short h;
	
	public Rect() {
		x = 0;
		y = 0;
		w = 0;
		h = 0;
	}
	
	public Rect(short x, short y, short w, short h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public Rect(int x, int y, int w, int h) {
		this.x = (short)x;
		this.y = (short)y;
		this.w = (short)w;
		this.h = (short)h;
	}
	
	public short getX() {
		return x;
	}
	
	public short getY() {
		return y;
	}
	
	public short getWidth() {
		return w;
	}
	
	public short getHeight() {
		return h;
	}
	
}
