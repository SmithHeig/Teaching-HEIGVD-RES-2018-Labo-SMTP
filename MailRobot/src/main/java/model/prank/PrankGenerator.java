/**
 * PrankGenerator.java
 * @authors James Smith and Jérémie Chatillon
 */
package model.prank;

import client.SMTPClient;
import configurationManager.ConfigurationManager;
import model.mail.Mail;
import model.mail.Person;

import java.util.LinkedList;
import java.util.logging.*;

public class PrankGenerator {
    private static final Logger LOG = Logger.getLogger(PrankGenerator.class.getName());

    // ATTRIBUTS
    private ConfigurationManager configurationManager;

    private LinkedList<Person> victims;
    LinkedList<String> messages;

    Prank prank;
    SMTPClient client;

    /**
     * Constructor
     * Init the configuration manager and create an SMTP client
     */
    public PrankGenerator(){
        this.configurationManager = new ConfigurationManager();
        client = new SMTPClient(configurationManager.getServerAdress(),configurationManager.getServerPort());
        LOG.log(Level.INFO, "PrankGenerator Created");
    }

    /**
     * generate a prank
     * @return a prank generate with the config files
     */
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

    /**
     * run the prank generate
     */
    public void runPrank(){
        LOG.log(Level.INFO, "Running prank...");
        prank.run();
    }

    /**
     * return the prank generate
     * @return
     */
    public Prank getPrank(){
        return prank;
    }

    /**
     * utils - return a random person from the victims. Delete it from the list
     * @return Person random
     */
    private Person pickRandomPerson(){
        int indexPerson = randomGenerator(victims.size()) - 1;
        Person p = victims.get(indexPerson);
        victims.remove(indexPerson);
        return p;
    }

    /**
     * utils - return a random messge from the list of message
     * @return
     */
    private String pickRandomMessage(){
        int indexMessage = randomGenerator(messages.size()) - 1;
        String message = messages.get(indexMessage);
        messages.remove(indexMessage);
        return message;
    }

    /**
     * utils - generate a random number
     * @param max - number max generated
     * @return int random
     */
    private int randomGenerator(int max){
        int randomGen = (int)(1 + Math.random() * max);
        return randomGen;
    }
}
