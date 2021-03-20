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
				BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
				canvas.paint(image.getGraphics());
				
				// creating an array of pixels, where each pixel is a long
				ArrayList<Long> pixels = new ArrayList<Long>();
				
				for (int x_ = 0; x_ < width; x_++) {	
					for (int y_ = 0; y_ < height; y_++) {
						if (image.getRGB(x + x_, y + y_) == colorToRemove.getRGB()) {
							// The pixel is 32 bits of color and 32 bits of position(x, y)
							long pixel = (replaceColor.getRGB() & 0xffffff) | ((byte)(colorToRemove.getAlpha()) << 24)
										 | ((short)(x_) << 32)  | ((short)(y_) << 48);
							pixels.add(pixel);
						}
					}
				}
				
				// Converting the pixels to array of longs and adding it to the canvas
				Long[] pixelsAsArray = new Long[pixels.size()];
				pixelsAsArray = pixels.toArray(pixelsAsArray);		
				canvas.getPixels().add(pixelsAsArray);
			}
		}).start();	
	}
}
