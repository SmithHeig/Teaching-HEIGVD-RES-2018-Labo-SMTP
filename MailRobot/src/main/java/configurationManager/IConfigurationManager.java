/**
 * IConfigurationManager.java
 * Interface for a configuration manager
 * @authors James Smith and Jérémie Chatillon
 */
package configurationManager;

import model.mail.Person;

import java.io.IOException;
import java.util.LinkedList;

public interface IConfigurationManager {
    /**
     * getter - the list of victims (emails)
     * @return list of emails
     */
    LinkedList<Person> getVictims();

    /**
     * getter - the list of person on Cc
     * @return list of emails
     */
    LinkedList<Person> getCCPerson();

    /**
     * getter - List of message
     * @return list of strings (messages)
     */
    LinkedList<String> getMessage();

    /**
     * getter - the server adress
     * @return String - addresse of the server
     */
    String getServerAdress();

    /**
     * getter - the server port
     * @return port of the server
     */
    int getServerPort();

    /**
     * getter - number of groups
     * @return the number of groups contain in the properties
     */
    int getNbGroups();
}
