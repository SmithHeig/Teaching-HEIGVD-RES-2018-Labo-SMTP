package config;

import model.mail.Person;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Properties;

public class ConfigurationManager implements IConfigurationManager {
    private String serverAdress;
    private int serverPort;
    private LinkedList<Person> victims;
    private LinkedList<Person> cc;
    private LinkedList<String> messages;
    private int nbGroups;

    public ConfigurationManager() throws IOException{
        loadAdressesFromFile("./config/victims.utf8");
        loadMessageFromFile("./config/messages.utf8");
        loadProperties("./config/config.properties");
    }

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

    }

    public void loadAdressesFromFile(String file) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String s;
        while((s = in.readLine()) != null){
            victims.add(new Person(s));
        }
        in.close();
    }

    public void loadMessageFromFile(String file) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String s;
        while((s = in.readLine()) != null){
            messages.add(s);
        }
        in.close();
    }
    @Override
    public LinkedList<Person> getVictims() {
        return victims;
    }

    @Override
    public LinkedList<Person> getCCPerson() {
        return cc;
    }

    @Override
    public LinkedList<String> getMessage() {
        return messages;
    }

    @Override
    public String getServerAdress() {
        return serverAdress;
    }

    @Override
    public int getServerPort() {
        return serverPort;
    }

    @Override
    public int getNbGroups() {
        return nbGroups;
    }
}
