/**
 * Prank.java
 * @authors James Smith and Jérémie Chatillon
 */
package model.prank;

import client.SMTPClient;
import model.mail.Mail;


public class Prank {
    // ATTRIBUTS
    private Mail[] mails;
    private SMTPClient smtp;

    /**
     * Constructor of a prank
     * @param smtp - the server smtp
     * @param mails - the mails to send
     */
    public Prank(SMTPClient smtp, Mail ... mails){
        this.mails = mails;
        this.smtp = smtp;
    }

    /**
     * run the prank - send the mails
     */
    public void run(){
        smtp.sendMail(mails);
    }
}
