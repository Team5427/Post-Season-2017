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
        
        super("Graph Points Scroll");
        setSize(1200,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        sp_textScroller = new JScrollPane(new GraphPointsScrollPanel(600),JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp_textScroller.setBounds(150,25,getWidth()-100,getHeight()-70);
        
        y_lbl = new JLabel();
        y_lbl.setIcon(new ImageIcon("src/org/usfirst/frc5427/postSeason2017/yLabels.png"));
        y_lbl.setBounds(110, -10, 100, 630);
        
        add(sp_textScroller);
        add(y_lbl);

        setVisible(true);
    }




}
