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
import java.util.ArrayList;

import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 1L;
	
	ArrayList<int[]> positions = new ArrayList<int[]>();
	ArrayList<int[]> lines = new ArrayList<int[]>();
	private short count = 0;
	
	Color color = Color.BLUE;
	int lineSize = 4;
	
	

	public Canvas() {
		setBackground(Color.WHITE);
		addMouseListener(this);
		addMouseMotionListener(this);
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		count++;
		if (count >= 1) {
			int size = positions.size();
			int x = e.getX();
			int y = e.getY();
			if (x != positions.get(size-1)[0] || y != positions.get(size-1)[1]) {
				positions.add(new int[] {x, y});
				if (size > 1) {
					Graphics2D g2d = (Graphics2D) getGraphics();
					//BasicStroke stroke = 
		            g2d.setStroke( new BasicStroke(lineSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND) );
		            g2d.setRenderingHint(
		                    RenderingHints.KEY_ANTIALIASING,
		                    RenderingHints.VALUE_ANTIALIAS_ON);
		            g2d.setColor(color);
		            
		            int[] lp = positions.get(size - 2);
		            g2d.drawLine(lp[0], lp[1], x, y);
				}
			}
			
			
			count = 0;
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		positions.add(new int[] {e.getX(), e.getY()});
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		int len = positions.size();
		int[] pointes = new int[len+2];
		
		pointes[0] =  color.getRGB(); //size | ( ((long) color.getRGB()) << 32);
		pointes[1] = lineSize;
		
		//int to = pointes.length - 1;
		for (int i = 0; i < len; i++) {
			int[] one = positions.get(i);
			//int[] two = positions.get(i+1);
			
			pointes[i + 2] = ((short) one[0]) | ( ((short) one[1]) << 16 );
		}
		lines.add(pointes);
		
		//int end = ((int) ((pointes[0] & 0xffffffff00000000l) >>> 32)); // + 
		//int size = ((int) (pointes[0] & 0xffffffff));
		
		positions.clear();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		for (int[] line : lines) {
			g2d.setStroke(new BasicStroke(line[1], BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(new Color(line[0]));
			for (int i = 2; i < line.length-1; i++) {
				g2d.drawLine( (line[i] & 0xffff), ((line[i] & 0xffff0000) >> 16),
						(line[i + 1] & 0xffff), ((line[i + 1] & 0xffff0000) >> 16));
			}
		}
		
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

