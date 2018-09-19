package carcontrol.externalsystem;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import carcontrol.externalsystem.cruisecontrol.CarSimulator;

public class Main2ExternalSystemGUI extends JFrame {  
  
  private WindowListener windowListener = new JxWindowListener();
  
  private class JxWindowListener extends WindowAdapter  //inner class
  {    
    public void windowClosing(WindowEvent e)
    {     
      System.exit (0);
    }  
  }  
  
  private class JxOutputPanel extends JPanel
  {
	  private JLabel label1 = new JLabel("Speed: ");
	  private JTextField textField = new JTextField ("0000", 3);
	  
	  JxOutputPanel() {
		  Font f = new Font("Arial",Font.BOLD,16);
		  
		  label1.setFont(f);
		  this.add(label1);		  
		 
		  textField.setFont(f);		  
		  textField.setHorizontalAlignment(JTextField.RIGHT);
		  textField.setCaretColor(getBackground()); 
		 
		  this.add(textField);		  
	  }
  }
  
  private class JxButtonPanel1 extends JPanel
  {	
    private JButton b1 = new JButton ("Stop");
    private JButton b2 = new JButton ("Start");
    private JButton b3 = new JButton ("Park");
    private JButton b4 = new JButton ("Reverse");
    
    private ActionListener buttonListener = new JxButtonListener();
    
    private class JxButtonListener implements ActionListener //inner class
    {
      public void actionPerformed (ActionEvent e)
      { 
        Object theButton = e.getSource ();
        if (theButton == b1)
         System.out.println ("Hello, Stop");
        else if (theButton == b2)
          System.out.println ("Hello, Start");
        else if (theButton == b3)
          System.out.println ("Hello, Park");
        else 
          System.out.println ("Hello, Reverse");
      }
    }
  
    public JxButtonPanel1 ()
    {
      this.add (b1);
      this.add (b2);
      this.add (b3);
      this.add (b4);
           
      b1.addActionListener (buttonListener);
      b2.addActionListener (buttonListener);
      b3.addActionListener (buttonListener);
      b4.addActionListener (buttonListener);
    } 
  }
  
  private class JxButtonPanel2 extends JPanel
  {	
	private JButton b5 = new JButton ("Neutral"); 
    private JButton b6 = new JButton ("Drive"); 
    private JButton b7 = new JButton ("Accelerate");
    private JButton b8 = new JButton ("Brake"); 
    
    private ActionListener buttonListener = new JxButtonListener();
    
    private class JxButtonListener implements ActionListener //inner class
    {
      public void actionPerformed (ActionEvent e)
      { 
        Object theButton = e.getSource ();
        if (theButton == b5)
            System.out.println ("Hello, Neutral");
        else if (theButton == b6)
          System.out.println ("Hello, Drive");
        else if (theButton == b7)
          System.out.println ("Hello, Accelerate");
        else
          System.out.println ("Hello, Brake");
      }
    }
  
    public JxButtonPanel2 ()
    {
      this.add (b5);
      this.add (b6);
      this.add (b7);
      this.add (b8);
     
      b5.addActionListener (buttonListener);
      b6.addActionListener (buttonListener);
      b7.addActionListener (buttonListener);
      b8.addActionListener (buttonListener);
    } 
  }

  public Main2ExternalSystemGUI ()
  { 
    this.setTitle("Remote Control");
    this.setSize (350,350);
    
    this.addWindowListener(windowListener);
    
    JPanel mainPanel = new JPanel( new BorderLayout());
    
    JPanel panel0 = new CarSimulator();
    mainPanel.add("Center", panel0);
    
    JPanel panel1 = new JPanel(new BorderLayout());
    
    JPanel panel11 = new JxOutputPanel ();
    panel1.add("North", panel11);
  
    JPanel panel12 = new JxButtonPanel1 ();
    panel1.add("Center", panel12);
    
    JPanel panel13 = new JxButtonPanel2 ();
    panel1.add("South", panel13);
    
    mainPanel.add("South", panel1);
    
    Container contentPane = getContentPane();
    contentPane.add (mainPanel); 
  }
	
  public static void main (String[] args)
  {
    JFrame f = new Main2ExternalSystemGUI();
    f.setVisible(true);
  }
	
}
