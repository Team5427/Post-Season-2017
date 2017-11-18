package org.usfirst.frc5427.postSeason2017;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.Scanner;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Andrew, Gabby, Blake, Abhinav, Kipp Archer Strand icywaters Corman, Gerb, Varsha, Ethan, Aidan, Srikar, Sean, Gerb, Chris, Andrew, Charlie, Dylan, Art, Priyanka, Stack Overflow, Double Dub, Rahul, Philip, and more Gerb.
 */
public class GraphPanel extends JPanel implements Runnable
{
	public static final int numLines = 1;
	public static String dateString;
	private static ArrayList<ArrayList<Double>> lines = new ArrayList<ArrayList<Double>>(numLines);
	private int padding = 25;
	private int labelPadding = 25;
	private Color line1Color = new Color(44, 102, 230, 180);
	private Color line2Color = new Color(255, 51, 51, 180);
	private Color pointColor = new Color(100, 100, 100, 180);
	private Color gridColor = new Color(200, 200, 200, 200);
	private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
	private int pointWidth = 4;
	private int numberYDivisions = 10;
	private List<Double> scores;

	private int xMin = 0;
	private int xMax = 90;
	private int yMin = 0;
	private int yMax = 100;
	private int xShift = 0;
	
	private static Scanner scan;
	private static PrintWriter out;

	public GraphPanel()
	{
		Thread t = new Thread(this);
		t.start();
	}
	
