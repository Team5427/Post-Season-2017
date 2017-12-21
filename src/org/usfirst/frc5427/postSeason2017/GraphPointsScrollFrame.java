/**

 * Created by respe on 9/27/2017.
 */
package org.usfirst.frc5427.postSeason2017;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.awt.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JSplitPane;

public class GraphPointsScrollFrame extends JFrame implements MouseListener, Runnable{

	JScrollPane sp_textScroller;
	
	GraphPointsScrollPanel panel = null;
	private JComboBox fileDates;
	
	//JSplitPane splitPane;
	private String[] fileArray;

	public GraphPointsScrollFrame() {

		super("HistoryGraph");
		Thread t = new Thread(this);
		setSize(1200, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		fileArray = fileStringArray();
		panel = new GraphPointsScrollPanel(600);
		panel.addMouseListener(this);
		
		//dropdown of file dates
		fileDates = new JComboBox(fileArray);
		fileDates.setBounds(getWidth() - 290, 25, getWidth() - (getWidth() - 290), 50);
		
		//panel is added to jscroll pane
		sp_textScroller = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//dimensions of the history graph/ jscroll pane (does not affect panel size)
		sp_textScroller.setBounds(10, 25, getWidth() - 300, getHeight() - 70);
		sp_textScroller.addMouseListener(this);
		this.getContentPane().addMouseListener(this);
		
		
		add(sp_textScroller);
		add(fileDates);
		setVisible(true);
		setResizable(false);
		t.start();
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.BLACK);
		//old y scaling labeling (not needed anymore)
		/*for (int y = 100; y >= 0; y -= 5) {
			int ycoo = (int) ((y / 120.0) * (getHeight() - 90) + 100);
			g.drawString((100 - y) + "", 140, ycoo);
		}
		*/
		sp_textScroller.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (panel.getZoomed()) {
			sp_textScroller.getHorizontalScrollBar()
					.setValue((int) (sp_textScroller.getHorizontalScrollBar().getValue() * panel.ZOOM_FACTOR));
			sp_textScroller.getVerticalScrollBar()
					.setValue((int) (sp_textScroller.getVerticalScrollBar().getValue() * panel.ZOOM_FACTOR));
		} else if (!(panel.getZoomed())) {
			sp_textScroller.getHorizontalScrollBar()
					.setValue((int) (sp_textScroller.getHorizontalScrollBar().getValue() / panel.ZOOM_FACTOR));
			sp_textScroller.getVerticalScrollBar()
					.setValue((int) (sp_textScroller.getVerticalScrollBar().getValue() / panel.ZOOM_FACTOR));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addNotify() {
		super.addNotify();
	}
	
	public String[] fileStringArray() {
		//makes an array of files; index 0 is most recent.
		File folder = new File("HistoryGraphs");
		File[] listOfFiles = folder.listFiles();
		String[] fileStringArray = new String[listOfFiles.length];
		    for (int i = listOfFiles.length-1; i>=0; i--) {
		      if (listOfFiles[i].isFile()) {
		    	  fileStringArray[i] = listOfFiles[(listOfFiles.length-1)-i].getName();
		    	  System.out.println(fileStringArray[i]);
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }
		
		return fileStringArray;
	}

	public JComboBox getFileDates() {
		return fileDates;
	}

	public void setFileDates(JComboBox fileDates) {
		this.fileDates = fileDates;
	}

	public String[] getFileArray() {
		return fileArray;
	}

	public void setFileArray(String[] fileArray) {
		this.fileArray = fileArray;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			if(panel.getSelectedDate() != fileArray[fileDates.getSelectedIndex()]) {
				panel.setSelectedDate(fileArray[fileDates.getSelectedIndex()]);
				panel.update();
			}
			
		}
	}
}
