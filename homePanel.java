/**
 * homePanel.java
 * 
 * GUI Panel class for the cover page of application. 
 * Includes an image of the book's title. 
 * 
 * @author Harshita Yerramreddy
 * Modified Date: 5-10-2016
 */

import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import javax.swing.border.*;

public class homePanel extends JPanel{
  
  //instance variables
  private ImageIcon titleImg; 
  private Color brightred, lightyellow;
  private JPanel home, picture;
  private JLabel title;
  
  
  /**
   * Constructor creates a panel that contains a cover image.
   * User must click on the Page 1 text to begin reading the book. 
   */
  public homePanel(){
        
    //create Colors
    brightred = new Color(231,56,40);
    lightyellow = new Color(255,251,190); 
    
    //create panel to hold image
    home = new JPanel(); 
    
    //add background image
    titleImg = new ImageIcon("coverPage.png");
    title = new JLabel(titleImg); //add image to JLabel
    picture = new JPanel();
    picture.add(title); //add JLabel 
    picture.setBackground(brightred);
    
    //add picture panel to main panel
    home.add(picture);
    home.setBackground(lightyellow); //set background color of panel
    
    this.add(home); //add home to this homePanel object 
    this.setBackground(lightyellow); //set background color
    
  }
}