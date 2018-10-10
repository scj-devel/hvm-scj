package carcontrol.externalsystem.cruisecontrol;

import java.awt.*;

import javax.swing.JPanel;

public class CarSimulator extends /*Canvas*/ JPanel implements Runnable, CarSpeed{


    volatile private boolean ignition = false;     //engine off
    volatile private double throttle = 0.0;        //throttle setting 0.10
    volatile private int speed = 0;                //speed 0 .. 120 mph!
    volatile private int distance = 0;             //miles*10
    volatile private int brakepedal = 0;           // brake setting 0..10
    volatile Thread engine=null;

    final static int X = 30;
    final static int botY = 200;
    final static int maxSpeed = 120;
    final static double maxThrottle = 10.0;
    final static int maxBrake = 10;
    final static double airResistance = maxSpeed/maxThrottle;  //inverse air resistance factor
    final static int ticksPerSecond = 5;

    public CarSimulator() {
        super();
        setSize(260,260);
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
	        offgraphics.setFont(new Font("Helvetica",Font.BOLD,14));
	    }
        offgraphics.setColor(Color.black);
        offgraphics.fillRect(0, 0, getSize().width, getSize().height);
     }

    public void paint(Graphics g) {
         update(g);
    }

    public void update(Graphics g) {
        backdrop();
        // display ignition
//        offgraphics.setColor(Color.white);
//        offgraphics.drawString("Ignition",X,botY+15);
//        if (ignition)
//           offgraphics.setColor(Color.green);
//        else
//           offgraphics.setColor(Color.red);
//        offgraphics.fillArc(X+60,botY,20,20,0,360);
//        //display throttle setting
//        drawControl(offgraphics,"Throttle",X+100,botY,(int)(throttle*5.0),Color.green);
//        //display brake pedal setting
//        drawControl(offgraphics,"Brake",X+100,botY+20,brakepedal*5,Color.red);
        //display speedometer
        drawSpeedometer(offgraphics,X+30,20);
        g.drawImage(offscreen, 0, 0, null);
    }

//    private void drawControl(Graphics g,String name, int x, int y, int setting,Color c) {
//        g.setColor(Color.white);
//        g.drawString(name,x,y+15);
//        g.drawRect(x+59,y+2,51,16);
//        g.setColor(c);
//        g.fillRect(x+60,y+3,setting,15);
//    }


    private void drawSpeedometer(Graphics g,int x, int y) {
       //speedometer
        g.setColor(Color.white);
        g.drawArc(x,y,165,165,0,360);
        for (int i=0;i<=120;i+=10)
            drawMark(g,x+83,y+83,83,i);
        g.setColor(Color.cyan);
        g.fillArc(x+2,y+2,163,163,-150,speed!=0?-(2*speed):-1);
        g.setColor(Color.black);
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


    public synchronized void  engineOn(){
        ignition = true;
        if (engine==null) {
            engine = new Thread(this);
            engine.start();
        }
        repaint();
    }

    public synchronized void  engineOff() {
        ignition = false;
        engine=null;
        repaint();
    }

    public synchronized void  accelerate() {
        if (brakepedal>0)
           brakepedal=0;
        else {
           if (throttle<(maxThrottle-1))
                throttle +=1.0;
           else
              throttle=maxThrottle;
        }
        repaint();
    }

    public synchronized void  brake() {
        if (throttle>0.0)
           throttle=0.0;
        else {
          if (brakepedal<maxBrake) brakepedal +=1;
        }
        repaint();
    }

    public void run() {
      try {
        double fdist=0.0;
        double fspeed=0.0;
		synchronized(this) {
            while (engine!=null) {
            wait(1000/ticksPerSecond);
                fspeed = fspeed+((throttle - fspeed/airResistance - 2*brakepedal))/ticksPerSecond;
                if (fspeed>maxSpeed) fspeed=maxSpeed;
                if (fspeed<0) fspeed=0;
                fdist = fdist + (fspeed/36.0)/ticksPerSecond;
                speed = (int)fspeed;
                distance=(int)fdist;
                if (throttle>0.0) throttle-=0.5/ticksPerSecond; //throttle decays
                repaint();
            }
        }
      } catch (InterruptedException e) {}
      speed=0; //no freewheeling!!
      distance=0;
      throttle=0;
      brakepedal=0;
      repaint();
    }

    // implementation of speed control interface

    public synchronized void setThrottle(double val) {
        throttle=val;
        if (throttle<0.0) throttle=0.0;
        if (throttle>10.0) throttle=10.0;
        brakepedal=0;
    }

    public synchronized int getSpeed() {
        return speed;
    }

}
