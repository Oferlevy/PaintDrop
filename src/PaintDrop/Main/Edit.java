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
				int removeRGB = (colorToRemove.getRGB() | 0xFF000000);
				
				for (int x_ = 1; x_ < width; x_++) {	
					for (int y_ = 1; y_ < height; y_++) {
						// Compare the RGB value of the colors
						System.out.println(Integer.toBinaryString( img.getRGB(x + x_, y + y_) | 0xFF000000 ));
						if (( img.getRGB(x + x_, y + y_) | 0xFF000000) == removeRGB) {
							Pixel pixel = new Pixel(x + x_, y + y_, new Color(replaceColor.getRed(), replaceColor.getGreen(),
													replaceColor.getRed(), colorToRemove.getAlpha()).getRGB());
							pixels.add(pixel);
						}
					}
				}
				Pixel[] arr = new Pixel[pixels.size()];
				arr = pixels.toArray(arr);
				canvas.setPixelsArr(arr);
				canvas.repaint();
			}
		}).start();
	}
}
