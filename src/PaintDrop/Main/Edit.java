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
				int removeRGB = (colorToRemove.getRGB() & 0xFFFFFF);
				
				for (int x_ = 1; x_ < width; x_++) {	
					for (int y_ = 1; y_ < height; y_++) {
						// Compare the RGB value of the colors
						int rgb = img.getRGB(x + x_, y + y_);
						if (( rgb & 0xFFFFFF) == removeRGB) {
							//(int)((((long)rgb) & 0xFF000000l) >> 24)
							Pixel pixel = new Pixel(x + x_, y + y_, new Color( replaceColor.getRed(), replaceColor.getGreen(),
													replaceColor.getBlue(), (int)((((long)rgb) & 0xFF000000l) >> 24) ).getRGB() );
							pixels.add(pixel);
						}
					}
				}
				
				Pixel[] arr = new Pixel[pixels.size()];
				arr = pixels.toArray(arr);
				
				Rect rect = new Rect(x, y, width, height);
				canvas.addFilledRect(rect);
				
				canvas.setPixelsArr(arr);
				canvas.repaint();
			}
		}).start();
	}
}
