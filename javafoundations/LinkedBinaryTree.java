//*******************************************************************
//  LinkedBinaryTree.java       Java Foundations
//
//  Implements a binary tree using a linked representation.
//*******************************************************************

package javafoundations;

import java.util.Iterator;
import javafoundations.*;
import javafoundations.exceptions.*;

public class LinkedBinaryTree<T> implements BinaryTree<T>
{
  protected BTNode<T> root;
  
  //-----------------------------------------------------------------
  //  Creates an empty binary tree.
  //-----------------------------------------------------------------
  public LinkedBinaryTree()
  {
    root = null;
  }
  
  //-----------------------------------------------------------------
  //  Creates a binary tree with the specified element as its root.
  //-----------------------------------------------------------------
  public LinkedBinaryTree (T element)
  {
    root = new BTNode<T>(element);
  }
  
  //-----------------------------------------------------------------
  //  Creates a binary tree with the two specified subtrees.
  //-----------------------------------------------------------------
  public LinkedBinaryTree (T element, LinkedBinaryTree<T> left,
                           LinkedBinaryTree<T> right)
  {
    root = new BTNode<T>(element);
    
//    this.root.getLeft() = left.root;
    root.setLeft(left.root);
    root.setRight(right.root);
//    this.root.getRight() = right.root;
  }
  
  //-----------------------------------------------------------------
  //  Returns the element stored in the root of the tree. Throws an
  //  EmptyCollectionException if the tree is empty.
  //-----------------------------------------------------------------
  public T getRootElement() {
    if (root == null)
      throw new EmptyCollectionException ("Get root operation "
                                            + "failed. The tree is empty.");
    
    return root.getElement();
  }
  
  //-----------------------------------------------------------------
  //  Returns the left subtree of the root of this tree.
  //-----------------------------------------------------------------
  public LinkedBinaryTree<T> getLeft() {
    if (root == null)
      throw new EmptyCollectionException ("Get left operation "
                                            + "failed. The tree is empty.");
    
    LinkedBinaryTree<T> result = new LinkedBinaryTree<T>();
    result.root = root.getLeft();
    
    return result;
  }
  
  //-----------------------------------------------------------------
  //  Returns the element in this binary tree that matches the
  //  specified target. Throws a ElementNotFoundException if the
  //  target is not found.
  //-----------------------------------------------------------------
  public T find (T target) {
    BTNode<T> node = null;
    
    if (root != null)
      node = root.find(target);
    
    if (node == null)
      throw new ElementNotFoundException("Find operation failed. "
                                           + "No such element in tree.");
    
    return node.getElement();
  }
  
  //-----------------------------------------------------------------
  //  Returns the number of elements in this binary tree.
  //-----------------------------------------------------------------
  public int size() {
    int result = 0;
    
    if (root != null)
      result = root.count();
    
    return result;
  }
  
  //-----------------------------------------------------------------
  //  Populates and returns an iterator containing the elements in
  //  this binary tree using an inorder traversal.
  //-----------------------------------------------------------------
  public Iterator<T> inorder() {
    ArrayIterator<T> iter = new ArrayIterator<T>();
    
    if (root != null)
      root.inorder (iter);
    
    return iter;
  }
  
  //-----------------------------------------------------------------
  //  Populates and returns an iterator containing the elements in
  //  this binary tree using a levelorder traversal.
  //-----------------------------------------------------------------
  public Iterator<T> levelorder() {
    LinkedQueue<BTNode<T>> queue = new LinkedQueue<BTNode<T>>();
    ArrayIterator<T> iter = new ArrayIterator<T>();
    
    if (root != null) {
      queue.enqueue(root);
      while (!queue.isEmpty()) {
        BTNode<T> current = queue.dequeue();
        
        iter.add (current.getElement());
        
        if (current.getLeft() != null)
          queue.enqueue(current.getLeft());
        if (current.getRight() != null)
          queue.enqueue(current.getRight());
      }
    }
    return iter;
  }
  
  //-----------------------------------------------------------------
  //  Satisfies the Iterable interface using an inorder traversal.
  //-----------------------------------------------------------------
  public Iterator<T> iterator() {
    return inorder();
  }
  
  //-----------------------------------------------------------------
  //  The following methods are left as programming projects.
  //-----------------------------------------------------------------
  
  public String toString() {
    return "hello";
    
  }
  
  public LinkedBinaryTree<T> getRight() {
    if (root == null)
      throw new EmptyCollectionException ("Get right operation "
                                            + "failed. The tree is empty.");
    
    LinkedBinaryTree<T> result = new LinkedBinaryTree<T>();
    result.root = root.getRight();
    
    return result;
  }
  
  
  public boolean contains (T target) {
    return true;
  }
  
  public boolean isEmpty() {
    return true;
  }
  
  public Iterator<T> preorder() {
    ArrayIterator<T> iter = new ArrayIterator<T>();
    
    if (root != null)
      root.preorder (iter);
    
    return iter;
  }
  
  public Iterator<T> postorder() {
    ArrayIterator<T> iter = new ArrayIterator<T>();
    
    if (root != null)
      root.postorder (iter);
    
    return iter;
    
  }
  
  public int height() {
    if (this.root.left == null && this.root.right == null){
      return 0;
    }
    
    int h1= 0; int h2=0;
    if(this.root.left!=null){
      h1 = this.root.left.height();
    }
    
    if(this.root.right!=null)
    { h2 = this.root.right.height();}
    
    return Math.max(h1,h2) + 1;
  }
  
  public void spin() {
    
  }
  public static void main (String[] args){
    

    //BTNode<String> one = new BTNode<String>("one");
//    BTNode<String> two = new BTNode<String>("two");
//   
//    BTNode<String> three = new BTNode<String>("three");
    
   
//    BTNode<String> node = one;
    
    //ArrayIterator<String> it = new ArrayIterator<String>();
    
    String one = "one";
    LinkedBinaryTree<String> two = new LinkedBinaryTree("two");
    LinkedBinaryTree<String> three = new LinkedBinaryTree("three");
    LinkedBinaryTree<String> n = new LinkedBinaryTree<String>(one, two, three);
    
    Iterator<String> it = n.postorder();
    while(it.hasNext()){
      System.out.println(it.next()); 
    }
    
    Iterator<String> it2 = n.preorder();
    while(it2.hasNext()){
      System.out.println(it.next()); 
    }
    
    
  }
}

