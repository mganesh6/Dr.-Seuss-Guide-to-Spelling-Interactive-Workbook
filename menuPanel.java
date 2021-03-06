/**
 * menuPanel.java
 * 
 * menuPanel class extends from JPanel. It has four instance variables of type Book,
 * JTabbedPane, Color and IncorrectWords. The constructor takes no inputs
 * and initializes an object of type IncorrectWords. It adds the 8 tabs containing the Home
 * panel, six other pagePanels containing the three modes (Reading mode, Spelling mode and
 * Translation mode) and the end panel containing a text area where all the words that the 
 * user needs practice with are listed. The class also has the methods openNextTab()
 * and openPrevTab() which go to the next page and previous page respectfully.
 * The main method basically creates a menuPanel object
 * and adds it to the frame (initialized in the main method)
 * 
 * @author Harshita Yerramreddy
 * 
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

public class menuPanel extends JPanel{
  
  //instance variables
  private JTabbedPane menu;
  private Color brightred, lightyellow, lightblue,lemon;
  private IncorrectWords incWords;
  
  
  /**
   * the menuPanel Constructor take no parameters and creates an object 
   * of type IncorrectWords (which contains the priority queue containing the words the 
   * user needs to improve on). It initializes the colors of the panel and sets the 
   * dimensions of the panel. It initializes a homePanel containing the home page
   * the pagePanel which contains the three modes (Reading, Spelling, Translations) and 
   * the endPanel. Adds the three types of panels to the menuPanel. 
   * 
   * @author Harshita Yerramreddy
   */
  public menuPanel(){
    
    incWords = new IncorrectWords();
    
    //initialize the Colors
    Color lightblue = new Color(196,232,232);
    Color brightred = new Color(231,56,40);
    Color lightyellow = new Color(255,251,190); 
    Color lemon = new Color(255,250,205);
    
    setLayout(new BorderLayout(10,10)); //set layout to BorderLayout
    this.setPreferredSize(new Dimension(1200, 750)); //set size of main panel
    
    //intializes the JTabbedPane named menu
    menu = new JTabbedPane();
    
    //initializes homePanel and sets the background color of the homePanel
    homePanel home = new homePanel();
 //   home.setBackground(powderblue);
    
    
    /*initializes all the pagePanels with different texts. The pagePanel
    takes three inputs: the name of the filename with all the information
    about the page, an input of type menuPanel, and IncorrectWords */
    
    pagePanel page1 = new pagePanel("pageOneInfo.txt", this, incWords);
    pagePanel page2 = new pagePanel("pageTwoInfo.txt",this, incWords);
    pagePanel page3 = new pagePanel("pageThreeInfo.txt",this, incWords);
    pagePanel page4 = new pagePanel("pageFourInfo.txt",this, incWords);
    pagePanel page5 = new pagePanel("pageFiveInfo.txt",this, incWords);
    pagePanel page6 = new pagePanel("pageSixInfo.txt",this, incWords);
    endPanel end = new endPanel(incWords);
    
    //adds the home panel, pagePanels and the end panel
    menu.addTab("Home", home);
    menu.addTab("Page 1",page1);
    menu.addTab("Page 2", page2);
    menu.addTab("Page 3",page3);
    menu.addTab("Page 4",page4);
    menu.addTab("Page 5",page5);
    menu.addTab("Page 6",page6);
    menu.addTab("End",end);
    
    this.add(menu,BorderLayout.NORTH);
    this.setBackground(lightblue);
    
  }
  
  
  /**
   * openNextTab() increments the index of the present tab and sets the
   * it to the tabbedpane. This will then switch to the next tab.
   * This method is called within the other pages to navigate the book.
   * 
   * @return void
   * @author Harshita Yerramreddy
   */
  public void openNextTab() {
    
    int selectedIndex = menu.getSelectedIndex(); //check which tab currently at
    selectedIndex++; //increment index of tab (next tab)
    selectedIndex %= menu.getTabCount(); //loop around from end to beginning tab if at last tab
    menu.setSelectedIndex(selectedIndex); //set tab to incremented tab (go to next page)
  }
  
  
  /**
   * openPrevTab() decrements the index of the present tab and sets the
   * it to the tabbedpane. This will then switch to the previous tab. 
   * This method is called within the other pages to navigate the book.
   * 
   * @return void
   * @author Harshita Yerramreddy
   */
  public void openPrevTab() {
    int selectedIndex = menu.getSelectedIndex(); //check which tab currently at
    selectedIndex--; //decrement index of tab
    selectedIndex %= menu.getTabCount(); //use modulo to loop around tabs
    menu.setSelectedIndex(selectedIndex); //set to decremented tab (go to previous page)
  }
  
  
  /**
   * The main method initializes a JFrame and a menuPanel. The menuPanel is added to 
   * the frame. The background color of the frame is also set. This is the main
   * frame that is viewed in the GUI, and thus this class must be compiled and run to use the program.
   * 
   * @return void
   * @author Harshita Yerramreddy
   */
  public static void main (String[] args) {
    // creates and shows a Frame 
    JFrame frame = new JFrame("Dr. Seuss Reader");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //create a panel, and add it to the frame
    menuPanel panel = new menuPanel();
    panel.setBackground(panel.lemon);
    
    frame.getContentPane().add(panel);
    
    frame.pack();
    frame.setVisible(true); //make frame visible
  }
}