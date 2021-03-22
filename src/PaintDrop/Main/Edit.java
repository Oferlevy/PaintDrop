package PaintDrop.Main;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Edit {
	private Canvas canvas;
	
	public Edit() {
		canvas = new Canvas();
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	// Filter pixels of canvas 
	public void filter(int x, int y, int width, int height, Color colorToRemove, Color replaceColor) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// Gets an image of the canvas
				BufferedImage img = canvas.getPixels();
				
				for (int x_ = 0; x_ < width; x_++) {	
					for (int y_ = 0; y_ < height; y_++) {
						if (img.getRGB(x + x_, y + y_) == colorToRemove.getRGB()) {
							//img.setRGB(x + x_, y + y_, replaceColor.getRGB());
							// Save Pixels
						}
						
					}
				}
			}
		}).start();
	}
}
