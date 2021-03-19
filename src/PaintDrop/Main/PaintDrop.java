package PaintDrop.Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PaintDrop extends JFrame{
	/**
	 * V1.0 got deleted
	 * V1.1 better canvas save + editor
	 */
	public final Edit edit;
	private static final long serialVersionUID = 1L;
	
	

	public static void main(String[] args) {
		try {
			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
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
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
	        @Override
	        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	        	if (JOptionPane.showConfirmDialog(null, "Are You Sure You Want to Exit","Warning", JOptionPane.YES_NO_OPTION) ==  JOptionPane.YES_OPTION) {
	        		System.exit(1);
	        	}
	        }
	    });
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setUndecorated(true);
		setSize(500, 500);
		getContentPane().setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		ImageIcon img = new ImageIcon("paint-drop-icon.png");
		setIconImage(img.getImage());
		
		JMenuBar bar = new JMenuBar();
		bar.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
		
		JMenu file = new JMenu("File");
		JMenuItem item;
		
		
		item = new JMenuItem("New");
		file.add(item);
		item = new JMenuItem("Open");
		file.add(item);
		file.addSeparator();
		item = new JMenuItem("Save");
		file.add(item);
		item = new JMenuItem("Save As");
		file.add(item);
		bar.add(file);
		
		setJMenuBar(bar);
		
		edit = new Edit();
		JScrollPane scroll = new JScrollPane(edit.canvas);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		add(scroll);
		setVisible(true);
		
		
	}

}
