/*
 * //updated: daniel.sykes 2013
 */

package carcontrol.externalsystem.cruisecontrol;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class CruiseControl extends Applet {

    CarSimulator car;
    CruiseDisplay disp;
    Controller control;
    Button engineOn;
    Button engineOff;
    Button accelerate;
    Button brake;
    Button on;
    Button off;
    Button resume;

    public  void init() {
        String fixed  = getParameter("fixed");
        boolean isfixed = fixed!=null?fixed.equals("TRUE"):false;
        setLayout(new BorderLayout());
        car = new CarSimulator();
        add("Center",car);
        //disp = new CruiseDisplay();
        //add("East",disp);
        //control = new Controller(car,disp,isfixed);
		
        engineOn = new Button("engineOn");
        engineOn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              car.engineOn();
              control.engineOn();
            }
         });
		
        engineOff = new Button("engineOff");
        engineOff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              car.engineOff();
              control.engineOff();
            }
         });
		
        accelerate = new Button("accelerate");
        accelerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              car.accelerate();
              control.accelerator();
             }
         });
		
        brake = new Button("brake");
        brake.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              car.brake();
              control.brake();
             }
         });
		
        on = new Button("on");
        on.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              control.on();
            }
         });
		
        off = new Button("off");
        off.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              control.off();
            }
         });
		
        resume = new Button("resume");
        resume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              control.resume();
            }
         });
		
        Panel p1 = new Panel();
        p1.setLayout(new FlowLayout());
        p1.add(engineOn);
        p1.add(engineOff);
        p1.add(accelerate);
        p1.add(brake);
        p1.add(on);
        p1.add(off);
        p1.add(resume);
        add("South",p1);
   }

    public void stop() {
        car.engineOff(); //kill engine thread
        control.engineOff();
    }

}
