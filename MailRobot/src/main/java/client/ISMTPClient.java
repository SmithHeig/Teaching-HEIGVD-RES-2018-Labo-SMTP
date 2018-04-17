/**
 * Interface ISMTPClient.java
 * @authors James Smith and Jérémie Chatillon
 */
package client;

import model.mail.Mail;

/**
 * Interface ISMTPClient
 */
public interface ISMTPClient {
    // Function to Mails
    void sendMail(Mail ... mails);
}
