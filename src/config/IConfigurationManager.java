package config;

import model.mail.Person;

import java.io.IOException;
import java.util.LinkedList;

public interface IConfigurationManager {
    LinkedList<Person> getVictims();

    LinkedList<Person> getCCPerson();

    LinkedList<String> getMessage();

    String getServerAdress();

    int getServerPort();

    int getNbGroups();
}
