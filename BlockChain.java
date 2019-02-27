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
    
    MessageDigest md = MessageDigest.getInstance("sha-256");// instantiating the md object
    //potential hash and potential nonce
    Hash potHash = null;
    long potNonce = 0;

    while (!potHash.isValid()) {
      byte[] numBytes = ByteBuffer.allocate(4).putInt(0).array(); // puts num into byte buffer
      byte[] amountBytes = ByteBuffer.allocate(4).putInt(initial).array(); // puts amount into byte
                                                                          // buffer
      byte[] nonceBytes = ByteBuffer.allocate(8).putLong(potNonce).array();

      md.update(numBytes);
      md.update(amountBytes);
      md.update(nonceBytes);

      potHash = new Hash(md.digest());
      potNonce++;
    }//while

    this.first.value.hash = potHash;
    this.first.value.nonce = potNonce - 1;
    this.first.next = null;
    this.last = this.first;  
  }//BlockChain(int amount)
  
  public Block mine(int amount) throws NoSuchAlgorithmException {
    Block next = new Block(this.last.value.getNum() + 1, amount, this.last.value.getHash());
    return next;
  }//Block mine(int amount)
  
  public int getSize() {
    return this.last.value.getNum() + 1;
    }//int getSize()
  
  public void append(Block blk) {
	/*
	 
    if ((this.last.value.getNum() != blk.getNum() - 1) || (this.last.value.getHash() != blk.getPrevHash()) || (curAmount <= 0) || (curAmount >= this.first.value.getAmount())) {
      throw new IllegalArgumentException("Invalid block");
    }
    */
   
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
    }//if
    Node<Block> prevBlock = this.first;
    for (int i = 0;i < this.getSize() - 1;i++) {
      prevBlock = prevBlock.next;
    }
    this.last = prevBlock;
    this.last.next = null;
    return true;
  }
 
  public Hash getHash() {
    return this.last.value.getHash();
  }
 
  public boolean isValidBlockChain() {
   
	  Node<Block> prev = this.first;
	  Node<Block> cur = prev.next;
	//  MessageDigest md = MessageDigest.getInstance("sha-256");
	  
	  for(int i = 1; i < this.getSize(); i++) {
		  
		  //check blocknums
		  if (cur.value.getNum() != prev.value.getNum() +1) {
			  System.out.println("Block numbering inconsistent");
			  return false;
		  } 
		  
		  //check hashes for validity and consistency
		  if (!prev.value.getHash().isValid()) {
			  System.out.println("Hash invalid");
			  return false;
		  } else if (cur.value.getPrevHash() != prev.value.getHash()) {
			  System.out.println("Hashes inconsistent");
			  return false;
		  }
		  
		  //check amount
		  int curAmount = cur.value.getAmount() + prev.value.getAmount();
		  if(curAmount <= 0 || curAmount >= this.first.value.getAmount()) {
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
    
    for(int i = 1; i < this.getSize(); i++) {
      aliceSum += cur.value.getAmount();
      cur = cur.next;
    }
    System.out.println("Alice: " + aliceSum + ", Bob: " + (this.first.value.getAmount() - aliceSum));
  }
  
  @Override
  public String toString() {
    String str = "";
    Node<Block> cur = this.first;
    
    for(int i = 1; i < this.getSize(); i++) {
      str = str + cur.value.toString() + '\n';
      cur = cur.next;
    }
    return str;
  }
}//BlockChain class
      md.update(amountBytes);
      md.update(nonceBytes);

      potHash = new Hash(md.digest());
      potNonce++;
    }

    this.first.value.hash = potHash;
    this.first.value.nonce = potNonce - 1;
    this.first.next = null;
    this.last = this.first;  
  }//BlockChain(int amount)
  
  public Block mine(int amount) throws NoSuchAlgorithmException {
    Block next = new Block(this.last.value.getNum() + 1, amount, this.last.value.getHash());
    return next;
  }//Block mine(int amount)
  
  public int getSize() {
    return this.last.value.getNum() + 1;
    }//int getSize()
  
  public void append(Block blk) throws IllegalArgumentException {
    if (this.last.value.getNum() != blk.getNum() - 1) {
      throw new IllegalArgumentException("Invalid block");
    }
    Node<Block> newNode = new Node<Block>(blk, null);
    this.last.next = newNode;
    this.last = newNode;
  }
  public boolean removeLast() {
    if (this.first == this.last) {
      return false;
    }
    Node<Block> prevBlock = this.first;
    for (int i = 0;i < this.getSize() - 1;i++) {
      prevBlock = prevBlock.next;
    }
    this.last = prevBlock;
    this.last.next = null;
    return true;
  }
  public Hash getHash() {
    return this.last.value.getHash();
  }
 
  /**
   * UNIMPLEMENTED
   * @return
   */
  public boolean isValidBlockChain() {
   return true;
  }
  
  public void printBalances() {
    
    int aliceSum = 0;
    Node<Block> cur = this.first;
    
    for(int i = 1; i < this.getSize(); i++) {
      aliceSum += cur.value.getAmount();
      cur = cur.next;
    }
    System.out.println("Alice: " + aliceSum + ", Bob: " + (this.first.value.getAmount() - aliceSum));
  }
  
  @Override
  public String toString() {
    String str = "";
    Node<Block> cur = this.first;
    
    for(int i = 1; i < this.getSize(); i++) {
      str = str + cur.value.toString();
      cur = cur.next;
    }
    return str;
  }
}//BlockChain class
