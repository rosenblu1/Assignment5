package blockchain;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
 int num;
 int amount;
 Hash prevHash;
 long nonce;
 Hash hash;
 
 public static byte[] calculteHash(String msg) throws NoSuchAlgorithmException {
	 MessageDigest md = MessageDigest.getInstance("sha-256");//instantiating the md object
	 
	 
	 ByteBuffer.allocate(4).putInt(x) //puts int into byte buffer
	 ByteBuffer.allocate(4).puLong(x)
	 
	 ByteBuffer.allocate
	 
	 md.update(msg.getBytes()); //String into bytes, updating the md with it 
	 
	 
	 
	 byte[] hash = md.digest();//digesting and calculate the hash
	 return hash;
 }
 
 public Block(int num, int amount, Hash prevHash) {
	 this.num = num;
	 this.amount = amount;
	 this.prevHash = prevHash;
	 
	
		 
	 }
	 
 
}//class Block
