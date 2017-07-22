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
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Andrew
 */
public class GraphPanel extends JPanel implements Runnable {
	public static final int numLines = 2;
	private static ArrayList<ArrayList<Double>> lines = new ArrayList<ArrayList<Double>>(numLines);
	private int width = 800;
	private int heigth = 400;
	private int padding = 25;
	private int labelPadding = 25;
	private Color line1Color = new Color(44, 102, 230, 180);
	private Color line2Color = new Color(255, 51, 51, 180);
	private Color pointColor = new Color(100, 100, 100, 180);
	private Color gridColor = new Color(200, 200, 200, 200);
	private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
	private int pointWidth = 4;
	private int numberYDivisions = 10;
//	private List<Double> scores;

	public GraphPanel() {
//		this.scores = createAndShowGui();
		createAndShowGui();
		lines.add(0,null);
		lines.add(1,null);
	}

	public static void addLines(int lineNumber, List<Double> linePoints) {
		lines.add(lineNumber, (ArrayList<Double>) linePoints);
		System.out.println("\n\nLine number: " + lineNumber + "\n---------");
		for (int i = 0; i < linePoints.size(); i++) {
			System.out.println(linePoints.get(i));
		}

		System.out.print(lines.get(lineNumber).size());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		double xScale = 1;
		if(lines.get(0)!=null)
			xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (lines.get(0).size() - 1);
		double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());
		System.out.print(yScale);
		// List<Point> graphPoints = new ArrayList<>();
		System.out.print(getMaxScore());
		ArrayList<List<Point>> graphPoints = new ArrayList<List<Point>>(lines.size());
		for (int j = 0; j < lines.size(); j++) {
			graphPoints.add(j, new ArrayList<Point>());
			if(lines.get(j)!=null)
			{
			for (int i = 0; i < lines.get(j).size(); i++) {
				int x1 = (int) (i * xScale + padding + labelPadding);
				int y1 = (int) ((getMaxScore() - lines.get(j).get(i)) * yScale + padding);
				graphPoints.get(j).add(new Point(x1, y1));
			} // System.out.print(graphPoints.size());
			}
		}
		// draw white background
		g2.setColor(Color.WHITE);
		g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding,
				getHeight() - 2 * padding - labelPadding);
		g2.setColor(Color.BLACK);

		// create hatch marks and grid lines for y axis.
		for (int i = 0; i < numberYDivisions + 1; i++) {
			int x0 = padding + labelPadding;
			int x1 = pointWidth + padding + labelPadding;
			int y0 = getHeight()
					- ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
			int y1 = y0;
			if (lines.get(0)!=null&&lines.get(0).size() > 0) {
				g2.setColor(gridColor);
				g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
				g2.setColor(Color.BLACK);
				String yLabel = ((int) ((getMinScore()
						+ (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
			}
			g2.drawLine(x0, y0, x1, y1);
		}

		// and for x axis
		if(lines.get(0)!=null)
		{
		for (int i = 0; i < lines.get(0).size(); i++) {
			if (lines.get(0).size() > 1) {
				int x0 = i * (getWidth() - padding * 2 - labelPadding) / (lines.get(0).size() - 1) + padding + labelPadding;
				int x1 = x0;
				int y0 = getHeight() - padding - labelPadding;
				int y1 = y0 - pointWidth;
				if ((i % ((int) ((lines.get(0).size() / 20.0)) + 1)) == 0) {
					g2.setColor(gridColor);
					g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
					g2.setColor(Color.BLACK);
					String xLabel = i + "";
					FontMetrics metrics = g2.getFontMetrics();
					int labelWidth = metrics.stringWidth(xLabel);
					g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
				}
				g2.drawLine(x0, y0, x1, y1);
			}
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
		for (int j = 0; j < lines.size(); j++) {
			if (j == 0)
				g2.setColor(line1Color);
			else
				g2.setColor(line2Color);

			System.out.println("\nLine " + (j + 1));
			// LINES
			for (int i = 0; i < graphPoints.get(j).size() - 1; i++) {
				int x1 = graphPoints.get(j).get(i).x;
				System.out.println("x1: " + x1);
				int y1 = graphPoints.get(j).get(i).y;
				System.out.println("y1: " + y1);
				int x2 = graphPoints.get(j).get(i + 1).x;
				System.out.println("x2: " + x2);
				int y2 = graphPoints.get(j).get(i + 1).y;
				System.out.println("y2: " + y2 + "\n");
				g2.drawLine(x1, y1, x2, y2);
			}
			System.out.print("\n\n");
			// System.out.println(j);
		}
		g2.setStroke(oldStroke);
		g2.setColor(pointColor);
		for (int j = 0; j < lines.size(); j++) {
			// POINT

			for (int i = 0; i < graphPoints.get(j).size(); i++) {
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

	private double getMinScore() {
		double minScore = Double.MAX_VALUE;
		if(lines.get(0)!=null)
		{
		for (Double score : lines.get(0)) {
			minScore = Math.min(minScore, score);
		}
		}
		if(lines.get(1)!=null)
		{
		for (Double score : lines.get(1)) {
			minScore = Math.min(minScore, score);
		}
		}
		return minScore;
	}

	private double getMaxScore() {
		double maxScore = Double.MIN_VALUE;
		if(lines.get(0)!=null)
		{
		for (Double score : lines.get(0)) {
			maxScore = Math.max(maxScore, score);
		}
		}
		if(lines.get(1)!=null)
		{
		for (Double score : lines.get(1)) {
			maxScore = Math.max(maxScore, score);
		}
		}
		return maxScore;
	}

	/*
	 * public void setScores(List<Double> scores) { this.scores = scores;
	 * invalidate(); this.repaint(); }
	 */
	/*
	 * public List<Double> getScores() { return scores; }
	 */
	private static void createAndShowGui() {
//		List<Double> scores = new ArrayList<>();
//
//		// TODO: replace values
//		int maxDataPoints = 40;
//		int maxScore = 100;
//		double[] allPoints = new double[numLines * maxDataPoints];
//		for (int j = 0; j < numLines; j++) {
//			scores = new ArrayList<>();
//			for (int i = 0; i < maxDataPoints; i++) {
//				allPoints[j * maxDataPoints + i] = Math.random() * maxScore;
//				scores.add(allPoints[j * maxDataPoints + i]);
//				// scores.add((double) i);
//			}
//			addLines(j, scores);
//		}

		GraphPanel mainPanel = new GraphPanel();
		mainPanel.setPreferredSize(new Dimension(800, 600));
		JFrame frame = new JFrame("DrawGraph");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
//	 public static void main(String[] args) {
//	 SwingUtilities.invokeLater(new Runnable() {
//	 public void run() {
//	 createAndShowGui();
//	 }
//	 });
//	 }

	public void update(double value, int line) {
		lines.get(line).add(value);
	}

	@Override
	public void run() {
		while (true) {

			repaint();
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}