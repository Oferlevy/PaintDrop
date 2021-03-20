package PaintDrop.Main;

import java.awt.Color;

public class Edit {
	private final Canvas canvas = new Canvas();
	
	public Edit() {
		
	}
	
	// Filter pixels of canvas
	public void filter(int x, int y, int w, int h, Color colorToRemove, Color replaceColor) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
			}
		}).start();
		
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
}
