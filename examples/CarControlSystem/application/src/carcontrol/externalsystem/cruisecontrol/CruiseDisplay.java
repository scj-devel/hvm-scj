package carcontrol.externalsystem.cruisecontrol;

import java.awt.*;

import javax.swing.JPanel;

public class CruiseDisplay extends /*Canvas*/ JPanel {


    private int recorded     = 0;     //recorded speed
    private boolean cruiseOn = false; //cruise control state
    private final static int botY = 200;
    private Font small = new Font("Helvetica",Font.BOLD,14);
    private Font big = new Font("Helvetica",Font.BOLD,18);

    public CruiseDisplay() {
        super();
        setSize(150,260);
    }


    Image offscreen;
    Dimension offscreensize;
    Graphics offgraphics;

    public void backdrop() {
        Dimension d = getSize();
	    if ((offscreen == null) || (d.width != offscreensize.width)
	                            || (d.height != offscreensize.height)) {
	        offscreen = createImage(d.width, d.height);
	        offscreensize = d;
	        offgraphics = offscreen.getGraphics();
	        offgraphics.setFont(small);
	    }
        offgraphics.setColor(Color.black);
        offgraphics.fillRect(0, 0, getSize().width, getSize().height);
        offgraphics.setColor(Color.white);
        offgraphics.drawRect(5,10,getSize().width-15,getSize().height-40);
        offgraphics.setColor(Color.blue);
        offgraphics.fillRect(6,11,getSize().width-17,getSize().height-42);
     }

    public void paint(Graphics g) {
         update(g);
    }

    public void update(Graphics g) {
        backdrop();
        // display recorded speed
        offgraphics.setColor(Color.white);
        offgraphics.setFont(big);
        offgraphics.drawString("Cruise Control",10,35);
        offgraphics.setFont(small);
        drawRecorded(offgraphics,20,80,recorded);
        if (cruiseOn)
            offgraphics.drawString("Enabled",20,botY+15);
        else
            offgraphics.drawString("Disabled",20,botY+15);
        if (cruiseOn)
           offgraphics.setColor(Color.green);
        else
           offgraphics.setColor(Color.red);
        offgraphics.fillArc(90,botY,20,20,0,360);
        g.drawImage(offscreen, 0, 0, null);
    }

    public void drawRecorded(Graphics g, int x, int y, int speed) {
        g.drawString("Cruise Speed",x,y+10);
        g.drawRect(x+20,y+20,50,20);
        g.setFont(big);
        g.drawString(String.valueOf(speed),x+30,y+37);
        g.setFont(small);
    }

    public void enabled() {
        cruiseOn = true;
        repaint();
    }

    public void disabled() {
        cruiseOn = false;
        repaint();
    }

    public void record(int speed) {
        recorded=speed;
        repaint();
    }
}