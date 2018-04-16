/**
 * MailRobot.java
 * @authors James Smith and Jérémie Chatillon
 */

import model.prank.PrankGenerator;

public class MailRobot {

    public static void main(String[] args){
        // Create a prank generator
        PrankGenerator prankGenerator = new PrankGenerator();

        // Generate a prank
        prankGenerator.generatePrank();

        // run the generate prank
        prankGenerator.runPrank();
    }
}
