/**
 * SpellPage.java
 * 
 * Backend of the spelling exercise.
 * Checks if a word inputted by the user is the correct
 * answer to a spelling question. The user has
 * a maximum number of attempts for the page. If the user
 * gets a word wrong more than twice, the word is added to
 * a PriorityQueue (in the IncorrectWords class) and is
 * displayed at the end of the game. Methods include 
 * checkSpelling, getMaximumAttempts, and addWord.
 * 
 * 
 * @author Mathangi Ganesh, Mridula Peddada
 * Modified Date: 5-10-2016
 */

import java.io.*;
import java.util.*;

public class SpellPage {
  
  //instance variables
  
  private LinkedList<String> spell; //List of every words on page
  private int score, attemptCount,attemptMax,word1attempts, word2attempts,word3attempts;
  
  //Array of the three blanks user must fill in
  private String[] questions; 
  //Array holding the number of attempts per question
  private int[] attemptsPerWord;
  //Holds collection of incorrect words
  private IncorrectWords incWords;
  
  
  /**
   * Constructor method creates an object of SpellPage.
   * Catches the IOException if the file is not found
   * 
   * @author Mathangi Ganesh, Mridula Peddada
   * 
   * @param p file that contains every word on the page, separated by line
   * 
   * @param w collection that holds every word that has been misspelled more than twice
   */
  public SpellPage(String p, IncorrectWords w){
    
    //set default values
    attemptCount = 0;
    attemptMax = 10;
    
    //initialize arrays
    attemptsPerWord = new int[3];
    questions = new String[3];
    
    //pass IncorrectWords object
    incWords = w;
    
    try{
      
      spell = new LinkedList<String>(); //initialize linked list of properly-spelled wordss in sentence
      Scanner reader = new Scanner(new File(p));
      
      //Each word on the page is stored in a LinkedList
      while(reader.hasNext()){
        spell.add(reader.next().toLowerCase()); 
      }
    }
    
    catch(IOException ex){ //catch IO exception   
      System.out.println(ex);
    }
  }
  
  
  /**
   * Checks the user's spelling. 
   * @param x represents the index within the file
   * where the correct spelling is.
   * 
   * @author Mathangi Ganesh, Mridula Peddada
   * 
   * @param ans represents the user's answer
   * @return  returns true(boolean) if the user's answer is correct
   */
  public boolean checkSpelling(int x, String ans){
    
    /*Returns true if the user's spelling 
     * matches the correct spelling
     */
    if(ans.equals(spell.get(x))){
      return true;
    }
    return false;
    
  }
  
  
  /**
   * Attempt count is incremented by one
   * each time the user answers incorrectly
   * on the page (for any question)
   * 
   * @return void
   */
  public void addAttempt(){
    attemptCount++; 
  }
  
  
  /**
   * Increments the score. Method is called in the GUI when
   * the user answers a question correctly.
   */
  public void addScore(){
    score++;  
  }
  
  
  /**
   * Increments the attempts for the specified
   * question by 1.
   * 
   * @author Mathangi Ganesh
   * 
   * @param index refers to the question number. 
   * The number of attempts for this question will
   * be incremented by 1.
   * 
   */
  public void addAttemptsPerWord(int index){
    attemptsPerWord[index - 1]++;   
  }
  
  
  /**
   * If a word is misspelled more than twice,
   * it is added to the collection of misspelled words.
   * 
   * @author Mathangi Ganesh
   * 
   * @param index Refers to the question number
   * For example, if index is 1, the attemptsPerWord for 
   * question 1 will be checked before the word is added to the 
   * collection.
   */
  public void addWord(int index){
    
    if(attemptsPerWord[index-1] > 2){
      
      //addIncorrectWord method of the IncorrectWords class is called
      incWords.addIncorrectWord(new Word(questions[index-1], attemptsPerWord[index-1]));
      
    }
  }
  
  
  //getter methods
  
  /**
   * getter method returns to maximum number of attempts for page
   * 
   * @return maximum number of attempts for page
   */
  public int getAttemptMax(){
    return attemptMax; 
  }
  
  
  /**
   * getter method returns the number of attempts on page so far
   * 
   * @return number of attempts on page so far
   */
  public int getAttempts(){
    return attemptCount; 
  }
  
  
  /**
   * getter method returns the score
   * 
   * @return returns the score
   */
  public int getScore(){
    return score;
  }
  
  
  /**
   * getter method returns the word at a specified index, x
   * 
   * @param int index of word
   * @return returns the word at the specified index in the LinkedList
   */
  public String getWord(int x){ 
    return spell.get(x); 
  }
  
  
  /**
   * getter method returns linkedList of all words on page
   * 
   * @return returns the LinkedList holding all of the words on the page
   */
  public LinkedList<String> getSpellPage(){
    return spell; 
  }
  
  
  /**
   * Fills the array with the answers sto the threee fill-in-the-blank spelling questions
   * 
   * @author Mathangi Ganesh
   * 
   * @param q1, q1, q3 represents index of the answer in the text file
   */
  public void setQuestions(int q1, int q2, int q3){
    
    //Retrieves the words at the corresponding indices from the LinkedList
    
    questions[0] = spell.get(q1);
    questions[1] = spell.get(q2);
    questions[2] = spell.get(q3);
    
  }
  
  
  /**
   * setter method sets the maximum number of attempts allowed per page
   * 
   * @param attemptMax resets maximum attempts to input value
   */
  public void setAttemptMax(int attemptMax){
    this.attemptMax = attemptMax;
  }
  
  
  /**
   * setter method sets the score to s
   * 
   * @param s sets score to s 
   */
  public void setScore(int s){
    score = s;
  }
  
  
  /**
   * toString method returns a neatly-formatted string representation of the LinkedList and score
   * 
   * @return returns the String representation of the LinkedList and the score.
   */
  public String toString(){
    return spell.toString() + "\nScore: " + score;
  }
  
  
  /**
   * Basic testing method. The main method tests the methods setQuestions, 
   * addAttemptsPerWord(int) and the toString()
   */
  public static void main(String [] args){
    
    IncorrectWords w1 = new IncorrectWords();
    SpellPage s = new SpellPage("1_spell.txt", w1);
    
    s.setQuestions(1, 2, 3);
    System.out.println(s);
    
    s.addAttemptsPerWord(1);
    s.addAttemptsPerWord(1);
    s.addAttemptsPerWord(1);
    s.addAttemptsPerWord(2);
    s.addAttemptsPerWord(2);
    s.addAttemptsPerWord(2);
    s.addAttemptsPerWord(2);
    
    System.out.println(w1);
    
  }
}