	public static void addLines(int lineNumber, List<Double> linePoints)
	{
		lines.add(lineNumber, (ArrayList<Double>) linePoints);
//		System.out.println("\n\nLine number: " + lineNumber + "\n---------");
//		for (int i = 0; i < linePoints.size(); i++)
//		{
//			System.out.println(linePoints.get(i));
//		}       
//
//		System.out.print(lines.get(lineNumber).size());

	}
	


	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (xMax - xMin);
		double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (yMax - yMin);
//		System.out.print("yscale: "+yScale);
		// List<Point> graphPoints = new ArrayList<>();
		ArrayList<List<Point>> graphPoints = new ArrayList<List<Point>>(lines.size());
		for (int j = 0; j < lines.size(); j++)
		{
			graphPoints.add(j, new ArrayList<Point>());
			for (int i = 0; i < lines.get(j).size(); i++)
			{
				int x1 = (int) (i * xScale + padding + labelPadding) - (int)(xShift * xScale);
				int y1 = (int) ((yMax - (lines.get(j).get(i))) * yScale + padding);
				graphPoints.get(j).add(new Point(x1, y1));
			} // System.out.print(graphPoints.size());
		}
		// draw white background
		g2.setColor(Color.WHITE);
		g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding,
				getHeight() - 2 * padding - labelPadding);
		g2.setColor(Color.BLACK);

		// create hatch marks and grid lines for y axis.
		for (int i = 0; i < numberYDivisions + 1; i++)
		{
			int x0 = padding + labelPadding;
			int x1 = pointWidth + padding + labelPadding;
			int y0 = getHeight()
					- ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
			int y1 = y0;
			if (lines.get(0).size() > 0)
			{
				g2.setColor(gridColor);
				g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
				g2.setColor(Color.BLACK);
				String yLabel = ((int) ((yMin + (yMax - yMin) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
			}
			g2.drawLine(x0, y0, x1, y1);
		}

		// and for x axis
		for (int i = 0; i <= xMax - xMin; i++)
		{
			if (xMax - xMin > 1)
			{
				int x0 = i * (getWidth() - padding * 2 - labelPadding) / (xMax - xMin) + padding + labelPadding;
				int x1 = x0;
				int y0 = getHeight() - padding - labelPadding;
				int y1 = y0 - pointWidth;
				if ((i % ((int) ((xMax - xMin) / 20.0))) == 0)
				{
					g2.setColor(gridColor);
					g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
					g2.setColor(Color.BLACK);
					String xLabel = i + xShift + "";
					FontMetrics metrics = g2.getFontMetrics();
					int labelWidth = metrics.stringWidth(xLabel);
					g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
				}
				g2.drawLine(x0, y0, x1, y1);
			}
		}

		// create x and y axes
		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding,
				getHeight() - padding - labelPadding);
		// Draw lines here
		Stroke oldStroke = g2.getStroke();
		g2.setColor(line1Color);
		g2.setStroke(GRAPH_STROKE);
		for (int j = 0; j < lines.size(); j++)
		{
			if (j == 0)
				g2.setColor(line1Color);
			else
				g2.setColor(line2Color);

//			System.out.println("\nLine " + (j + 1));
			// LINES
			for (int i = (graphPoints.get(j).size()-1)-(xMax-xMin)>0 ? (graphPoints.get(j).size()-1)-(xMax-xMin):0; i < graphPoints.get(j).size() - 1; i++)
			{
				int x1 = graphPoints.get(j).get(i).x;
//				System.out.println("x1: " + x1);
				int y1 = graphPoints.get(j).get(i).y;
//				System.out.println("y1: " + y1);
				int x2 = graphPoints.get(j).get(i + 1).x;
//				System.out.println("x2: " + x2);
				int y2 = graphPoints.get(j).get(i + 1).y;
//				System.out.println("y2: " + y2 + "\n");
				g2.drawLine(x1, y1, x2, y2);
			}
			System.out.print("\n\n");
			// System.out.println(j);
		}
		g2.setStroke(oldStroke);
		g2.setColor(pointColor);
		for (int j = 0; j < lines.size(); j++)
		{
			// POINT

			for (int i = (graphPoints.get(j).size()-1)-(xMax-xMin)>0 ? (graphPoints.get(j).size()-1)-(xMax-xMin):0; i < graphPoints.get(j).size(); i++)
			{
				int x = graphPoints.get(j).get(i).x - pointWidth / 2;
				int y = graphPoints.get(j).get(i).y - pointWidth / 2;
				int ovalW = pointWidth;
				int ovalH = pointWidth;
				g2.fillOval(x, y, ovalW, ovalH);
			}
		}
	}

	// @Override
	// public Dimension getPreferredSize() {
	// return new Dimension(width, heigth);
	// }

	// private double getMinScore() {
	// double minScore = Double.MAX_VALUE;
	// for (Double score : lines.get(0)) {
	// minScore = Math.min(minScore, score);
	// }
	// if(lines.size()>1)
	// {
	// for (Double score : lines.get(1)) {
	// minScore = Math.min(minScore, score);
	// }
	// }
	// return minScore;
	// }

	// private double getMaxScore() {
	// double maxScore = Double.MIN_VALUE;
	// for (Double score : lines.get(0)) {
	// maxScore = Math.max(maxScore, score);
	// }
	// if(lines.size()>1)
	// {
	// for (Double score : lines.get(1)) {
	// maxScore = Math.max(maxScore, score);
	// }
	// }
	// return maxScore;
	// }

	/*
	 * public void setScores(List<Double> scores) { this.scores = scores;
	 * invalidate(); this.repaint(); }
	 */
	/*
	 * public List<Double> getScores() { return scores; }
	 */
	private static void createAndShowGui()
	{
		addPoints();
		GraphPanel mainPanel = new GraphPanel();
		mainPanel.setPreferredSize(new Dimension(800, 600));
		JFrame frame = new JFrame("DrawGraph");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent)
		    {
		    	out.close();
		 
		    	frame.dispose();
		    	
		    	new GraphPointsScrollFrame();
		    	
		    }
		});
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}


	public void addPoint(int lineNum, double data)
	{
		if(lines.get(lineNum).size()>xMax-xMin)
		{
			xShift++;
			xMin++;
			xMax++;
		}
		lines.get(lineNum).add(data);
		out.write((lines.get(lineNum).size()==0 ? "":",") + data + "&"+LocalDateTime.now().toString().substring(14));
	}
	
	public static void addPoints()
	{
		List<Double> scores = new ArrayList<>();
		
		for (int j = 0; j < numLines; j++)
		{
			scores = new ArrayList<>();
//			for (int i = 0; i < maxDataPoints; i++)
//			{
////				allPoints[j * maxDataPoints + i] = Math.random() * maxScore;
////				scores.add(allPoints[j * maxDataPoints + i]);
//				// scores.add((double) i);
//			}
			addLines(j, scores);
		}
	}

	public static void main(String[] args)throws Exception
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();
		dateString = dateFormat.format(date);
		System.out.println(dateString);
		out = new PrintWriter(new File("src/org/usfirst/frc5427/postSeason2017/"+dateString+".txt"));
		scan = new Scanner(new File("src/org/usfirst/frc5427/postSeason2017/"+dateString+".txt"));
		
		final File file = new File("src/"+dateString+".txt");
		file.createNewFile();
		
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				createAndShowGui();
			}
		});
	}
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		while(true)
		{
			try
			{
				Thread.sleep(20);
				addPoint(0,Math.random()*100);
				repaint();
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}