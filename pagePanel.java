/**
 * pagePanel.java
 * 
 * GUI panel contains a JTabbedPane of the three storybook modes: reader, spelling practice, and translation practice. 
 * Creates an object of each page type (pageZero (reader), pageOne (spelling), and pageTwo (translate)).
 * 
 * @author Mridula Peddada
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

public class pagePanel extends JPanel{
  
  private JTabbedPane page;
  private Color brightred, lightyellow, lightblue;
  private IncorrectWords incWords;
  
  /**
   * Constructor builds a tabbed pane with three different page types. Takes as input
   * a filename, which is passed as an input to each page type. The file input contains all relevant
   * information to a page, including a link to its background image, data files for spell/translation practice,
   * audio files, etc. The menuPanel input is also passed to each page type, which allows the user to navigate between
   * pages (page one to page two) within the respective page panel. IncorrectWords is taken as an input and passed to the 
   * pageOne object, which uses it to add to a PriorityQueue of incorrectly spelled words.
   * 
   * @author Mridula Peddada
   * 
   * @param String filename containing page's information
   * @param menuPanel main GUI panel with tabs for each page
   * @param IncorrectWords object containing PriorityQueue of misspelled words
   */
  public pagePanel(String filename, menuPanel m, IncorrectWords words){
    
    //Gives this panel access to the IncorrectWords object
    this.incWords = words;
    
    //create Colors using RGB values
    Color lightblue = new Color(196,232,232);
    Color brightred = new Color(231,56,40);
    Color lightyellow = new Color(255,251,190); 
    
    setLayout(new BorderLayout(10,10)); //set layout to BorderLayout
    this.setPreferredSize(new Dimension(1050, 720)); //set size of main panel
    this.setBackground(lightyellow); //set background color
    
    page = new JTabbedPane(); //create new JTabbedPane
    
    //create objects of each page type
    pageZero reader = new pageZero(filename,m);
    
    //passes incWords to allow access to the PriorityQueue of mispelled words
    pageOne spellPage = new pageOne(filename, m, incWords); 
    pageTwo translatePage = new pageTwo(filename, m);
    
    //add tabs with each page type to pane
    page.addTab("Reader",reader);
    page.addTab("Spelling", spellPage);
    page.addTab("Translation",translatePage);
    
    //add page to this object
    this.add(page,BorderLayout.NORTH);
  }
}