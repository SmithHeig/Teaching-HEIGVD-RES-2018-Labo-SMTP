/**
 * Person.java
 * @authors James Smith and Jérémie Chatillon
 */
package model.mail;

public class Person {
    // ATTRIBUTS
    private String email;

    /**
     * Constructor
     * @param email - of the person
     */
    public Person(String email){
        this.email = email;
    }

    /**
     * getter - return the email of the person
     * @return String email
     */
    public String getEmail(){
        return email;
    }
}
