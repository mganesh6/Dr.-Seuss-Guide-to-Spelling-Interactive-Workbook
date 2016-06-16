/**
 * pageTwo.java
 * 
 * The pageTwo class extends JPanel and has instance variables of type JButton,JComboBox
 * JLabel, Color, TranslatePage, menuPanel and String (for the keys and the filenames). 
 * It has a contructor which takes two inputs, a filename (String) and a menuPanel
 * which is useful when user wants to go to the next or the previous page. The constructor adds 
 * the background images, reads from a file to get all the information about what image
 * to use and the contents of the hashtable that is used to check if the translations the 
 * user inputs are correct. 
 * 
 * The class also contains three helper methods readfile(filename)
 * randomize(LinkedList<String>) and playSound(String soundFile) which is used in the actionlistener.
 * It has action listeners changePageListener(to go to the next or previous page), 
 * ComboListener(for the combo boxes) and HelpListener(for the help button which gives 
 * helpful instructions.
 * 
 * @author Mridula Peddada, Mathangi Ganesh
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

public class pageTwo extends JPanel{
  
  private JButton select, select2, select3, sound1, sound2, sound3, next,prev, help;
  private JComboBox word1, word2, word3;
  private JLabel ans1Label,ans2Label,ans3Label;
  private String key1, key2, key3,imgSource,translatefile;
  private Color brightred, lightyellow, lightblue;
  private TranslatePage translate;
  private menuPanel mainmenu;
  
  /* the pageTwo Constructor will take three instance variables a String with the filename 
   * containing the text file with the png files, indices of words in the text, spelling filename,
   * translate filename and the sound files. It initializes the colors and reads from the file. 
   * The constructor adds the background image for the translate mode page and has three 
   * combo boxes with each containing three spanish words (as options) associated with the 
   * english word. The page also contains a previous button ( which goes to the previous page)
   * and a next button(which goes to the next page). The "help" button is also added ( with 
   * an image added to the button) which gives a pop-up message when it is clicked. This constructor
   * initializes variables of type JColor,JButton,JComboBox,JLabel, TranslatePage, menuPanel 
   * and String.
   * 
   * @author Mridula Peddada, Mathangi Ganesh
   */
  public pageTwo (String filename, menuPanel mainmenu){
    
    //initialize colors
    Color lightblue = new Color(196,232,232);
    Color brightred = new Color(231,56,40);
    Color lightyellow = new Color(255,251,190); 
    
    //reads from the file using the parameter filename
    readFile(filename);
    
    //set menuPanel object to input
    this.mainmenu = mainmenu;
    
    //initializes translate object
    translate = new TranslatePage(translatefile);
    
    //initializes a hashtable
    Hashtable<String,String[]> translations = translate.getHashtable();
    
    String[] values1,values2,values3; //arrays of possible Spanish obtions for each combo box
    
    //initializes keys of the hashtable using getKeys() method
    key1 = translate.getKeys().get(0); //each key in the hashtable is the English word to translate
    key2 = translate.getKeys().get(1);
    key3 = translate.getKeys().get(2);
    
    //initializes the values array correcponding to the appropriate key
    values1 = translations.get(key1); //each corresponding value is an array of possible spanish answers
    values2 = translations.get(key2);
    values3 = translations.get(key3);
    
    //Clones array to preserve values in hashtable
    String[] valuesClone1 = new String[values1.length];
    String[] valuesClone2 = new String[values2.length];
    String[] valuesClone3 = new String[values3.length];
    
    //makes a clone of every values array
    for(int i = 0; i<values1.length;i++){
      
      valuesClone1[i] = values1[i];
      valuesClone2[i] = values2[i];
      valuesClone3[i] = values3[i];
      
    }
    
    //initializes three LinkedLists of Strings
    LinkedList<String> rand1 = new LinkedList<String>();
    LinkedList<String> rand2 = new LinkedList<String>();
    LinkedList<String> rand3 = new LinkedList<String>();
    
    //adds only the values from the textfile ( without including the "Select:")
    for(int i = 1; i < valuesClone1.length; i++){
      
      rand1.add(valuesClone1[i]);
      rand2.add(valuesClone2[i]);
      rand3.add(valuesClone3[i]);
      
    }
    
    //initializes a String array of choices which contains values in random order
    String [] choices = randomize(rand1);
    String [] choices2 = randomize(rand2);
    String [] choices3 = randomize(rand3);
    
    //adds the randomly ordered values to the valuesClone. 
    //this is done in order to have the options in the combo boxes in a random order
    for (int i = 0; i< choices.length; i++){
      
      valuesClone1[i+1] = choices[i];
      valuesClone2[i+1] = choices2[i];
      valuesClone3[i+1] = choices3[i];
      
    }
    
    //initializes a JPanel and sets the layout to BorderLayout
    JPanel page = new JPanel(); 
    page.setLayout(new BorderLayout(10,10));
    page.setBackground(lightyellow);
    
    //initializes the image for the background
    //imgSource is a String that is taken from the file
    //JLabel is used to give the background image a title
    ImageIcon bg = new ImageIcon(imgSource);
    JLabel image = new JLabel(bg);    
    JPanel picture = new JPanel();
    image.setBorder(new MatteBorder(5, 5, 5, 5, brightred)); //format GUI elements
    picture.setBackground(lightblue);
    picture.add(image);
    page.add(picture, BorderLayout.CENTER);
    
    //directions panel contains a JLabel with the directions for the user
    JPanel directionsPanel  = new JPanel();
    JLabel directions = new JLabel("Directions: Choose the correct Spanish word " +
                                   "that replaces each English word!");
    directions.setFont(new Font("HelveticaNeue", Font.BOLD, 18));
    directionsPanel.add(directions);
    page.add(directionsPanel,BorderLayout.NORTH);
    
    //JPanel questions will contain the combo boxes and excercises for the user
    JPanel questions = new JPanel();
    
    //the previous button is initialized and an actionlistener is added to it
    prev = new JButton();
    prev.addActionListener(new changePageListener());
    ImageIcon prevIcon = new ImageIcon("left.png"); // load the image to an imageIcon
    Image prevPic = prevIcon.getImage(); // transform it to an Image
    Image newimg2 = prevPic.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way to 20x20
    prevIcon = new ImageIcon(newimg2);  // transform it back
    prev.setIcon(prevIcon); //add image to JButton
    
    //follows the same process as previousbutton
    next = new JButton();
    next.addActionListener(new changePageListener());
    ImageIcon nextIcon = new ImageIcon("next.png"); 
    Image nextPic = nextIcon.getImage(); 
    Image newimg3 = nextPic.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
    nextIcon = new ImageIcon(newimg3);
    next.setIcon(nextIcon);
    
    questions.add(prev);
    
    //initializes label for corresponding combo box
    JLabel word1Label = new JLabel("1: ");
    //makes combo box with the values ( in random order)
    word1 = new JComboBox(valuesClone1); 
    
    JLabel word2Label = new JLabel("2: ");
    word2 = new JComboBox(valuesClone2); //max. number of characters is 10
    
    JLabel word3Label = new JLabel("3: ");
    word3 = new JComboBox(valuesClone3); //max. number of characters is 10
    
    //listener for the combo box is initialized
    ComboListener listener = new ComboListener();
    
    //buttons for the user to select the answer 
    select = new JButton();
    select2 = new JButton();
    select3 = new JButton();
    
    //label for the select button
    JLabel selectLabel = new JLabel("Go!");
    JLabel select2Label = new JLabel("Go!");
    JLabel select3Label = new JLabel("Go!");
    
    //initilizes label which says if answer is correct or not
    ans1Label = new JLabel("");
    ans2Label = new JLabel("");
    ans3Label = new JLabel("");
    
    //adds label and listener to every button
    select.add(selectLabel);
    select.addActionListener(listener);
    
    select2.add(select2Label);
    select2.addActionListener(listener);
    
    select3.add(select3Label);
    select3.addActionListener(listener);
    
    //adds combo boxes, select buttons and ans labels to the panel
    questions.add(word1Label);
    questions.add(ans1Label);
    questions.add(word1);
    questions.add(select);
    
    questions.add(word2Label);
    questions.add(ans2Label);
    questions.add(word2);
    questions.add(select2);
    
    questions.add(word3Label);
    questions.add(ans3Label);
    questions.add(word3);
    questions.add(select3);
    
    //initializes a help button and adds listener to button
    //adds an image to the help button 
    help = new JButton();
    help.addActionListener(new helpListener());
    ImageIcon helpIcon = new ImageIcon("help.png"); // load the image to an imageIcon
    Image helpPic = helpIcon.getImage(); // transform it to an Image
    Image newimg4 = helpPic.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way to 20x20
    helpIcon = new ImageIcon(newimg4);  // transform it back
    help.setIcon(helpIcon); //add image to 
    
    //adds help button to the questions panel
    questions.add(help);
    
    questions.add(next);
    
    page.add(questions, BorderLayout.SOUTH ); 
    page.setBackground(lightblue);
    directionsPanel.setBackground(lightblue);
    questions.setBackground(lightblue);
    this.setBackground(lightblue);
    this.add(page);
    
  }
  
  
  /**
   * Helper method readFile method takes a String filename. It scans through every element in the 
   * file and assigns the background image(png) to the imgSource variable. Assigns 
   * translatefile( which is the input to the TranslatePage object) to the .txt element
   * in the file. Closes scanner. This method catches the IOException if the file is 
   * not found. This method does not return anything.
   * 
   * @Harshita Yerramreddy
   */
  private void readFile(String filename){
    
    try{
      //uses scanner to go through every element in the file
      Scanner scan = new Scanner (new File(filename)); //create Scanner object
      
      //goes to the third line and finds the png filename for the background
      scan.next();
      scan.next();
      imgSource = "bookPictures/" +scan.next(); //same image source with directory
      //goes to the ninth line and finds the .txt filename for the translate file
      scan.next();   
      scan.next(); 
      scan.next(); 
      scan.next();
      translatefile = scan.next(); //save .txt file with values for translation exercise
      
      scan.close(); //close Scanner
      
    }
    
    //catches the IOException
    catch(IOException e){
      System.out.println(e);
    }
  }
  
  
  /**
   * Randomize method takes LinkedList of strings as a parameter and 
   * returns an array of Strings.This method puts all the elements in 
   * the LinkedList in a random order ( randomzes them). It basically adds the elements
   * in random order to the array of Strings. It uses the Random class and .nextInt method
   * to get a random element from the list which is added to the String array.
   * 
   * @author Mridula Peddada, Mathangi Ganesh
   */
  public String [] randomize(LinkedList<String> choices){
    
    //initializes a new string array 
    String [] rand = new String[3];
    Random gen = new Random();
    
    int index = 0;
    int k;
    
    //as long as the linkedlist is not empty
    while(!choices.isEmpty()){
      //creates a random integer between zero and size (of linkedList)
      k = gen.nextInt(choices.size());
      //picks a random element from the linkedList (using random index)
      rand[index] = choices.get(k);    
      index++;
      //removes the element that was added to the array, from the linkedlist
      choices.remove(k);
    }
    
    return rand;
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
    
    public void actionPerformed (ActionEvent event){
      
      if (event.getSource() == next ){
        //calls the openNextTab() on mainmenu which is of type menuPanel
        // because the menuPanel contains the collection of pages
        mainmenu.openNextTab();
      } 
      else {
        mainmenu.openPrevTab(); 
      }
    }
  }
  
  
  /**
   * ActionListener for 'help' button displays a frame with JOptionPane to display more detailed
   * instructions on how to use the specific storybook mode. In this mode, the user will be instructed 
   * on how to fill in the text fields for each missing word, and how the settings button works.
   * 
   * @author Mridula Peddada
   */
  private class helpListener implements ActionListener{
    public void actionPerformed(ActionEvent event){
      //initializes a new JFrame with name "Help"
      JFrame frame = new JFrame("Help");
      //shows a message with all the instructions
      JOptionPane.showMessageDialog(frame, "Need Help?\n"+ "Press the next button to go to the next page"
                                      + "\nPress the previous button to go to the previous page"
                                      + "\nPress the Go! to choose your answer" 
                                      +"\nNavigate between storybook modes using the 'Reader','Spelling', and 'Translation' tabs." 
                                      +"\nNavigate between pages by clicking the tabs at the top of the page."
                                   );
    }
  }
  
  
  
  /**
   * ComboListener which implements ActionListener does two things. It calls the method
   * checkTranslation using the user input and the key the combobox is associated with.
   * Depending on whether or not the user input is right, it pays a sound and sets the 
   * JLabel ans1Label to either Correct or Try Again!
   * 
   * @author Mridula Peddada, Mathangi Ganesh
   */
  private class ComboListener implements ActionListener{
    
    public void actionPerformed(ActionEvent event){
      
      //if the Go! button is clicked
      if (event.getSource() == select){
        
        //casts the selected item to String and assigns it to a variable
        String ans1 = (String)word1.getSelectedItem();
        
        //if the translation is correct, it plays a Yay! sound by calling the 
        //playSound method and sets the ans1Label to Correct
        if (translate.checkTranslation(key1, ans1)){
          
          playSound("yay.wav");
          ans1Label.setText("Correct!");
          ans1Label.setForeground(Color.blue);
        } 
        
        else{
          
          //if the translation is incorrect, it plays an aww! sound by calling the 
          //playSound method and sets the ans1Label to Try Again!
          
          playSound("aww.wav");
          ans1Label.setText("Try Again!");
          word1.setSelectedIndex(0);
          ans1Label.setForeground(Color.red);
          
        }
      }
      
      //does the same for the rest of the combo boxes
      if ( event.getSource() == select2){
        
        String ans2 =  (String)word2.getSelectedItem();
        
        if (translate.checkTranslation(key2,ans2)){
          playSound("yay.wav");
          ans2Label.setText("Correct!");
          ans2Label.setForeground(Color.blue);
        }
        
        else{
          playSound("aww.wav");
          ans2Label.setText("Try Again!");
          word2.setSelectedIndex(0);
          ans2Label.setForeground(Color.red);
          
        }
      }
      
      if (event.getSource() == select3){
        
        String ans3 =  (String)word3.getSelectedItem();
        if (translate.checkTranslation(key3,ans3)){
          playSound("yay.wav");
          ans3Label.setText("Correct!");
          ans3Label.setForeground(Color.blue);
        }
        
        else{
          playSound("aww.wav");
          ans3Label.setText("Try Again!");
          word3.setSelectedIndex(0);
          ans3Label.setForeground(Color.red);
        }
      }                                                   
    }
  }
}