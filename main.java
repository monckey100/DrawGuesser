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

    public static void main(String[] args) {
        // Prints "Hello, World" to the terminal window.
        Scanner input = new Scanner (System.in);
        System.out.println("Please pick a mode:\n[C]lient\n[S]erver");
        String mode = input.next(); // Get what the user types.
        if(mode.toLowerCase() == "c") {
          System.out.println("Running as Client..\n");
        } else {
          System.out.println("Running as Server..\n");
        }
    }

}
