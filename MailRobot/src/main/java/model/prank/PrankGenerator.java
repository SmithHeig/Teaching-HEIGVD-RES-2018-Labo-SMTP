package model.prank;

import client.SMTPClient;
import configurationManager.ConfigurationManager;
import model.mail.Mail;
import model.mail.Person;

import java.util.LinkedList;
import java.util.logging.*;

public class PrankGenerator {

    private static final Logger LOG = Logger.getLogger(PrankGenerator.class.getName());
    private ConfigurationManager configurationManager;

    private LinkedList<Person> victims;
    LinkedList<String> messages;

    Prank prank;
    SMTPClient client;

    public PrankGenerator(){
        this.configurationManager = new ConfigurationManager();
        client = new SMTPClient(configurationManager.getServerAdress(),configurationManager.getServerPort());
        LOG.log(Level.INFO, "PrankGenerator Created");
    }

    public Prank generatePrank(){
        LOG.log(Level.INFO, "Genarating pranks...");
        victims = configurationManager.getVictims();
        messages = configurationManager.getMessage();
        int nbGroups = configurationManager.getNbGroups();
        int nbVictimsPerPranks = (victims.size() / nbGroups) - 1; // sub the sender

        Mail[] mails = new Mail[nbGroups];

        for(int i = 0; i < nbGroups; ++i){
            LinkedList<Person> recievers = new LinkedList();
            for(int j = 0; j < nbVictimsPerPranks; j++){
                recievers.add(pickRandomPerson());
            }
            Person sender = pickRandomPerson();
            mails[i] = new Mail(sender, recievers, configurationManager.getCCPerson(), pickRandomMessage());
        }
        prank = new Prank(client, mails);
        return prank;
    }

    public void runPrank(){
        LOG.log(Level.INFO, "Running prank...");
        prank.run();
    }

    public Prank getPrank(){
        return prank;
    }

    private Person pickRandomPerson(){
        int indexPerson = randomGenerator(victims.size()) - 1;
        Person p = victims.get(indexPerson);
        victims.remove(indexPerson);
        return p;
    }

    private String pickRandomMessage(){
        int indexMessage = randomGenerator(messages.size()) - 1;
        String message = messages.get(indexMessage);
        messages.remove(indexMessage);
        return message;
    }

    private int randomGenerator(int max){
        int randomGen = (int)(1 + Math.random() * max);
        return randomGen;
    }
}
