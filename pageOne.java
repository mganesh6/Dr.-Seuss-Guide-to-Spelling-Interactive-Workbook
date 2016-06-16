/**
 * pageOne.java
 * 
 * This panel corresponds to the 'Spelling' mode within the game. 
 * Reads from a text file to dynamically generate a GUI panel with a unique background image and 
 * corresponding missing words and spelling exercises.
 * 
 * Shows an image of the storybook page with certain words covered up and labeled with numbers.
 * Below the image, the user can hear each missing word, and can attempt to spell that missing
 * word. If they spell the word incorrectly, a sound effect ('Aww') will be played,
 * and the user will be prompted to try again. Once they correctly spell the word, a
 * congratulatory sound effect ('Yay!') will be played, and their correct answer will
 * remain int the text field.
 * 
 * The user has a maximum number of attempts (10), after which the answers 
 * will be provided to them. The user can change the maximum number of
 * attempts to adjust the difficulty of the game. If a word is incorrectly
 * spelled more than twice, then it will be added to a PriorityQueue by
 * the SpellPage object. The PriorityQueue (within an object of IncorrectWords) 
 * contains Word objects, which corresponds to a String (the word) and an 
 * int (the number of attempts) and extends Comparable. At the end of the game, 
 * the user will be given a list of words to practice using the PriorityQueue, 
 * ordered by the number of attempts.
 * 
 * Like pageZero, the user also has a help button, and can navigate to the previous or 
 * next page using navigation buttons.
 * 
 * @author Harshita Yerramreddy, Mathangi Ganesh, Mridula Peddada
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
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class pageOne extends JPanel {
  
  //instance variables
  
  //Color objects
  private Color brightred, lightyellow, lightblue;
  private JTextField word1, word2, word3; //text fields
  private JButton sound1, sound2, sound3, prev, next, help, settings; //buttons
  private String imgSource, spellFile, word1Sound, word2Sound, word3Sound; //filenames
  private JLabel attempts, score;
  private int index1, index2, index3, word1attempts, word2attempts, word3attempts;
  private menuPanel mainmenu; //main GUI panel
  private SpellPage checker; //spell checker
  private IncorrectWords words; //stores incorrectly spelled words
  
 
  /**
   * Constructor method generates a JLabel with an image of the storybook page's contents,
   * and fill-in-the blank areas for three missing words to spell. Audio buttons allow the user
   * to hear each missing word. Previous and back buttons allow user to navigate between pages.
   * Help button gives more detailed instructions, and Settings button allows the user to change 
   * the difficulty of the game. 
   * 
   * @author Harshita Yerramreddy, Mathangi Ganesh, Mridula Peddada
   * 
   * @param String filename containing contents unique to the page (image file, spelling exercise file, etc)
   * @param mainmenu passes the mainPanel object, which allows user to switch between pages of the book
   * @param incWords object of IncorrectWords that contains PriorityQueue of incorrectly attempted words
   */
  public pageOne(String filename, menuPanel mainmenu, IncorrectWords incWords) {
    
    //Color objects for GUI style (taken from color scheme of book)
    Color lightblue = new Color(196, 232, 232); //create new color using RGB values
    Color brightred = new Color(231, 56, 40);
    Color lightyellow = new Color(255, 251, 190);
    
    this.mainmenu = mainmenu; //pass input menuPanel object
    this.words = incWords; //pass input IncorrectWords object
    readFile(filename); //call readFile on filename to generate contents of page (background image, etc)
    
    checker = new SpellPage(spellFile, words); //create object of SpellPage to check for correct spellings and increment incorrect attempts
    checker.setQuestions(index1, index2, index3); //tell SpellPage object which words in sentence to check for
    
    
    /* main JPanel to hold all elements*/
    JPanel page = new JPanel();
    page.setLayout(new BorderLayout(10, 10)); //use BorderLayout
    page.setBackground(lightyellow);
    
    
    /* storybook page picture*/
    JPanel picture = new JPanel();
    ImageIcon bg = new ImageIcon(imgSource); //get background image file
    JLabel image = new JLabel(bg); //add background image to JLabel
    image.setBorder(new MatteBorder(5, 5, 5, 5, brightred));
    picture.setBackground(lightblue);
    picture.add(image); //add image JLabel to JPanel
    page.add(picture, BorderLayout.CENTER); //put image in the center of the Panel
    
    
    /* directions for user */
    JPanel directionsPanel = new JPanel();
    JLabel directions = new JLabel("Directions: Click on the audio button before " +
                                   "each blank and type in the word you hear!");
    directions.setFont(new Font("HelveticaNeue", Font.BOLD, 18));
    directionsPanel.add(directions);
    page.add(directionsPanel, BorderLayout.NORTH); //add to the top of the panel (north)
    
    
    /* text fields and navigation buttons */
    JPanel questions = new JPanel(); 
    
    //JButtons for navigation and sound 
    
    //sound buttons play a recording of each missing word
    sound1 = new JButton(); //audio for first word
    sound1.addActionListener(new WordPlayer()); //add action listener to JButton to hear sound
    
    ImageIcon soundIcon = new ImageIcon("sound.png"); // load the image to an imageIcon
    Image soundPic = soundIcon.getImage(); // transform it to an Image
    Image newimg = soundPic.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way to 20x20
    soundIcon = new ImageIcon(newimg); // transform it back
    sound1.setIcon(soundIcon); //add image to button
    
    sound2 = new JButton(); //audio for second word
    sound2.addActionListener(new WordPlayer());
    sound2.setIcon(soundIcon); //add image to button
    
    sound3 = new JButton(); //audio for third word
    sound3.addActionListener(new WordPlayer());
    sound3.setIcon(soundIcon); //add image to button
    
    
    //help button gives user instruction on how to use this mode
    help = new JButton();
    help.addActionListener(new helpListener());
    
    //add an image to the JButton
    ImageIcon helpIcon = new ImageIcon("help.png"); // load the image to an imageIcon
    Image helpPic = helpIcon.getImage(); // transform it to an Image
    Image newimg4 = helpPic.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way to 20x20
    helpIcon = new ImageIcon(newimg4); // transform it back
    help.setIcon(helpIcon); //add image to button
    
    //settings button allows user to adjust the maximum number of attempts they are allowed for this page
    settings = new JButton();
    settings.addActionListener(new settingsListener());
    
    ImageIcon settingsIcon = new ImageIcon("settings.png"); // load the image to an imageIcon
    Image settingsPic = settingsIcon.getImage(); // transform it to an Image
    Image newimg5 = settingsPic.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way to 20x20
    settingsIcon = new ImageIcon(newimg5); // transform it back
    settings.setIcon(settingsIcon); //add image to button
    
    //previous button goes to previous page (tab) in menuPanel object
    prev = new JButton();
    prev.addActionListener(new changePageListener());
    
    ImageIcon prevIcon = new ImageIcon("left.png"); // load the image to an imageIcon
    Image prevPic = prevIcon.getImage(); // transform it to an Image
    Image newimg2 = prevPic.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way to 20x20
    prevIcon = new ImageIcon(newimg2); // transform it back
    prev.setIcon(prevIcon); //add image to button
    
    //next button goes to next page (tab) in menuPanel object
    next = new JButton();
    next.addActionListener(new changePageListener());
    
    ImageIcon nextIcon = new ImageIcon("next.png"); // load the image to an imageIcon
    Image nextPic = nextIcon.getImage(); // transform it to an Image
    Image newimg3 = nextPic.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way to 20x20
    nextIcon = new ImageIcon(newimg3); // transform it back
    next.setIcon(nextIcon); //add image to 
    
    
    /* JLabels and TextFields for each missing word */
    
    JLabel word1Label = new JLabel("1: ");
    word1 = new JTextField(10); //max. number of characters is 10
    word1.addActionListener(new SpellListener()); //add action listener to check for correct answe
    
    JLabel word2Label = new JLabel("2: ");
    word2 = new JTextField(10); //max. number of characters is 10
    word2.addActionListener(new SpellListener());
    
    JLabel word3Label = new JLabel("3: ");
    word3 = new JTextField(10); //max. number of characters is 10
    word3.addActionListener(new SpellListener());
    
    attempts = new JLabel("Attempts: 0"); //starting value for attempts and score is 0
    score = new JLabel("Score: 0");
    
    
    /* add all buttons and text fields in appropriate order to panel */
    questions.add(prev);
    
    questions.add(sound1);
    questions.add(word1Label);
    questions.add(word1);
    
    questions.add(sound2);
    questions.add(word2Label);
    questions.add(word2);
    
    questions.add(sound3);
    questions.add(word3Label);
    questions.add(word3);  
    
    questions.add(attempts);
    questions.add(score);
    questions.add(help);
    questions.add(settings);
    
    questions.add(next);
    
    //add panels to page and format
    page.add(questions, BorderLayout.SOUTH); //add panel with all buttons and text fields to bottom of the main page panel
    page.setBackground(lightblue);
    directionsPanel.setBackground(lightblue);
    questions.setBackground(lightblue);
    this.add(page); //add page panel to pageOne object
    this.setBackground(lightblue);
  }
  
  
  /**
   * Helper method reads from a file containing information relavent and unique to each page,
   * including a link to the background image file, a .txt file with the contents of the sentence
   * for the spell checker, sound files for each word, and integers corresponding to the indices of missing words.
   * Skips over irrelevant pieces of data (corresponding to the translation exercise or reader)
   * 
   * @author Harshita Yerramreddy
   * @return void
   * @param String filename of page's information (in format pageNumberInfo.txt)
   */
  private void readFile(String filename) {
    
    try {
      Scanner scan = new Scanner(new File(filename)); //create Scanner object
      
      scan.nextLine(); //skip first line (image for Reader tab)
      imgSource = "bookPictures/" + scan.nextLine(); //save image with directry
      scan.nextLine(); //skip line with image for translation tab
      spellFile = scan.nextLine(); //source file containing the contents of the sentence, with one word per line
      
      //indices corresponding to missing words in the sentence
      //using indices and indexing the whole sentence allows developers to easily adjust the missing words
      index1 = Integer.parseInt(scan.nextLine());
      index2 = Integer.parseInt(scan.nextLine());
      index3 = Integer.parseInt(scan.nextLine());
      
      //skip over translation file and sentence audio file
      scan.nextLine();
      scan.nextLine();
      
      //save .wav files for each missing words
      word1Sound = scan.nextLine();
      word2Sound = scan.nextLine();
      word3Sound = scan.nextLine();
      
      scan.close(); //close Scanner
    } 
    
    catch (IOException e) {
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
   * ActionListener for audio buttons plays the appropriate .wav audio file
   * corresponding to the missing word in the sentence. Calls playSound 
   * helper method to open each audio clip and play it. Uses instance variables 
   * initialized in readFile to find audio files and event sources.
   * 
   * @author Harshita Yerramreddy
   */
  private class WordPlayer implements ActionListener {
    
    /** actionPerformed method called whenever an ActionEvent occurs (button press).*/
    public void actionPerformed(ActionEvent event) {
      
      //play sound corresponding to appropriate button/word
      if (event.getSource() == sound1) {
        playSound(word1Sound); //call playSound helper method to play word audio
      } 
      
      else if (event.getSource() == sound2) {
        playSound(word2Sound);
      } 
      
      else if (event.getSource() == sound3) {
        playSound(word3Sound);
      } 
    }
  }
  
  
  /**
   * ActionListener for settings button displays a JOptionPane that allows the
   * user to adjust the maximum number of attempts they will be allowed for all JTextFields.
   * If the user exceeds the max number of attempts on that page, the answers will be provided for them. 
   * The maximum number of attempts should be adjusted before beginning the exercise. 
   * 
   * @author Harshita Yerramreddy
   */
  private class settingsListener implements ActionListener {
    
    /** actionPerformed method called whenever an ActionEvent occurs (button press).*/
    public void actionPerformed(ActionEvent event) {
      
      //create JOptionPane to ask user for desired maximum number of attempts
      String m = JOptionPane.showInputDialog("How many attempts would you like to be given? Enter a value greater than 3.");
      
      //continue only if 'Yes' was pressed and string was created
      if (m != null) {
        int maxAttempts = Integer.parseInt(m); //set variable to user input
        
        if (maxAttempts > 3){ //there must be a minimum of 3 attempts - one for each blank
          checker.setAttemptMax(Integer.parseInt(m)); //set attemptMax variable in SpellPage object to user input  
        }
        //if user input is less than 3, nothing is changed
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
  private class helpListener implements ActionListener {
    
    /** actionPerformed method called whenever an ActionEvent occurs (button press).*/
    public void actionPerformed(ActionEvent event) {
      
      //create JFrame with OptionPane containing instructions for storybook mode
      JFrame frame = new JFrame("Help");
      JOptionPane.showMessageDialog(frame, "Need Help? \nClick the " +
                                    "sound icon and type in the word that you hear!\n" +
                                    "Click Settings to change the number of attempts for" +
                                    " this page!\nPress the right arrow to go to the next page " + 
                                    "\nand the left arrow to go back." + 
                                    "\nTo change modes, click the tabs on top of the page. ");
    }
  }
  
  
  /**
   * ActionListener for each JTextField corresponding to missing words on the page.
   * First, SpellListener increments attempts for each time the JTextField is changed,
   * and the attempts JLabel is changed to display the incremented number of attempts.
   * Catches NullPointerException.
   * 
   * @author Harshita Yerramreddy
   * 
   */
  private class SpellListener implements ActionListener {
    
    /** actionPerformed method called whenever an ActionEvent occurs (button press).*/
    public void actionPerformed(ActionEvent event) {
      
      String ans; //corresponds to correct answer for JTextField
      
      try {
        
        System.out.println(words);
        
        if (checker.getAttempts() == checker.getAttemptMax()) { //if user reaches maximum number of attempts
          
          /*If the user reaches max attempts, then the addWord method will be called on all three words.
           Whichever words required more than two attempts will be added to the PriorityQueue of misspelled words.*/
          checker.addWord(1);
          checker.addWord(2);
          checker.addWord(3);
          
          //set text of attemptsJLabel and fill in each blank with the correct answer
          attempts.setText("You're out of attempts! Here are the answers!");
          attempts.setForeground(Color.blue); //set text color to blue
          
          word1.setText(checker.getWord(index1));
          word1.setEditable(false); //set editable to false so that user cannot change the answer
          word1.setForeground(Color.blue);
          
          word2.setText(checker.getWord(index2));
          word2.setForeground(Color.blue);
          word2.setEditable(false);
          
          word3.setText(checker.getWord(index3));
          word3.setForeground(Color.blue);
          word3.setEditable(false);
        } 
        
        else { //if maxAttempts has not yet been reached
          
          if (event.getSource() == word1) { //if the first text field was changed
            ans = word1.getText().toLowerCase(); //get inputted text from text field (convert to lower-case)
            
            //check if the answer is correct using SpellPage
            if (checker.checkSpelling(index1, ans)) { //if answer is correct
              playSound("yay.wav"); //play sound effect
              
              word1.setText(ans); //set user's correct answer to text field
              checker.addScore(); //increment score in SpellPage
              checker.addWord(1); //add word and corresponding attemptCount to IncorrectWords using SpellPage
              //note: a word is only added to IncorrectWords if it has been incorrectly attempted more than twice (>2)
              
              score.setText("Score: " + checker.getScore()); //increment score
              word1.setForeground(Color.green); //set color of text to green
              word1.setEditable(false); //make text field un-editable, so that correct answer remains
            } 
            
            else { //if user's answer is incorrect
              checker.addAttemptsPerWord(1); //call addAttemptsWord in SpellPage, which increments that word's attemptCount
              playSound("aww.wav"); //play sound effect
              word1.setText("Try again!"); //display message in text field
              word1.setForeground(Color.red); //change text color to red
            }
          } 
          
          //similar method as above, for second and third text fields
          
          else if (event.getSource() == word2) {
            ans = word2.getText().toLowerCase();
            
            if (checker.checkSpelling(index2, ans)) {
              checker.addScore();
              checker.addWord(2);
              score.setText("Score: " + checker.getScore());
              playSound("yay.wav");
              word2.setText(ans);
              word2.setForeground(Color.green);
              word2.setEditable(false);
            } 
            
            else {
              checker.addAttemptsPerWord(2);
              word2.setText("Try again!");
              playSound("aww.wav");
              word2.setForeground(Color.red);
            }
          } 
          
          else if (event.getSource() == word3) {            
            ans = word3.getText().toLowerCase();
            
            if (checker.checkSpelling(index3, ans)) {
              checker.addScore();
              checker.addWord(3);
              score.setText("Score: " + checker.getScore());
              playSound("yay.wav");
              word3.setText(ans);
              word3.setForeground(Color.green);
              word3.setEditable(false);
            } 
            
            else {
              checker.addAttemptsPerWord(3);
              word3.setText("Try again!");
              playSound("aww.wav");
              word3.setForeground(Color.red);
              
            }            
          }
          
          checker.addAttempt(); //increment attempt counter every time the user presses 'Enter' in the JTextField
          attempts.setText("Attempts: " + checker.getAttempts()); //change attempts JLabel to reflect number of attempts
          
        }
      } catch (NullPointerException ex) { //catch exception
        System.out.println(ex);
      }
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
    
    /** actionPerformed method called whenever an ActionEvent occurs (button press).*/
    public void actionPerformed(ActionEvent event) {
      
      if (event.getSource() == next) { //check if the next button was pressed
        mainmenu.openNextTab(); //call openNextTab() method to go to next page on menuPanel
      } 
      
      else { //if previous button was pressed
        mainmenu.openPrevTab(); //call openPrevTab() method to go to previous page on menuPanel
      }
    }
  }
}