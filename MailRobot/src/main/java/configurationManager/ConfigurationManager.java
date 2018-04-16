/**
 * ConfigurationManager.java
 * @authors James Smith and Jérémie Chatillon
 * Read all the configurations files and store them
 */

package configurationManager;

import model.mail.Person;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigurationManager implements IConfigurationManager {
    private final static Logger LOG = Logger.getLogger(ConfigurationManager.class.getName());

    // CONFIGURATION FO THE APPLICATION
    private String serverAdress;
    private int serverPort;
    private LinkedList<Person> victims;
    private LinkedList<Person> cc;
    private LinkedList<String> messages;
    private int nbGroups;

    /**
     * Constructor
     * Read all the config files
     */
    public ConfigurationManager(){
        try {
            // initialisation
            victims = new LinkedList();
            cc = new LinkedList();
            messages = new LinkedList();

            // Loading datas
            loadAdressesFromFile("./config/victims.utf8");
            loadMessageFromFile("./config/messages.utf8");
            loadProperties("./config/config.properties");

            verify();
            LOG.log(Level.INFO,"Configuration loaded");
        } catch(IOException e){
            LOG.log(Level.SEVERE, "Can not load config files: " + e);
        }
    }

    /**
     * Load the priorities of the run
     * @param file - file .properties with all the data usefull to the application
     * @throws IOException - if file do not exist
     */
    private void loadProperties(String file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        Properties properties = new Properties();
        properties.load(in);

        serverAdress = properties.getProperty("SMTPServerAdress");
        serverPort = Integer.parseInt(properties.getProperty("SMTPServerPort"));
        nbGroups = Integer.parseInt(properties.getProperty("nbGroups"));

        this.cc = new LinkedList<>();
        String witnessesToCC = properties.getProperty("witnessesToCC");
        String[] ccs = witnessesToCC.split(",");
        for(String ccPerson : ccs){
            this.cc.add(new Person(ccPerson));
        }

        LOG.log(Level.INFO, "Properties loaded...");
    }

    /**
     * Load the email adresse from file.
     * @param file - file content all the email. Each email on a line
     * @throws IOException - if file do not exist
     */
    public void loadAdressesFromFile(String file) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String s;
        while((s = in.readLine()) != null){
            victims.add(new Person(s));
        }
        in.close();
        LOG.log(Level.INFO,"Email Adresses loaded");
    }

    /**
     * Load all the message from a file
     * @param file - fil content all the message.
     *             Each message should start with Subject: <the subject> and finish with the seperation ==
     * @throws IOException - if file do not exist
     */
    public void loadMessageFromFile(String file) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String s;
        String message = new String();
        while((s = in.readLine()) != null){
            if(s.equals("==")){
                messages.add(message);
                message = "";
            } else {
                message += s + "\r\n";
            }
        }
        in.close();
        LOG.log(Level.INFO,"Message Loaded");
    }

    /**
     * Verify if the number of groups have enogh victims (min 3)
     */
    private void verify(){
        // Test if enogh number of victims compare to nb groups
        if(victims.size() / nbGroups < 3){
            LOG.log(Level.SEVERE, "ERROR - Too much groups compare to number of victimes please change your configuration");
            System.exit(-1);
        }
    }

    /**
     * getter - the list of victims (emails)
     * @return list of emails
     */
    @Override
    public LinkedList<Person> getVictims() {
        return victims;
    }

    /**
     * getter - the list of person on Cc
     * @return list of emails
     */
    @Override
    public LinkedList<Person> getCCPerson() {
        return cc;
    }

    /**
     * getter - List of message
     * @return list of strings (messages)
     */
    @Override
    public LinkedList<String> getMessage() {
        return messages;
    }

    /**
     * getter - the server adress
     * @return String - addresse of the server
     */
    @Override
    public String getServerAdress() {
        return serverAdress;
    }

    /**
     * getter - the server port
     * @return port of the server
     */
    @Override
    public int getServerPort() {
        return serverPort;
    }

    /**
     * getter - number of groups
     * @return the number of groups contain in the properties
     */
    @Override
    public int getNbGroups() {
        return nbGroups;
    }
}
