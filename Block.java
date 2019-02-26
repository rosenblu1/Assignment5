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


  public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
    this.num = num;
    this.amount = amount;
    this.prevHash = prevHash;

    // compute the hash

    MessageDigest md = MessageDigest.getInstance("sha-256");// instantiating the md object

    Hash potHash = null;
    long potNonce = 0;

    while (!potHash.isValid()) {
      byte[] numBytes = ByteBuffer.allocate(4).putInt(num).array(); // puts num into byte buffer
      byte[] amountBytes = ByteBuffer.allocate(4).putInt(amount).array(); // puts amount into byte
                                                                          // buffer
      byte[] nonceBytes = ByteBuffer.allocate(8).putLong(potNonce).array();

      md.update(numBytes);
      md.update(amountBytes);
      md.update(prevHash.getData());
      md.update(nonceBytes);

      potHash = new Hash(md.digest());
      potNonce++;
    }

    this.hash = potHash;
    this.nonce = potNonce - 1;
  }// Block(int num, int amount, Hash prevHash)


  public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
    this.num = num;
    this.amount = amount;
    this.prevHash = prevHash;
    this.nonce = nonce;
    // compute the hash

    MessageDigest md = MessageDigest.getInstance("sha-256");// instantiating the md object

    byte[] numBytes = ByteBuffer.allocate(4).putInt(num).array(); // puts num into byte buffer
    byte[] amountBytes = ByteBuffer.allocate(4).putInt(amount).array(); // puts amount into byte
                                                                        // buffer
    byte[] nonceBytes = ByteBuffer.allocate(8).putLong(nonce).array();

    md.update(numBytes);
    md.update(amountBytes);
    md.update(prevHash.getData());
    md.update(nonceBytes);

    this.hash = new Hash(md.digest());



  }// Block(int num, int amount, Hash prevHash, long nonce)

  public int getNum() {
    return this.num;
  }

  public int getAmount() {
    return this.amount;
  }

  public long getNonce() {
    return this.nonce;
  }

  public Hash getPrevHash() {
    return this.prevHash;
  }

  public Hash getHash() {
    return this.hash;
  }

  @Override
  public String toString() {
    return "Block " + this.num + " (Amount: " + this.amount + ", Nonce: " + this.nonce
        + ", prevHash: " + this.prevHash + ", hash: " + this.hash + ")";
  }

}// class Block

