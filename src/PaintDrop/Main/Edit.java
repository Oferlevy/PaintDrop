package PaintDrop.Main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
				ArrayList<Pixel> pixels = new ArrayList<Pixel>();
				
				for (int x_ = 0; x_ < width; x_++) {	
					for (int y_ = 0; y_ < height; y_++) {
						// Compare the RGB value of the colors
						if ((img.getRGB(x + x_, y + y_) & 0xffffff) == (colorToRemove.getRGB() & 0xffffff)) {
							Pixel pixel = new Pixel(x + x_, y + y_, new Color(replaceColor.getRed(), replaceColor.getGreen(),
													replaceColor.getRed(), colorToRemove.getAlpha()));
							pixels.add(pixel);
						}
					}
				}
				Pixel[] arr = new Pixel[pixels.size()];
				arr = pixels.toArray(arr);
				canvas.setPixelsArr(arr);
			}
		}).start();
	}
}
