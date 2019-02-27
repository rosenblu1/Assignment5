package blockchain;

import java.util.Scanner;

public class BlockChainDriver {
	
	public static void main(String[] args) {
		if(args.length != 1) {
			throw new Exception("Incorrect Number of Arguments");
		} else if (Integer.parseInt(args[0]) < 0) {
			throw new Exception("Please enter a positive integer");
		}
		
		BlockChain chain = new BlockChain(Integer.parseInt(args[0]));
		
		
	}//main(String[] args)
	
	
	Scanner in = new Scanner(System.in);
	   boolean done = false;
	   int i = 0;
	    while (!done) {
	      pen.print("Please enter an integer: ");
	      pen.flush();
	      
	      if (!in.hasNextInt()) {
	        String tmp = in.next();
	        pen.println("'" + tmp + "' is not an integer.  Try again.");
	      } else {
	        i = in.nextInt();
	        done = true;
	      } // endif
	    } // while
}

