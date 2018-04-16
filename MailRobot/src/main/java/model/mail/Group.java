package model.mail;

import java.io.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Group {
    private static final Logger LOG = Logger.getLogger(Group.class.getName());

    Person sender;
    LinkedList<Person> receivers;

    public Group(Person sender, LinkedList<Person> receivers) {
        if(receivers.size() < 2){
            LOG.log(Level.SEVERE, "Wrong number of recivers");
            return;
        }

        this.sender = sender;
        this.receivers = receivers;
    }

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

    public Person Sender(){
        return sender;
    }

    public LinkedList<Person> getReceivers() {
        return receivers;
    }
}
