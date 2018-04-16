/**
 * Mail.java
 * @authors James Smith and Jérémie Chatillon
 */
package model.mail;

import java.util.LinkedList;

public class Mail {
    // ATTRIBUTS OF A MAIL
    Person from;
    LinkedList<Person> to;
    LinkedList<Person> cc;
    String message;

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
        this.message = message;
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
    public String getMessage(){return message;}
}
