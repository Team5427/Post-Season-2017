package org.usfirst.frc5427.postSeason2017;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.io.*;

public class GraphPointsScrollPanel extends JPanel implements MouseListener {

	ArrayList<Double> points = new ArrayList<>();
	ArrayList<String> timePoints = new ArrayList<>();
	private int panelWidth;
	private int panelHeight;
	private double xZoomScale;
	private double yZoomScale;
	private boolean zoomed;
	public final double ZOOM_FACTOR = 2;
	private int xZoomCenter;
	private int yZoomCenter;
	
	/*
	 * X_SCALE_CONSTANT - Variable made to make changing the X Scale easy. Larger
	 * makes the distance between points larger. Smaller makes the distance between
	 * points smaller. PURELY VISUAL - Does not change the data whatsoever.
	 */
	public final int X_SCALE_CONSTANT = 10;

	public GraphPointsScrollPanel(int panelHeight) {
		try {
			readData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.xZoomScale = 1;
		this.yZoomScale = 1;
		this.zoomed = false;
		
		this.panelWidth = points.size() * 100 + 100;
		this.panelHeight = panelHeight;
		// int w = 120 * 50 + 130;
		addMouseListener(this);
	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		// creating y axis lines and labeling
		for (int y = 100; y >= 0; y -= 5) {
			int ycoo = (int) ((y / 100.0) * (getHeight() - 90) + 20);
			// g.drawString((100 - y)+"",20,ycoo + 2);
			g.drawLine(40, ycoo, getWidth(), ycoo);
		}

		// labeling x points, graphing points
		for (int x = 0; x < points.size(); x++) {
			// labeling spec points
			g.setColor(Color.black);
			if (x % 4 == 0)
				g.drawString(x + "", x * X_SCALE_CONSTANT + 30, 560);

			// drawing axis lines every second
			g.setColor(Color.RED);
			if (x < points.size() - 1) {
				int ycoo1 = (int) (((100 - points.get(x).intValue()) / 100.0) * (getHeight() - 90) + 20);
				int ycoo2 = (int) (((100 - points.get(x + 1).intValue()) / 100.0) * (getHeight() - 90) + 20);
				g.drawLine(x * X_SCALE_CONSTANT + 35, ycoo1, (x + 1) * X_SCALE_CONSTANT + 35, ycoo2);
			}

			// points

			g.fillOval(x * X_SCALE_CONSTANT + 35 - 3,
					(int) (((100 - points.get(x).intValue()) / 100.0) * (getHeight() - 90) + 20 - 3), 6, 6);
			g.setColor(Color.black);
			if (x % 4 == 0)
				g.drawLine(x * X_SCALE_CONSTANT + 35, (getHeight() - 90) + 20, x * X_SCALE_CONSTANT + 35, 0);
			g.translate(panelWidth/2, panelHeight/2);
			((Graphics2D)g).scale(xZoomScale,yZoomScale);
			g.translate(-panelWidth/2, -panelHeight/2);
		}

	}

	public void readData() throws IOException {
		BufferedReader br = new BufferedReader(
				new FileReader("src/org/usfirst/frc5427/postSeason2017/" + GraphPanel.dateString + ".txt"));
		String s = br.readLine();
		String[] pointsText = (s.split(","));
		ArrayList<String> ypoints = new ArrayList<>();
		for (int x = 0; x < pointsText.length; x++) {
			String y = pointsText[x];

			String[] yp = y.split("&");
			ypoints.add(y.split("&")[0]);
			if (x != 0)
				timePoints.add(y.split("&")[1]);
			// for(int i = 1;i<yp.length;i++){
			// System.out.print(","+yp[i]);
			// }
		}
		for (int i = 1; i < pointsText.length; i++) {
			points.add(Double.parseDouble(ypoints.get(i)));
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension((int) (this.panelWidth * xZoomScale), (int) (this.panelHeight * yZoomScale));
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(this.zoomed)
		{
			this.zoomed = false;
			this.xZoomScale = 1;
			this.yZoomScale = 1;
		}
		else if(!this.zoomed)
		{
			this.zoomed = true;
			this.xZoomScale = ZOOM_FACTOR;
			this.yZoomScale = ZOOM_FACTOR;
			this.xZoomCenter = e.getX();
			this.yZoomCenter = e.getY();
		}
		System.out.println(this.xZoomScale);
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void addNotify()
	{
		super.addNotify();
	}
}