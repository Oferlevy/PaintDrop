package PaintDrop.Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<int[]> positions = new ArrayList<int[]>();
	private ArrayList<int[]> lines = new ArrayList<int[]>();
	private ArrayList<Pixel[]> changedPixels = new ArrayList<Pixel[]>();
	private BufferedImage pixels;
	private ArrayList<Rect> filledRects = new ArrayList<Rect>();
	private short count = 0;
	
	Color color = Color.BLUE;
	int lineSize = 4;

	public boolean drawSelectRect;
	public int[] selectRect = new int[4];
	public int[] clickPos = new int[2];

	public Canvas() {
		setBackground(Color.WHITE);
		addMouseListener(this);
		addMouseMotionListener(this);
		//System.out.println(getWidth() + " a " + getHeight());
		
	}
	
	public void create(Dimension wh) {
		System.out.println(wh + " X " + (int)wh.getWidth() + " X " + (int)wh.getWidth());
		pixels = new BufferedImage((int)wh.getWidth(),(int)wh.getHeight(), BufferedImage.TYPE_INT_RGB);
		paint(pixels.getGraphics());
	}
	
	public void setPixelsArr(Pixel[] change) {
		changedPixels.add(change);
	}
	
	public void addFilledRect(Rect rect) {
		filledRects.add(rect);
	}
	
	public BufferedImage getPixels() {
		return pixels;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		count++;
		if (count >= 2) { // counter adds after one time to make the line look better
			count = 0;
			if (!drawSelectRect) {
				int size = positions.size();
				int x = e.getX();
				int y = e.getY();
				
				// check if the point is not the same as last one and if positions should add the point
				if (size > 1 && (x != positions.get(size - 1)[0] || y != positions.get(size - 1)[1])) {
					positions.add(new int[]{ x, y });
					
					//Graphics2D g2d = pixels.createGraphics();
		            //g2d.setStroke( new BasicStroke(lineSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND) );
		            //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		            //g2d.setColor(color);
		            //int[] lp = positions.get(size - 2);
		            //g2d.drawLine(lp[0], lp[1], x, y);
		            repaint();
				}
				else if (size <= 1) {
					positions.add(new int[]{ x, y });
				}
			}else if (clickPos[0] == 0 && clickPos[1] == 0){ 
				// MousePressed does not get called every time so if it does not to that in here
				clickPos = new int[] {e.getX(), e.getY()};
				selectRect[0] = clickPos[0];
				selectRect[1] = clickPos[1];
				selectRect[2] = clickPos[0] + 2;
				selectRect[3] = clickPos[1] + 2;
			}else {
				// change the rect adds width and height and rotate the rect if needed
				int x = e.getX();
				int y = e.getY();
				
				if (x < clickPos[0]) {
					selectRect[0] = x;
					selectRect[2] = clickPos[0] - x;
				}else if (x > clickPos[0]) {
					selectRect[2] = x - clickPos[0];
				}
				
				if (y < clickPos[1]) {
					selectRect[1] = y;
					selectRect[3] = clickPos[1] - y;
				}else if (y > clickPos[1]) {
					selectRect[3] = y - clickPos[1];
				}
				repaint();
				
			}
		}
		
	}
	public void drawLinePointes() {
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// on first click - does not get called always due to UImanger setLookAndFeel windows
		int[] click = new int[] {e.getX(), e.getY()};
		if (drawSelectRect) {
			clickPos = click;
			selectRect[0] = clickPos[0];
			selectRect[1] = clickPos[1];
			selectRect[2] = clickPos[0] + 2;
			selectRect[3] = clickPos[1] + 2;
		}else {
			positions.add(click);
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// save the last line as int array. every int is x,y packed
		if (!drawSelectRect) {
			int len = positions.size();
			int[] pointes = new int[len+2];
			
			pointes[0] =  color.getRGB();
			pointes[1] = lineSize;
			
			for (int i = 0; i < len; i++) {
				int[] one = positions.get(i);
				// pack the x,y to the int
				pointes[i + 2] = ((short) one[0]) | ( ((short) one[1]) << 16 ); 
			}
			
			lines.add(pointes);	
			positions.clear();
		}	
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = pixels.createGraphics();
		Dimension size = getPreferredSize();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, size.width,  size.height);
		
		//Graphics2D g2d = (Graphics2D) pixels.createGraphics();
		for (int[] line : lines) {
			g2d.setStroke(new BasicStroke(line[1], BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(new Color(line[0]));
			for (int i = 2; i < line.length-1; i++) {
				// unpack both x,y of the two ints and draw a line
				g2d.drawLine((line[i] & 0xffff), ((line[i] & 0xffff0000) >> 16), (line[i + 1] & 0xffff), ((line[i + 1] & 0xffff0000) >> 16));
			}
		}
		
		g2d.setStroke( new BasicStroke(lineSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND) );
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        int psize = positions.size();
        for (int i = 0; i < psize-1; i++) {
        	int[] np = positions.get(i + 1);
	        int[] lp = positions.get(i);
	        g2d.drawLine(lp[0], lp[1], np[0], np[1]);
        }
		
		/*
		 * Graphics2D g2d = img.createGraphics();
g2d.setColor(Color.RED);
g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
g2d.dispose();
		 */
		g2d.setColor(Color.WHITE);
		for (Rect rect : filledRects) {
			g2d.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
		}
		
		for (Pixel[] arr : changedPixels) {
			for (Pixel pix : arr) {
				pixels.setRGB(pix.getX(), pix.getY(), pix.getColor());
			}
		}
		
		if (drawSelectRect) {
			g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
			g2d.setColor(Color.BLACK);
			g2d.drawRect(selectRect[0], selectRect[1], selectRect[2], selectRect[3]);
		}
		
		
		g.drawImage(pixels, 0, 0, this);
		g2d.dispose();
		
		
	}

	@Override 
	public Dimension getPreferredSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	@Override
	public void mouseMoved(MouseEvent arg0) {}
	@Override
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}	
}

