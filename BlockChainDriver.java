package blockchain;

import java.util.Scanner;
import java.io.PrintWriter;

public class BlockChainDriver {

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      throw new Exception("Incorrect Number of Arguments");
    } else if (Integer.parseInt(args[0]) < 0) {
      throw new Exception("Please enter a positive integer");
    }
    PrintWriter pen = new PrintWriter(System.out, true);
    BlockChain chain = new BlockChain(Integer.parseInt(args[0]));

    Scanner in = new Scanner(System.in);
    boolean done = false;

    while (!done) {
      pen.println(chain);
      pen.println("Command?");
      String input = in.next();

      if (input.equals("mine")) {
        pen.println("Amount transferred?");
        int amount = Integer.parseInt(in.next()); 
        pen.println("amount = " + amount + ", nonce = " + chain.mine(amount).getNonce());
      } else if (input.equals("append")) {
        pen.println("Amount transferred?");         
        int amount = Integer.parseInt(in.next());
        pen.println("Nonce?");
        long nonce = Long.parseLong(in.next());
        chain.append(new Block((chain.last.value.getNum() + 1), amount, chain.last.value.getHash(), nonce));
      } else if (input.equals("remove")) {
          if(!chain.removeLast()) {
        	  pen.println("Cannot remove block from single-block chains");
          }
      } else if (input.equals("check")) {
        if (chain.isValidBlockChain()) {
          pen.println("chain is valid");
        } else {
          pen.println("chain is invalid");
        }
      } else if (input.equals("report")) {
        chain.printBalances();
      } else if (input.equals("help")) {

        pen.println("Valid commands:");
        pen.println("    mine: discovers the nonce for a given transaction");
        pen.println("    append: appends a new block onto the end of the chain");
        pen.println("    remove: removes the last block from the end of the chain");
        pen.println("    check: checks that the block chain is valid");
        pen.println("    report: reports the balances of Alice and Bob");
        pen.println("    help: prints this list of commands");
        pen.println("    quit: quits the program");
      } else if (input.equals("quit")) {
        done = true;
      } else {
        throw new Exception("command not found");
      }
    } // while

    in.close();
    pen.flush();
  }// main(String[] args)
}
