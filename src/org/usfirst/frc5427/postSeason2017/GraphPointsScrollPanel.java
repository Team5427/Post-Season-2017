package org.usfirst.frc5427.postSeason2017;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.io.*;

public class GraphPointsScrollPanel extends JPanel implements MouseListener, MouseMotionListener{

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
	private AffineTransform at;
	private String selectedDate;
	private double velocity;
	private double time;
	/*
	 * X_SCALE_CONSTANT - Variable made to make changing the X Scale easy. Larger
	 * makes the distance between points larger. Smaller makes the distance between
	 * points smaller. PURELY VISUAL - Does not change the data whatsoever.
	 */
	//spacing between each x label
	public int X_SCALE_CONSTANT = 10;
	
	public void update() {
		try {
			readData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		repaint();
	}

	public GraphPointsScrollPanel(int panelHeight) {
		this.selectedDate = GraphPanel.dateString;
		try {
			readData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.xZoomScale = 1;
		this.yZoomScale = 1;
		this.zoomed = false;
		this.panelWidth = points.size() * X_SCALE_CONSTANT+70;
		this.panelHeight = panelHeight;
		// int w = 120 * 50 + 130;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public String getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		// creating y axis lines and labeling
		for (int y = 100; y >= 0; y -= 5) {
			//y coordinate on panel calculation
			int ycoo = (int) ((y / 100.0) * (getHeight() - 90) + 20);
			//label for y line
			g.drawString((100 - y)+"",20,ycoo + 2);
			//line
			g.drawLine(40, ycoo, getWidth(), ycoo);
		}

		// labeling x points, graphing points
		for (int x = 0; x < points.size(); x++) {
			// labeling spec points
			g.setColor(Color.black);
			if (x % 4 == 0) {
				if (yZoomScale != 1.0)
					g.drawString(x + "", x * (int) xZoomScale * X_SCALE_CONSTANT + 30,
							(int) (560 * (yZoomScale + .04)));
				else
					g.drawString(x + "", x * (int) xZoomScale * X_SCALE_CONSTANT + 30, 560);
				}

			// drawing the lines every second
			g.setColor(Color.RED);
			if (x < points.size() - 1) {
				int ycoo1 = (int) (((100 - points.get(x).intValue()) / 100.0) * (getHeight() - 90) + 20);
				int ycoo2 = (int) (((100 - points.get(x + 1).intValue()) / 100.0) * (getHeight() - 90) + 20);
				g.drawLine(x * (int) xZoomScale * X_SCALE_CONSTANT + 35, ycoo1,
						(x + 1) * (int) xZoomScale * X_SCALE_CONSTANT + 35, ycoo2);
			}

			// points
			g.fillOval(x * (int) xZoomScale * X_SCALE_CONSTANT + 35 - 3,
					(int) (((100 - points.get(x).intValue()) / 100.0) * (getHeight() - 90) + 20 - 3), 6, 6);
			g.setColor(Color.black);
			if (x % 4 == 0)
				g.drawLine(x * (int) xZoomScale * X_SCALE_CONSTANT + 35, (getHeight() - 90) + 20,
						x * (int) xZoomScale * X_SCALE_CONSTANT + 35, 0);
		}
		// g.translate(panelWidth/2, panelHeight/2);
		// ((Graphics2D)g).scale(xZoomScale,yZoomScale);
		// g.translate(-panelWidth/2, -panelHeight/2);
		// Graphics2D g2 = (Graphics2D)g;
		// at = new AffineTransform();
		// at.scale(xZoomScale,yZoomScale);
		// ((Graphics2D)g).transform(at);
	}

	public void readData() throws IOException {
		//reads data everytime new date is selected so the attributes must clear
		points.clear();
		timePoints.clear();
		
		BufferedReader br = new BufferedReader(
				new FileReader("HistoryGraphs/" + selectedDate));
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
		if (this.zoomed) {
			this.zoomed = false;
			this.xZoomScale = 1;
			this.yZoomScale = 1;
		} else if (!this.zoomed) {
			this.zoomed = true;
			this.xZoomScale = ZOOM_FACTOR;
			this.yZoomScale = ZOOM_FACTOR;
			this.xZoomCenter = e.getX();
			this.yZoomCenter = e.getY();
		}
		repaint();
		revalidate();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void addNotify() {
		super.addNotify();
	}

	public boolean getZoomed() {
		return this.zoomed;
	}

	public double getXZoomScale() {
		return this.xZoomScale;
	}

	public double getYZoomScale() {
		return this.yZoomScale;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		//This if/else sets shows the X value which mouse is hovering
		double newlocx;
		if(!zoomed) {
			double origin = e.getX() - 35;
			newlocx = (origin/10);
			setTime(newlocx);
		}
		else {
			double origin = e.getX() - 35;
			newlocx = (origin/20);
			setTime(newlocx);
		}
		
		//this section sets the Y value (velocity) based of the results of the X value from time
		int x = 0;
		while(newlocx > x) {
			x++;
			//if mouse hover x is the same as counter, use the y value at the integer index
			if(newlocx > points.size()) {
				setVelocity(0);
			}
			else if(newlocx == x) {
				setVelocity(points.get(x));
			}
			//if in between integer points, then calculate percentage distance between two reference x pts
			//and use the percentage to calculate the y value
			else if(newlocx < x)
			{
				//percent between both x reference points
				double percent = newlocx - (x-1);
				//change in y value in both reference points
				double dvelocity = (points.get(x) - points.get(x-1));
				//calculates the change in y with the percentage* change in y value of ref. points
				double Ychange = percent*dvelocity;
				//sets the velocity by adding the change in y with the previous ref. pt.
				setVelocity(points.get(x-1) + Ychange);
			}
		}
			
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	
}