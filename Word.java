/**
 * Word.java
 * 
 * The Word class implements Comparable. It has instance variables 
 * of type integer called attemptCount which keeps track of the number
 * of attempts and a word of type String. It contains a contructor which
 * takes a Strinf and integer as the parameters. The parameter is assigned
 * to the instance variable. The getAttemptCount() will return an integer 
 * or the number of attempts. The compareTo method compares two Word objects
 * based on the number of attempts. The toString() will return a string representation
 * of the Word class info. The main class is used to test the compareTo method.
 * 
 * @author Mathangi Ganesh
 * Modified Date: 5-10-2016
 */

import javafoundations.*;
import java.util.*;

public class Word implements Comparable<Word>{
  
  //instance variables
  
  private int attemptCount; // number of attempts
  private String word; //the user input
  
  
  /**
   * The constructor takes two parameters, a String and an integer. 
   * The instance variables are assigned to the parameters.
   * 
   * @author Mathangi Ganesh
   * @param String w word input
   * @param int a number of attempts for that word
   */
  public Word(String w, int a){
    word = w;
    attemptCount = a;
  }
  
  
  /** 
   * The compareTo method takes an object of type Word and returns an 
   * integer based on whether other is greater than, smaller than or equal
   * to another Word. It returns a negative number if other is less than 
   * this.It returns a positive number if other is greater than this and returns
   * 0 if other is equal to this.
   * 
   * @param Word to compare this Word to
   * @return int representing relationship between words, where a negative number means that
   *         this Word has less attempts, 0 means both Words have equal attempts, and a positive
   *         number means that this Word has more attempts than the parameter Word
   * @author Mathangi Ganesh
   */
  public int compareTo(Word other){
    
    return (this.getAttemptCount() - other.getAttemptCount());
  }
  
  
  //getter methods
  
  
  /**
   * getWord() getter method will return the word in this object
   * 
   * @return String word
   */
  public String getWord(){
    return word;
  }
  
  
  /**
   * getAttemptCount() getter method will return the number of attempts
   * 
   * @return int attempt count for word
   */
  public int getAttemptCount(){
    return attemptCount;
  }
  
  
  //setter methods
  
  
  /**
   * setWord() setter method will set the word variable to user input
   * 
   * @param String word
   * @return void
   */
  public void setWord(String w){
    word = w;
  }
  
  
  /**
   * setAttemptCount() setter method will set the number of attempts to the input
   * 
   * @param int attempt count
   * @return void
   */
  public void setAttemptCount(int a){
    attemptCount = a;
  }
  
  
  /**
   * the toString() method retunrs a neatly formatted String representation of the Word class. 
   * 
   * @return String representation of Word
   */
  public String toString(){
    return word;
  }
  
  
  /**
   * The main method creates two objects of type Word and tests the compareTo
   * method.
   * 
   * @return void
   */
  public static void main(String[] args){
    
    Word a = new Word("apple",2);
    Word b = new Word("band", 5);
    
    System.out.println(a.compareTo(b));
  }
}