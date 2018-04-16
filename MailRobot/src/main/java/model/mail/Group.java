/**
 * Group.java
 * @authors James Smith and Jérémie Chatillon
 */
package model.mail;

import java.io.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Group {
    private static final Logger LOG = Logger.getLogger(Group.class.getName());

    // ATTRIBUTS
    Person sender;
    LinkedList<Person> receivers;

    /**
     * Constructeur by attributs
     * @param sender - the sender Person of the mail
     * @param receivers the receivers of the mail
     */
    public Group(Person sender, LinkedList<Person> receivers) {
        if(receivers.size() < 2){
            LOG.log(Level.SEVERE, "Wrong number of recivers");
            return;
        }

        this.sender = sender;
        this.receivers = receivers;
    }

    /**
     * Constructeur by a file
     * @param file -file contain the groups
     * @remarks not used in this project
     */
    public Group(String file) {
        try {
            String s;
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            sender = new Person(in.readLine());
            while((s = in.readLine()) != null){
                receivers.add(new Person(s));
            }
        } catch (IOException e){
            LOG.log(Level.SEVERE, "Can't read file : " + file);
        }

    }

    /**
     * Return the sender of the group
     * @return Person - the sender of the email
     */
    public Person Sender(){
        return sender;
    }

    /**
     * Return the recievers of the email
     * @return List of Person
     */
    public LinkedList<Person> getReceivers() {
        return receivers;
    }
}
