package carcontrol.externalsystem;

import java.awt.*;
import javax.swing.JPanel;

public class SpeedometerPanel extends JPanel {

    volatile private int speed = 0;                //speed 0 .. 120 mph
    volatile private int distance = 0;             //miles*10

    final static int X = 30;  // 30

    public SpeedometerPanel() {
        super();
        setPreferredSize(new Dimension(260,260));
        //setSize(260,260);
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
	        //offgraphics.setFont(new Font("Helvetica",Font.BOLD,14));
	    }
        //offgraphics.setColor(Color.red);
        //offgraphics.fillRect(0, 0, getSize().width, getSize().height);
     }

    public void paint(Graphics g) {
         update(g);
    }

    public void update(Graphics g) {
        backdrop();
        drawSpeedometer(offgraphics,X+30,20);
        //g.drawImage(offscreen, 0, 0, null);
    }

    private void drawSpeedometer(Graphics g,int x, int y) {
       //speedometer
        g.setColor(Color.white);
        g.drawArc(x,y,165,165,0,360);
        for (int i=0;i<=120;i+=10)
            drawMark(g,x+83,y+83,83,i);
        g.setColor(Color.cyan);
        g.fillArc(x+2,y+2,163,163,-150,speed!=0?-(2*speed):-1);
        g.setColor(Color.red);
        g.fillArc(x+8,y+8,150,150,0,360);
        //odometer
        drawOdo(g,x+57,y+120,distance);
    }

    private void drawMark(Graphics g, int x, int y, int len, int n) {
        double flen = len;
        double fangle = ((60+n*2)*Math.PI)/180;
        int mx = x - (int)(flen*Math.sin(fangle));
        int my = y + (int)(flen*Math.cos(fangle));
        g.drawLine(x,y,mx,my);
        // display number
        flen = flen+12;
        mx = x- 7 - (int)(flen*Math.sin(fangle));
        my = y+7+ (int)(flen*Math.cos(fangle));
        g.drawString(String.valueOf(n),mx,my);
    }

    private void drawOdo(Graphics g, int x, int y, int distance) {
        String zero = "0";
        int digits[]= new int[4];
        for (int i=3;i>=0;i--) {
            digits[i]=distance%10;
            distance=distance/10;
        }
        g.setColor(Color.white);
        FontMetrics fm = g.getFontMetrics();
        int w = fm.stringWidth(zero);
        int h = fm.getHeight();
        for (int i=0;i<4;i++) {
            g.drawRect(x+(w+4)*i,y,w+4,h+2);
            if (i>1) g.setColor(Color.yellow);
            g.drawString(String.valueOf(digits[i]),x+(w+4)*i+3,y+h-2);
            g.setColor(Color.white);
        }
    }
}
