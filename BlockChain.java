package blockchain;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BlockChain {

  /**
   * Simple nodes for linked structures. Nodes are not encapsulated; we assume that the client
   * classes (in the same package) will use the fields as appropriate.
   */
  public static class Node<T> {

    T value;

    Node<T> next;

    public Node(T value, Node<T> next) {
      this.value = value;
      this.next = next;
    } // Node(T, Node<T>)
  } // class Node<T>

  Node<Block> first;
  Node<Block> last;

  public BlockChain(int initial) throws NoSuchAlgorithmException {

    // potential hash and potential nonce
    this.first = new Node<Block>(new Block(0, initial, (Hash) null), null);
  
    this.last = this.first;
  }// BlockChain(int amount)
  
  public Block mine(int amount) throws NoSuchAlgorithmException {
    Block next = new Block(this.last.value.getNum() + 1, amount, this.last.value.getHash());
    return next;
  }//Block mine(int amount)
  
  public int getSize() {
    return this.last.value.getNum() + 1;
    }//int getSize()
  
  public void append(Block blk) {
   
    Node<Block> newNode = new Node<Block>(blk, null);
    this.last.next = newNode;
    this.last = newNode;
    
    if(!this.isValidBlockChain()) {
        this.removeLast();
    }
    
  }//void append(Blockblk)
  
  public boolean removeLast() {
    if (this.first == this.last) {
      return false;
    }
    Node<Block> prevBlock = this.first;
    for (int i = 0;i < this.getSize() - 2 ;i++) {
      prevBlock = prevBlock.next;
    }
    this.last = prevBlock;
    this.last.next = null;
    if (this.last == this.first) {
      this.first.next = null;
    }
    return true;
  }
 
  public Hash getHash() {
    return this.last.value.getHash();
  }
 
  public boolean isValidBlockChain() {
   
      Node<Block> prev = this.first;
      Node<Block> cur = prev.next; 
    //  MessageDigest md = MessageDigest.getInstance("sha-256");
      int curAmount = prev.value.getAmount();
      for(int i = 1; i < this.getSize(); i++) {
          
          //check blocknums
          if (cur.value.getNum() != prev.value.getNum() +1) {
              System.out.println("Block numbering inconsistent");
              return false;
          } 
          
          //check hashes for consistency
         if (cur.value.getPrevHash() != prev.value.getHash()) {
              System.out.println("Hashes inconsistent");
              return false;
          }
          
          //check amount
          curAmount += cur.value.getAmount();
          if(curAmount < 0 || curAmount > this.first.value.getAmount()) {
              System.out.println("Insufficient funds for transaction");
              return false;
          }
          
          prev = cur;
          cur = cur.next;
         
      }//for
      return true;
  }//boolean isValidBlockChain()
  
  public void printBalances() {
    
    int aliceSum = 0;
    Node<Block> cur = this.first;
    
    for(int i = 0 ; i < this.getSize(); i++) {
      aliceSum += cur.value.getAmount();
      cur = cur.next;
    }
    System.out.println("Alice: " + aliceSum + ", Bob: " + (this.first.value.getAmount() - aliceSum));
  }
  
  @Override
  public String toString() {
    String str = "";
    Node<Block> cur = this.first;
    
    for(int i = 0; i < this.getSize(); i++) {
      str = str + cur.value.toString() + '\n';
      cur = cur.next;
    }
    return str;
  }
}//BlockChain class
     