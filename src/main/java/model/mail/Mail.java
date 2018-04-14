package model.mail;

import java.util.LinkedList;

public class Mail {
    Person from;
    LinkedList<Person> to;
    LinkedList<Person> cc;
    String subject;

    public Mail(Person from, LinkedList<Person> to, LinkedList<Person> cc, String subject){
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.subject = subject;
    }

    public Person getFrom() {
        return from;
    }

    public LinkedList<Person> getTo() {
        return to;
    }

    public LinkedList<Person> getCc() {
        return cc;
    }

    public String getSubject() {
        return subject;
    }
}
