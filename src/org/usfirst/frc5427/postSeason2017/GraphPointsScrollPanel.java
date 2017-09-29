package org.usfirst.frc5427.postSeason2017;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.*;

public class GraphPointsScrollPanel extends JPanel
{

    ArrayList<Double> points = new ArrayList<>();
    ArrayList<String> timePoints = new ArrayList<>();
    int spacing;
    int interval;
    public GraphPointsScrollPanel(int h)
    {
    	interval = 1;
        spacing = 1000;
        
        try {
            readData();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        int w = (int) ((points.size() * spacing)*.025 + 100);
        
        
        setPreferredSize(new Dimension(w,h));
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.BLACK);
        //creating y axis lines and labeling
        for(int y = 100; y >= 0; y-=5) {
            int ycoo  = (int)((y / 100.0) * (getHeight()-90)+ 20);
            //g.drawString((100 - y)+"",20,ycoo + 2);
            g.drawLine(40, ycoo, getWidth(), ycoo);
        }
        
      
        double timeBeg = Integer.parseInt(timePoints.get(0).split(":")[0])*60 + Double.parseDouble(timePoints.get(0).split(":")[1]);
     
        
        //labeling x points, graphing points
        
        for(int x = 0; x < points.size(); x++){
        	//labeling spec points
            g.setColor(Color.black);
            g.drawString(x+"s",x * spacing + 30,580 );
            
            
            //finding sec
            String[] timeAtX = timePoints.get(x).split(":");
            double timeSinceBeg =  (Integer.parseInt(timeAtX[0])*60 + Double.parseDouble(timeAtX[1])) - timeBeg;
            
      
            
            
            //drawing lines every second
            g.setColor(Color.RED);
            if(x < points.size()-1) {
            	
                int ycoo1  = (int)(((100 - points.get(x).intValue())/ 100.0) * (getHeight()-90)+ 20);
                int ycoo2  = (int)(((100 - points.get(x+1).intValue())/ 100.0) * (getHeight()-90)+ 20);
                
                String[] timeAtXPlus1 = timePoints.get(x+1).split(":");
                double timeSinceBegPlus1 =  (Integer.parseInt(timeAtXPlus1[0])*60 + Double.parseDouble(timeAtXPlus1[1])) - timeBeg;
                
                g.drawLine((int) (timeSinceBeg*spacing + 35), ycoo1, (int) (timeSinceBegPlus1*spacing + 35), ycoo2);
            }
            
            //lines and point
            
            g.fillOval((int) (timeSinceBeg*spacing+35-3),(int)(((100 - points.get(x).intValue())/ 100.0) * (getHeight()-90)+ 20 - 3),6,6);
            g.setColor(Color.black);
            g.drawLine(x*spacing+35, (getHeight()-90)+ 20, x*spacing+35, 0);
        }
        
       

    }


    public void readData() throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader("src/org/usfirst/frc5427/postSeason2017/GraphPoints.txt"));
        String s = br.readLine();
        String[] pointsText = (s.split(","));
        ArrayList<String> ypoints = new ArrayList<>();
        for(int x = 0; x< pointsText.length;x++){
        	String y = pointsText[x];
        	
        	String[] yp = y.split("&");
        	ypoints.add(y.split("&")[0]);
        	if(x!=0)
        		timePoints.add(y.split("&")[1]);
//        	for(int i = 1;i<yp.length;i++){
//        		System.out.print(","+yp[i]);
//        	}
        }
        for (int i = 1; i < pointsText.length; i++) {
            points.add(Double.parseDouble(ypoints.get(i)));
        }
    }
}