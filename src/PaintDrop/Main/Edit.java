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
				int rp = replaceColor.getRGB();
				
				for (int x_ = 1; x_ < width; x_++) {	
					for (int y_ = 1; y_ < height; y_++) {
						// Compare the RGB value of the colors
						int rx = x + x_;
						int ry = y + y_;
						int rgb = img.getRGB(rx, ry);
						if (( rgb & 0xFFFFFF) == removeRGB) {
							/*
							 * new Color( replaceColor.getRed(), replaceColor.getGreen(),
													replaceColor.getBlue(),
													((int)( ( ( (long)rgb ) & 0xFF000000l) >> 24)) ).getRGB()
							 */
							int red = (rp >> 16) & 0x000000FF;
							int green = (rp >>8 ) & 0x000000FF;
							int blue = (rp) & 0x000000FF;
							
							int alpha = (rgb >> 24) & 0xff;
							if (red != 0) {
								System.out.println("A");
							}
							int ret = ((red << 16) & 0x00FF0000) | ((green << 8) & 0x0000FF00) |
									(blue & 0x000000FF);
							Pixel pixel = new Pixel(rx, ry, ret);
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
