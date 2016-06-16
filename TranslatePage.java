/**
 * TranslatePage.java
 * 
 * Compares English to Spanish translation by using hashtables. 
 * Takes as input a filename corresponding to a hashtable in the form of a .txt file. 
 * Reads from file to generate a hashtable, where the first word
 * in each line is the English word to be translated. The rest of the words
 * correspond to an array of possible Spanish translations, which will be stored as
 * the value in the Hashtable.
 * 
 * Can check if a user's input is the correct translation of a word. 
 * 
 * @author Mridula Peddada
 * Modified Date: 5-10-2016
 * 
 */

import java.io.*;
import java.util.*;

public class TranslatePage{
  
  //hashtable with english word being the key and the value being an array of 3 options
  private Hashtable<String,String[]> translations;
  private LinkedList<String> keys; //stores all English words
  
  
  /**
   * Constructor takes a filename to call a method that 
   * reads from file, and initializes instance variables.
   * 
   * @author Mridula Peddada
   * 
   * @param String .txt filename containining elements of hashtable
   */
  public TranslatePage(String filename){
    
    //initialize instance variables
    translations = new Hashtable<String,String[]>();
    keys = new LinkedList<String>();
    
    //read from file to generate hashtable and arrays
    this.readFile(filename);
  }
  
  
   /**
   * Reads from a .txt file to generate the keys and values of the hashtable.
   * Each key is the first word in each line of the file and the following words in 
   * the line are stored in an array as possible Spanish translations.
   * 
   * @author Mridula Peddada
   * 
   * @param String .txt filename containining elements of hashtable
   */
  public void readFile(String filename){
    try{
      
      Scanner scan = new Scanner (new File(filename)); //open Scanner
      
      while(scan.hasNext()){ //check all lines of the .txt file
        
        String[] values = new String[4]; //craete array of possible Spanish translations, where 0th index is default "Select: "
        String line = scan.nextLine(); //read line of text
        String[] table = line.split(" "); //split words by space
        
        for (int i = 1; i < table.length; i++){
          values[i-1] = table[i]; //add the three Spanish words to values
        }
        
        translations.put(table[0],values); //add the English word and spanish word array to hashtable
        keys.add(table[0]); //add English word to linkedList of English words
      }
      
      scan.close();
    }
    
    catch(IOException e){
      System.out.println(e); 
    }
  }
  
  
  /**
   * Takes in the user's choice as input, and checks the hashtable for the correct Spanish translation,
   * where the 1st index in the corresponding array is the correct answer. 
   * 
   * @author Mridula Peddada
   * 
   * @param String english word to check translation of
   * @param String user input of Spanish translation
   * @return boolean whether user's answer was correct
   */
  public boolean checkTranslation(String english, String input){ //user input will be a value
    
    if(translations.containsKey(english)){ //check if the chosen translation is a key in the hashtable (not "Select:")
      
      String correctAnswer = translations.get(english)[1]; //1st index is always the correct translation
      
      if (correctAnswer.equals(input)){ //check if input and correct answer match
        return true; //returns true only if answer is correct
      } 
    }
    
    return false; //otherwise, return false
  }
  
  
  //getter methods
  
  /**
   * Getter method returns the hashtable of English keys and 
   * corresponding arrays of Spanish translation options.
   * 
   * @author Mridula Peddada
   * 
   * @return hashtable of English and Spanish words
   */
  public Hashtable<String,String[]> getHashtable(){
    return translations;
  }
  
  
  /**
   * Getter method returns the LinkedList of English words
   * which will be used in the translation exercise. 
   * 
   * @author Mridula Peddada
   * 
   * @return LinkedList of all English words being tested for
   */
  public LinkedList<String> getKeys(){
    return keys;
  }
  
  
  //setter methods
  
  /**
   * Setter method sets the hashtable of English keys and 
   * corresponding array of Spanish translation options.
   * 
   * @author Harshita Yerramreddy
   * 
   * @param hashtable of english/spanish values
   * @return void
   */
  public void setHashtable(Hashtable<String,String[]> hash){
    this.translations = hash;
  }
  
  
  /**
   * Setter method sets the LinkedList of English words to translate.
   * 
   * @author Harshita Yerramreddy
   * 
   * @param LinkedList of English words to translate
   * @return void
   */
  public void setKeys(LinkedList<String> keys){
    this.keys = keys;
  }
}