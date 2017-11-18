/**

 * Created by respe on 9/27/2017.
 */
package org.usfirst.frc5427.postSeason2017;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
public class GraphPointsScrollFrame extends JFrame  {


    JScrollPane sp_textScroller = null;
    JLabel y_lbl = null;

    public GraphPointsScrollFrame()
    {
        
        super(GraphPanel.dateString);
        
        setSize(1200,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        sp_textScroller = new JScrollPane(new GraphPointsScrollPanel(600),JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp_textScroller.setBounds(150,25,getWidth()-100,getHeight()-70);
        add(sp_textScroller);

        setVisible(true);
    }
    
    @Override
    public void paint(Graphics g)
    {
    	g.setColor(Color.WHITE);
    	g.fillRect(0, 0, getWidth(), getHeight());
    	
    	g.setColor(Color.BLACK);
    	for(int y=100;y>=0;y-=5)
    	{
    		int ycoo = (int)((y/120.0)*(getHeight()-90)+100);
    		g.drawString((100-y)+"", 140, ycoo);
    	}
    	sp_textScroller.repaint();
    }
}
