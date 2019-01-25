/******************************************************************************
 *  Compilation:  javac HelloWorld.java
 *  Execution:    java HelloWorld
 *
 *  Client/Server chooser for Drawsomething game.
 *
 ******************************************************************************/
import java.util.Scanner;
import java.lang.System;
public class main {
	/*
	 * PROJECT TODO:
	 * Server - Able to restart, Wipe image, Change whose turn is next, IP ban user,....
	 * 
	 * 
	 * Database - Stores Image bytes or image location? Usernames + Passwords, Turn order,
	 * Points, Words available, Words guess correctly, level, EXP..... 
	 * 
	 * 
	 * Client - Can guess an image that is shown that another user drew, provided by server
	 * Send image to server, requests for data. 3 Modes: Guess, Draw, Create.
	 * Guess: Guess Image randomly picked by server
	 * Draw: Draw image, send to server based on word chosen from 3 word choices from server
	 * Create: Create their own words, or track how many guessed/not guessed......
	 * 
	 * .....Denotes information incomplete, more may be added. We should each work on a different
	 * section to avoid crossing paths too badly/redoing work.
	 * ttretlertlel
	 */
    public static void main(String[] args) {
        Scanner input = new Scanner (System.in);
        System.out.println("Please pick a mode:\n[C]lient\n[S]erver");
        String mode = input.next(); // Get what the user types.
        boolean isClient = mode.toLowerCase() == "c" ? true : false;
        if(isClient) 
        	new Client();
        else
        	new Server();
    }

}
