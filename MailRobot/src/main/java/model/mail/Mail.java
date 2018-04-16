package model.mail;

import java.util.LinkedList;

public class Mail {
    Person from;
    LinkedList<Person> to;
    LinkedList<Person> cc;
    String message;

    public Mail(Person from, LinkedList<Person> to, LinkedList<Person> cc, String message){
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.message = message;
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

    public String getMessage(){return message;}
}
