package org.usfirst.frc5427.postSeason2017;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.*;

public class GraphPointsScrollPanel extends JPanel
{

    ArrayList<Double> points = new ArrayList<>();
    ArrayList<String> timePoints = new ArrayList<>();
    public GraphPointsScrollPanel(int h)
    {
        try {
            readData();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        int w = points.size() * 100 + 100;
        //int w = 120 * 50 + 130;
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
//            g.drawString((100 - y)+"",20,ycoo + 2);
            g.drawLine(40, ycoo, getWidth(), ycoo);
        }
        
            
            //drawing axis lines every second
            g.drawLine(5, ycoo, getWidth(), ycoo);
        }

        //labeling x points, graphing points
        for(int x = 0; x < points.size(); x++){
            g.setColor(Color.black);
            g.drawString(x + "",x * 50 + 5,580 );
            g.setColor(Color.RED);
            if(x < points.size()-1) {
                int ycoo1  = (int)(((100 - points.get(x).intValue())/ 100.0) * (getHeight()-90)+ 20);
                int ycoo2  = (int)(((100 - points.get(x+1).intValue())/ 100.0) * (getHeight()-90)+ 20);
<<<<<<< HEAD
                g.drawLine(x * 100 + 35, ycoo1, (x + 1) * 100 + 35, ycoo2);
            }
            
            //points
            
            
            g.fillOval(x*100+35-3,(int)(((100 - points.get(x).intValue())/ 100.0) * (getHeight()-90)+ 20 - 3),6,6);
            g.setColor(Color.black);
            g.drawLine(x*100+35, (getHeight()-90)+ 20, x*100+35, 0);
=======
                g.drawLine(x * 50 + 5, ycoo1, (x + 1) * 50 + 5, ycoo2);
            }
            g.fillOval(x*50+5-3,(int)(((100 - points.get(x).intValue())/ 100.0) * (getHeight()-90)+ 20 - 3),6,6);
            g.setColor(Color.black);
            g.drawLine(x*50+5, (getHeight()-90)+ 20, x*50+5, 0);
>>>>>>> 54e0c72... scrollable history graph
        }
        
       

    }


    public void readData() throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader("src/org/usfirst/frc5427/postSeason2017/GraphPoints.txt"));
        String s = br.readLine();
        String[] pointsText = (s.split(","));
<<<<<<< HEAD
<<<<<<< HEAD
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
=======
        for (int i = 1; i < pointsText.length; i++) {
            points.add(Double.parseDouble(pointsText[i]));
>>>>>>> 54e0c72... scrollable history graph
=======
        ArrayList<String> ypoints = new ArrayList<>();
        for(int x = 0; x< pointsText.length;x++){
        	String y = pointsText[x];
        	System.out.println(y+"");
        	ypoints.add(y.split("&")[0]);
        	
        }
        for (int i = 1; i < pointsText.length; i++) {
            points.add(Double.parseDouble(ypoints.get(i)));
>>>>>>> 3b2e71e... adding time to txt file
        }
    }
}