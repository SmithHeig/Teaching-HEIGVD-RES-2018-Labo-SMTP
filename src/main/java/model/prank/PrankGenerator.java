package model.prank;

import client.SMTPClient;
import config.ConfigurationManager;
import model.mail.Mail;
import model.mail.Person;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrankGenerator {

    private ConfigurationManager configurationManager;

    private LinkedList<Person> victims;
    LinkedList<String> messages;

    SMTPClient client;

    LinkedList<Prank> pranks;

    public PrankGenerator(ConfigurationManager configurationManager){
        this.configurationManager = configurationManager;
        client = new SMTPClient(configurationManager.getServerAdress(),configurationManager.getServerPort());
    }

    public LinkedList<Prank> generatePranks(){
        victims = configurationManager.getVictims();
        messages = configurationManager.getMessage();
        int nbGroups = configurationManager.getNbGroups();
        int nbVictimsPerPranks = victims.size() / nbGroups;

         pranks = new LinkedList();

        for(int i = 0; i < nbGroups; ++i){
            LinkedList<Person> recievers = new LinkedList();
            for(int j = 0; j < nbVictimsPerPranks; j++){
                recievers.add(pickRandomPerson());
            }
            Person sender = pickRandomPerson();
            Mail mail = new Mail(sender, recievers, configurationManager.getCCPerson(), pickRandomMessage());
            pranks.add(new Prank(client, mail));
        }
        return pranks;
    }

    public void runAllPranks(){
        for(Prank prank: pranks){
            prank.run();
        }
    }

    public LinkedList<Prank> getPranks(){
        return pranks;
    }

    private Person pickRandomPerson(){
        int indexPerson = randomGenerator(victims.size());
        Person p = victims.get(indexPerson);
        victims.remove(indexPerson);
        return p;
    }

    private String pickRandomMessage(){
        int indexMessage = randomGenerator(messages.size());
        String message = messages.get(indexMessage);
        messages.remove(indexMessage);
        return message;
    }

    private int randomGenerator(int max){
        return (int)Math.round(1 + (Math.random() * max));
    }
}
