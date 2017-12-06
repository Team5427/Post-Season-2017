/**

 * Created by respe on 9/27/2017.
 */
package org.usfirst.frc5427.postSeason2017;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class GraphPointsScrollFrame extends JFrame implements MouseListener {

	JScrollPane sp_textScroller = null;
	JLabel y_lbl = null;
	GraphPointsScrollPanel panel = null;

	public GraphPointsScrollFrame() {

		super(GraphPanel.dateString);

		setSize(1200, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		panel = new GraphPointsScrollPanel(600);
		panel.addMouseListener(this);
		sp_textScroller = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp_textScroller.setBounds(150, 25, getWidth() - 100, getHeight() - 70);
		sp_textScroller.addMouseListener(this);
		this.getContentPane().addMouseListener(this);
		add(sp_textScroller);
		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.BLACK);
		for (int y = 100; y >= 0; y -= 5) {
			int ycoo = (int) ((y / 120.0) * (getHeight() - 90) + 100);
			g.drawString((100 - y) + "", 140, ycoo);
		}
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
}
