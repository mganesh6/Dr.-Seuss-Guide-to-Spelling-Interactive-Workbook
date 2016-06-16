/**
 * IncorrectWords.java
 * 
 * Holds a PriorityQueue of the misspelled words from
 * the spelling exercises of all of the pages. The 
 * misspelled words are dequeued from highest number 
 * of attempts to lowest. The collection is then 
 * displayed at the end of the interactive storybook.
 * 
 * 
 * @author Mathangi Ganesh
 * Modified Date: 5-10-2016
 */


import javafoundations.*;

public class IncorrectWords{
  
  private PriorityQueue<Word> incWords;
  
  
  /**
   * Constructor method.
   * 
   * Initializes the PriorityQueue of Word objects
   * 
   * @author Mathangi Ganesh
   * 
   */
  public IncorrectWords(){
    incWords = new PriorityQueue<Word>();
  }
  
  
  /**
   * Enqueues Word w to the PriorityQueue 
   * only if the word is not already in queue.
   * 
   * @param w Word to enqueue to PriorityQueue
   * @author Mathangi Ganesh
   */
  public void addIncorrectWord(Word w){
    
    int size = incWords.size();
    boolean add = true;
    LinkedQueue<Word> temp = new LinkedQueue<Word>();
    
    //fill temp queue
    for(int i = 0; i < size; i++){
      
      //dequeue elements from priority queue and enqueue into temp queue
      temp.enqueue(incWords.dequeue()); 
      
    }
    
    
    //Check to make sure that word does not already exist in queue.
    for(int i = 0; i < size; i++){
      
      Word w1 = temp.dequeue();
      incWords.enqueue(w1);
      
      //if word exists in the queue, add is false
      if(w.getWord().equals(w1.getWord())){
        add = false;
      }
      
    }
    
    //only adds if word is not in queue
    if(add){
      incWords.enqueue(w);
    }
    
  }
  
  
  /**
   * getter method returns PriorityQueue.
   * @return PriorityQueue<Word> PriorityQueue of misspelled words
   * @author Mathangi Ganesh
   */
  public PriorityQueue<Word> getQueue(){
    
    return incWords;
    
  }
  
  
  /**
   * Returns the String representation of the collection.
   * Traverses through the PriorityQueue to print out each Word.
   * 
   * @return String representation of PriorityQueue
   * @author Mathangi Ganesh
   */
  public String toString(){
    
    String s = "";
    
    //Temporary queue created
    LinkedQueue<Word> temp = new LinkedQueue<Word>();
    int size = incWords.size();
    
    
    //Temp queue filled
    for(int i = 0; i < size; i++){
      
      temp.enqueue(incWords.dequeue());
    }
    
    //Word objects added to String s
    for(int i = 0; i < size; i++){
      
      Word w1 = temp.dequeue();
      System.out.println(w1);
      //every element in the queue is added to s
      s += w1 + "\n";
      incWords.enqueue(w1);
      
    }
    
    return s;
  }
  
  
  /**
   * Basic testing. The main method tests the methods addIncorrectWord and toString()
   * 
   * @return void
   */
  public static void main(String [] args){
    
    IncorrectWords w = new IncorrectWords();
    
    w.addIncorrectWord(new Word("you", 6));
    w.addIncorrectWord(new Word("song", 8));
    w.addIncorrectWord(new Word("hello", 2));
    w.addIncorrectWord(new Word("hello", 3));
    
    System.out.println(w+ "\n");
    System.out.println(w ); 
  } 
}