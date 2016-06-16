/**
 * pageZero.java
 * 
 * GUI Panel class creates a JPanel that displays an image with the
 * page's images and text, and allows the user to hear an audio
 * recording of the sentence. Reads from a text file to access the
 * page's background image and audio file. Has next and previous
 * navigation buttons to access the previous or following page. 
 * This class contains a Constructor, readFile (helper method), WordPlayer actionlistener
 * changePageListener and HelpListener. 
 * 
 * @author Harshita Yerramreddy
 * Modified Date: 5-9-2016
 * 
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
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class pageZero extends JPanel{
  
  //Instance variables
  private JButton sound1, prev, next, help;
  private String imgSource, sentence_audio;
  private menuPanel mainmenu; 
  private Color brightred, lightyellow,lightblue;
  
  /**
   * Constructor method initializes pageZero object.
   * 
   * @author Harshita Yerramreddy
   * 
   * @param fileName name of file with info corresponding to reader
   * @param menuPanel passed to all panels so that "next" and "previous"
   *                  page buttons can function for every page
   */
  public pageZero (String filename, menuPanel mainmenu){
    
    //Color objects
    Color lightblue = new Color(196,232,232);
    Color brightred = new Color(231,56,40);
    Color lightyellow = new Color(255,251,190); 
    
    this.mainmenu = mainmenu;
    
    //Reads in file containing name of the audio file and background image.
    readFile(filename); 
    
    //create page panel
    JPanel page = new JPanel(); 
    page.setLayout(new BorderLayout(10,10));
    page.setBackground(lightyellow);
    
    //adds background image 
    ImageIcon bg = new ImageIcon(imgSource);
    JLabel image = new JLabel(bg);    
    JPanel picture = new JPanel();
    image.setBorder(new MatteBorder(5, 5, 5, 5, brightred));
    picture.setBackground(lightblue);
    
    picture.add(image); //add JLabel to JPanel
    page.add(picture, BorderLayout.CENTER); //add JPanel picture to page
    
    //add directions panel to top of panel
    JPanel directionsPanel  = new JPanel();
    JLabel directions = new JLabel("Directions: Click on the audio button to hear this page read out loud!");
    directions.setFont(new Font("HelveticaNeue", Font.BOLD, 18));
    directionsPanel.add(directions);
    page.add(directionsPanel,BorderLayout.NORTH);
    
    //panel that contains the help, next, prev and sound buttons
    JPanel navigation = new JPanel();
    
    /* Create JButtons with images for audio, previous, next, and help buttons */
    
    sound1 = new JButton(); //to play sentence
    sound1.addActionListener (new WordPlayer()); 
    ImageIcon soundIcon = new ImageIcon("soundwave.png"); // load the image to an imageIcon
    Image soundPic = soundIcon.getImage(); // transform it to an Image
    Image newimg = soundPic.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way to 20x20
    soundIcon = new ImageIcon(newimg);  // transform it back
    sound1.setIcon(soundIcon); //add image to button
    
    prev = new JButton(); //go to previous page
    prev.addActionListener(new changePageListener());
    ImageIcon prevIcon = new ImageIcon("left.png"); // load the image to an imageIcon
    Image prevPic = prevIcon.getImage(); // transform it to an Image
    Image newimg2 = prevPic.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way to 20x20
    prevIcon = new ImageIcon(newimg2);  // transform it back
    prev.setIcon(prevIcon); //add image to sound button
    
    next = new JButton(); //go to next page
    next.addActionListener(new changePageListener());
    ImageIcon nextIcon = new ImageIcon("next.png"); // load the image to an imageIcon
    Image nextPic = nextIcon.getImage(); // transform it to an Image
    Image newimg3 = nextPic.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way to 20x20
    nextIcon = new ImageIcon(newimg3);  // transform it back
    next.setIcon(nextIcon); //add image to sound button
    
    help = new JButton(); //help button
    help.addActionListener(new helpListener());
    ImageIcon helpIcon = new ImageIcon("help.png"); // load the image to an imageIcon
    Image helpPic = helpIcon.getImage(); // transform it to an Image
    Image newimg4 = helpPic.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way to 20x20
    helpIcon = new ImageIcon(newimg4);  // transform it back
    help.setIcon(helpIcon); //add image to button
    
    //add buttons to navigation panel
    navigation.add(prev); 
    navigation.add(sound1);
    navigation.add(help);
    navigation.add(next);
    
    //add navigation panel to page and format
    page.add(navigation, BorderLayout.SOUTH ); 
    page.setBackground(lightblue);
    directionsPanel.setBackground(lightblue);
    navigation.setBackground(lightblue);
    this.setBackground(lightblue);
    this.add(page);  
  }
  
  
  /**
   * Helper method reads from a file to retreivefile names
   * of the background image and audio files. This method catches 
   * the IOException if the file is not found. It does not return 
   * anything. When it is reading from the file, the first line 
   * of the file is the png which is then assigned to imgSource.
   * 
   * @author Harshita Yerramreddy
   * @param filename file that has the aforementioned file names 
   */
  private void readFile(String filename){
    
    try{ 
      Scanner scan = new Scanner (new File(filename)); //create Scanner object
      
      imgSource = "bookPictures/" +scan.nextLine(); //save background image with directory
      scan.nextLine(); //skip files corresponding to other storybook modes 
      scan.nextLine();
      scan.nextLine();
      scan.nextLine();
      scan.nextLine();
      scan.nextLine();
      scan.nextLine();
      sentence_audio = scan.nextLine(); //save audio recording of sentence
      
      scan.close(); //close Scanner
      
    }
    catch(IOException e){
      System.out.println(e);
    }
  }
  
  
  /**
   * Helper method takes an input of a .wav sound file, and creates an AudioInputStream
   * in a parallel process to play the audio of that word. Note that if the button is
   * pressed repeatedly, the audio clips will overlap and create an echo. For optimal
   * use, the button should only be pressed once, and the user should wait for the clip
   * to play fully before pressing the button again.
   * Catches any Exception.
   * 
   * @author Harshita Yerramreddy
   */
  private void playSound(String soundFile) {
    
    try {
      String soundName = soundFile; //create String corresponding to file name (to prevent changing original soundFile)
      
      //create AudioInputStream object and clip
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream); //open audio stream
      clip.start(); //play audio stream
    } 
    
    catch (Exception e) { //catch any exception
      System.err.println(e.getMessage());
    }
  }
  
  
  /**
   * WordPlayer ActionListener class
   * Plays audio file when sound1 button is clicked.
   * It calls the playSound method.
   * @author Harshita Yerramreddy
   */
  private class WordPlayer implements ActionListener{
  
    /**
     * actionPerformed method called whenever an ActionEvent occurs (button press)
     * @param event action of clicking the button
     */
    public void actionPerformed(ActionEvent event){
     
      playSound(sentence_audio); //play sentence audio
    } 
  }
  
  
  /**
   * ActionListener for navigation buttons. Calls the openNextTab() and openPrevTab()
   * methods in the menuPanel object to navigate between tabs in the JTabbedPane. Each
   * tab corresponds to a page, so the previous button will take the user to the previous
   * page, and the next button will take them to the next page. When the next button is clicked
   * on the end page, they will be led back to the beginning of the book.
   * 
   * Public access needed in order to switch from page to page.
   * When next or previous buttons clicked, this actionListener 
   * is called.
   * 
   * @author Harshita Yerramreddy
   */
  public class changePageListener implements ActionListener {
    
    /**
     * actionPerformed method called whenever an ActionEvent occurs (button press)
     * 
     * @param event the action of clicking the next or
     *              previous button is passed onto this method
     */
    public void actionPerformed (ActionEvent event){
      
      if (event.getSource() == next ){
        //next tab is opened using the openNextTab() method
        mainmenu.openNextTab();
      }
      
      else {
        mainmenu.openPrevTab(); //call openPrevTab() method in menuPanel
      }
    }
  }
  
  
  /**
   * ActionListener for help button displays a frame with JOptionPane to display more detailed
   * instructions on how to use the specific storybook mode. In this mode, the user will be instructed 
   * on how to fill in the text fields for each missing word, and how the settings button works.
   * 
   * @author Mridula Peddada
   */
  private class helpListener implements ActionListener{
    
    /**
     * actionPerformed method called whenever an ActionEvent occurs (button press)
     * 
     * @param event the action of clicking the help
     * button is passed on to this method
     */
    
    public void actionPerformed(ActionEvent event){
      
      JFrame frame = new JFrame("Help");
      
      //Help message displayed using a JOptionPane
      JOptionPane.showMessageDialog(frame, "Need Help? \n" +
                                    "Press the left arrow to go to the previous page.\n" +
                                    "Press the right arrow to go to the next page. \n" +
                                    "Press the sound wave to hear the word read to you. \n"+
                                    "Navigate between storybook modes using the 'Reader','Spelling', and 'Translation' tabs. \n" +
                                    "Navigate between pages by clicking the tabs at the top of the page.");
      
    }
  }
}