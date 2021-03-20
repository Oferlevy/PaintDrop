package PaintDrop.Main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

public class PaintDrop extends JFrame implements MouseListener {
	 /*
	 * V1.0 got deleted
	 * V1.1 better canvas save + editor
	 * Made by: Nave Weksler, Ofer Levy
	 * 
	 * Rules:
	 * always pull the code before starting
	 * don't change other people's code without their approval
	 * send only the file you worked on to GitHub
	 * make sure the code is working and written well before sending it
	 * post an issue if your code doesn't work and don't change the main brunch
	 * in order to test something, do it in package PaintDrop.Tests and don't write it in the README
	 * 
	 * Syntax Rules:
	 * add comments on every function + complicated parts in a class
	 * no more then two empty lines
	 * don't leave empty lines for no reason
	 * remove unused imports
	 */
	
	private static final long serialVersionUID = 1L;
	
	public Edit edit;
	private JMenu editMenu;
	private JMenu drawMenu;
	private CardLayout cardLayout;
	private JPanel CardPanel;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new PaintDrop();
			}
		});
	}
	
	public PaintDrop() {
		edit = new Edit();
		// the message on close are you sure you want to exit
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent windowEvent) {
	        	if (JOptionPane.showConfirmDialog(null, "Are You Sure You Want to Exit", "Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
	        		System.exit(1);
	        	}
	        }
	    });
		
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(500, 500);
		getContentPane().setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		
		ImageIcon img = new ImageIcon("paint-drop-icon.png");
		setIconImage(img.getImage());
		

		JMenuBar bar = new JMenuBar();
		bar.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
		
		JMenu menu = new JMenu("File");
		JMenuItem item;
		item = new JMenuItem("New");
		menu.add(item);
		
		item = new JMenuItem("Open");
		menu.add(item);
		
		menu.addSeparator();
		item = new JMenuItem("Save");
		menu.add(item);
		
		item = new JMenuItem("Save As");
		menu.add(item);
		bar.add(menu);
		
		// Edit & Draw panels switch using CardLayout
		cardLayout = new CardLayout();
		CardPanel = new JPanel(cardLayout);
		CardPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
		
		JPanel drawPanel = new JPanel();
		drawPanel.setBackground(new Color(240,240,240));
		CardPanel.add(drawPanel, "draw"); // adding the drawPanel as "draw"
		drawPanel.setLayout(new GridLayout(2, 80));
		
		JButton setColor = new JButton("Color");
		setColor.addActionListener(e -> {
			Color c = JColorChooser.showDialog(this,"Select a color",Color.BLACK);
			if (c != null) {
				edit.getCanvas().color = c;
			}
			
		});
		
		setColor.setFocusPainted(false);
		setColor.setContentAreaFilled(false);
		setColor.setBorder( BorderFactory.createLineBorder(new Color(200, 200, 200), 5) );
		setColor.setPreferredSize(new Dimension(10,10));
		
		final JSpinner setSize = new JSpinner();
		setSize.setValue(4);
		JComponent comp = setSize.getEditor();
		JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
		DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
		formatter.setCommitsOnValidEdit(true);
		setSize.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				edit.getCanvas().lineSize = (int) setSize.getValue();
			}
		});
		    
		setSize.setBorder( BorderFactory.createLineBorder(Color.BLACK, 1) );
		setSize.setPreferredSize(new Dimension(10,10));
			
		drawPanel.add(setSize);
		drawPanel.add(setColor);
		
		
		JPanel editPanel = new JPanel();
		editPanel.setBackground(new Color(240, 240, 240));
		CardPanel.add(editPanel, "edit"); // adding the editPanel as "edit"
		editPanel.setLayout(new GridLayout(2, 8));
		
		JButton selectArea = new JButton("Select");
		//selectArea.setFocusPainted(false);
		selectArea.setContentAreaFilled(false);
		selectArea.setBorder( BorderFactory.createLineBorder(new Color(170, 170, 190), 2) );
		selectArea.addActionListener(e -> {
			edit.getCanvas().drawSelectRect = true;
		});
		editPanel.add(selectArea);
		
		//button to call the Edit.filter
		JButton filterArea = new JButton("Filter");
		filterArea.setContentAreaFilled(false);
		filterArea.setBorder( BorderFactory.createLineBorder(new Color(170, 170, 190), 1) );
		filterArea.addActionListener(e -> {
			//get color to remove and color to replace
			Color rev = JColorChooser.showDialog(this,"Select color to remove",Color.BLACK);
			Color rep = JColorChooser.showDialog(this,"Select color to replace",Color.BLACK);
			
			Canvas canvas = edit.getCanvas();
			// if the user selected a box
			if (canvas.selectRect[0] != 0 || canvas.selectRect[1] != 0 ||
					canvas.selectRect[2] != 0 || canvas.selectRect[3] != 0) {
				System.out.println("AAAA");
				edit.filter(canvas.selectRect[0], canvas.selectRect[1], canvas.selectRect[2],
						canvas.selectRect[3], rev, rep);
			}
		});
		editPanel.add(filterArea);
		
		drawMenu = new JMenu("Draw");
		drawMenu.addMouseListener(this);
		bar.add(drawMenu);
		
		editMenu = new JMenu("Edit");
		editMenu.addMouseListener(this);
		bar.add(editMenu);
		
		add(CardPanel, BorderLayout.NORTH);
		setJMenuBar(bar);
		
		// add scrolling to the canvas
		JScrollPane scroll = new JScrollPane(edit.getCanvas());
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll);
		
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// click on JMenu draw / edit
		JMenu act = (JMenu) e.getSource();
		if (act == drawMenu) {
			// show the "draw" panel of the CardLayout above (DrawPanel)
			cardLayout.show(CardPanel, "draw");
			edit.getCanvas().drawSelectRect = false;
		} else if (act == editMenu) {
			// show the "edit" panel of the CardLayout above (editPanel)
			System.out.println("edit");
			cardLayout.show(CardPanel, "edit");
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
