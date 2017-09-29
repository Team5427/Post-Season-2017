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

    public GraphPointsScrollFrame()
    {
        
        super("Graph Points Scroll");
        setSize(1700,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        sp_textScroller = new JScrollPane(new GraphPointsScrollPanel(600),JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp_textScroller.setBounds(50,25,getWidth()-300,getHeight()-70);
        
        add(sp_textScroller);

        setVisible(true);
    }

    @Override
    public void paint(Graphics g)
    {
    	g.setColor(Color.WHITE);
    	g.fillRect(0, 0, getWidth(), getHeight());
    	
    	g.setColor(Color.BLACK);
        for(int y = 100; y >= 0; y-=5) {
<<<<<<< HEAD
            int ycoo  = (int)((y / 86.0) * (600-90)+ 20);
=======
            int ycoo  = (int)((y / 100.0) * (getHeight()-90)+ 20);
>>>>>>> c0982b8... made y axis NOT BE AN IMAGE AKSHAT U IDIOT
            g.drawString((100 - y)+"",20,ycoo + 2);
        }
        sp_textScroller.repaint();
    }


}
