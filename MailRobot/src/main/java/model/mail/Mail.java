/**
 * Mail.java
 * @authors James Smith and Jérémie Chatillon
 */
package model.mail;

import java.util.LinkedList;
import java.util.Scanner;

public class Mail {
    // ATTRIBUTS OF A MAIL
    private Person from;
    private LinkedList<Person> to;
    private LinkedList<Person> cc;
    private String content;
    private String subject;

    /**
     * Constructor
     * @param from - Person who send the mail
     * @param to - revievers of the mail
     * @param cc - persons on copy of the mail
     * @param message - the message to send
     */
    public Mail(Person from, LinkedList<Person> to, LinkedList<Person> cc, String message){
        this.from = from;
        this.to = to;
        this.cc = cc;
        setSubject(message);
        setContent(message);
    }

    /**
     * getter - return the Person who send the mail
     * @return
     */
    public Person getFrom() {
        return from;
    }

    /**
     * getter - return the list of person who will recived the mail
     * @return list of Person
     */
    public LinkedList<Person> getTo() {
        return to;
    }

    /**
     * getter - return the list of person who will be in Cc of the mail
     * @return list of Person
     */
    public LinkedList<Person> getCc() {
        return cc;
    }

    /**
     * return the message who will be send
     * @return String message
     */
    public String getContent(){return content;}

    public String getSubject(){return subject;}


    private void setSubject(String message){
        Scanner scanner = new Scanner(message);
        String firstLine = scanner.nextLine();
        int index = firstLine.indexOf(": ");
        subject = firstLine.substring(index + 2, firstLine.length());
        System.out.println(subject);
    }

    private void setContent(String message){
        content = message.substring(message.indexOf("\n") + 1, message.length());
    }
}
