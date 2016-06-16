/** 
 * endPanel.java
 * 
 * the endPanel class extends from the JPanel. It has three instance variable of type 
 * ImageIcon, Color, JTextArea and JPanel. This class contains a text area which lists
 * all the words that the user needs to improve on.The constructor takes no parameters
 * and sets the background size and color of the panel. It also initializes
 * a Scrollable panel.
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
import java.net.*;

public class endPanel extends JPanel{
  
  //instance variables
  private ImageIcon endImg; //for the image that appears in the end panel 
  private Color brightred, lightyellow,lightblue;
  private JTextArea textArea; //text area that list the elements in the priority queue (IncorrectWords)
  private JPanel link; //panel containing the final message
  private IncorrectWords incWords; //IncorrectWords object containing PriorityQueue of mispelled words
  
  
  /* The constructor endPanel takes no parameters. It initializes the colors to be used in
   * the panel. It also initializes the endPanel of type JPanel with a Border layout.
   * Another panel is initialized, which contains the message "Congrats!" The constructo also
   * initializes a JscrollPane which will contain the words the user needs to improve on.
   * The different panels and JScrollPane are then added to the endPanel.
   * 
   * @author Harshita Yerramreddy
   */
  public endPanel(IncorrectWords words){
    
    this.incWords = words; //set instance variable for incWords
    
    //create Color objects
    Color lightblue = new Color(196,232,232);
    Color brightred = new Color(231,56,40);
    Color lightyellow = new Color(255,251,190); 
    
    //create JPanel
    JPanel endPanel = new JPanel(new BorderLayout(10,10)); 
    
    //add background image
    endImg = new ImageIcon("endPic.png");
    
    //initialize a JLabel with the image
    JLabel end = new JLabel(endImg);
    
    //initialize the panel which will contain image.
    JPanel picture = new JPanel();
    picture.add(end);
    picture.setBackground(brightred);
    
    //a textArea is initialized with a scrollable pane
    textArea = new JTextArea(5, 20);
    System.out.println(incWords);
    JScrollPane scrollPane = new JScrollPane(textArea); 
    textArea.setEditable(false);
    
    //panel with list of words to practice spelling
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
    
    //'Update' JButton to refresh list of words to practice
    JButton updateButton = new JButton("Update List of Words to Practice!");
    updateButton.addActionListener(new updateListListener()); //add action listener
    JLabel updateLabel = new JLabel();
    updateLabel.add(updateButton); //add button to label
    updateButton.setAlignmentX(updateLabel.CENTER_ALIGNMENT); //center JButton on panel
    
    //create ScrollPane to acccomodate long list of words
    JScrollPane areaScrollPane = new JScrollPane(textArea); //add JTextArea to ScrollPane
    
    //add vertical scroll bar
    areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    areaScrollPane.setPreferredSize(new Dimension(250, 250));
    
    //add update button and scrollable pane to panel
    infoPanel.add(updateButton);
    infoPanel.add(areaScrollPane);
    
    //add a panel with instructions to study word spellings  
    JPanel practiceList = new JPanel();
    JLabel practiceLabel = new JLabel ("Click the 'Update' button to view a list of words you can practice spelling. Focus on the words at the top of the list!");
    practiceList.setBackground(lightblue);
    practiceList.add(practiceLabel);
    
    //add a panel with final comments/thank you message
    JPanel link = new JPanel();
    JLabel linkLabel = new JLabel ("Thanks for reading our preview of One Fish, Two Fish by Dr. Seuss! \nGo to your local library to read the whole book!");
    linkLabel.setFont(new Font("HelveticaNeue", Font.BOLD, 16));
    link.setBackground(lightblue);
    link.add(linkLabel);
    
    //add the picture, message panel, the scroll pane, and the final comments pane to endPanel using BorderLayout
    endPanel.add(practiceList, BorderLayout.NORTH);
    endPanel.add(picture, BorderLayout.WEST);
    endPanel.add(infoPanel, BorderLayout.EAST);
    endPanel.add(link,BorderLayout.SOUTH);
    
    //endPanel is added the main frame.
    endPanel.setBackground(lightblue);
    this.add(endPanel);
    this.setBackground(lightblue);
  }
  
  
  /**
   * updateListListener is a basic action listener that sets the text in the textArea JTextArea
   * to the String that is returned in the toString method of IncorrectWords. An action listener
   * is required to refresh the text area, since when the program is initially run, 
   * the IncorrectWords object has an empty PriorityQueue, and the JTextArea does not dynamically update.
   * 
   * @author Harshita Yerramreddy
   */
  public class updateListListener implements ActionListener {
    
    /** actionPerformed method called whenever an ActionEvent occurs (button press).*/
    public void actionPerformed(ActionEvent event) {
      textArea.setText(incWords.toString());
    }
  }
}