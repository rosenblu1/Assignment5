package blockchain;

  public class Hash {

      private byte[] data;
      
      public Hash(byte[] data) {
          this.data = data;
      }//Hash(byte[] data)
      
      public byte[] getData() {
          return this.data;
      }//getData()
      
      public boolean isValid() {
          return (data[0] == 0 && data[1] == 0 && data[2] ==0);
      }//isValid()
      @Override
      public String toString() {
          String strHash = "";
          for (int i = 0; i < data.length; i++) {
          strHash = strHash + String.format("%02X", Byte.toUnsignedInt(data[i]));
          }//for
          return strHash;
              }//toString
      @Override
      public boolean equals(Object other) {
          if (other instanceof Hash) {
              Hash o = (Hash) other;
              if (this.data.equals(o.data)){
                  return true;
              }
          }
          return false;
      }//equals(Object other)
      

  }//class Hash

