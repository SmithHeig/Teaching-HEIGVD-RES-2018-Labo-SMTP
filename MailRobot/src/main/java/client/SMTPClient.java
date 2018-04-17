/**
 * SMTPClient.java
 * @authors James Smith and Jérémie Chatillon
 */
package client;

import model.mail.Group;
import model.mail.Mail;
import model.mail.Person;

import java.io.*;
import java.net.Socket;
import java.util.Base64;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SMTPClient implements client.ISMTPClient {
    private final static Logger LOG = Logger.getLogger(SMTPClient.class.getName());

    // SERVER COMMUNICATION
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    // SERVER ATTRIBUTS
    private String serverAdress;
    private int serverPort;

    public SMTPClient(String serverAdress, int serverPort){
        this.serverAdress = serverAdress;
        this.serverPort = serverPort;

        LOG.log(Level.INFO, "SMTPClient created...");
    }

    /**
     * Make the connection to the SMTP server and create the Streams to communicat with him
     * Skip the Hello Message
     */
    private void connect(){
        try {
            LOG.log(Level.INFO, "Try to connect to server " + serverAdress + " on port " + serverPort);

            clientSocket = new Socket(serverAdress, serverPort);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            skipServerMessage("220 "); // skip welcome messages

            LOG.log(Level.INFO, "Connected to the server");
        } catch(IOException e){
            LOG.log(Level.SEVERE,"Can't connect to server: " + e);
            System.exit(-1);
        }
    }

    /**
     * Disconnect the client from the server
     * Close all the streams
     */
    private void disconnect(){
        try {
            clientSocket.close();
            writer.close();
            reader.close();
            LOG.log(Level.INFO, "Disconnected from server");
        }catch(IOException e){
            LOG.log(Level.INFO, "Unable to disconnect: " + e);
        }
    }

    /**
     * Function to send mails.
     * @param mails - Take the mails to send
     */
    @Override
    public void sendMail(Mail ... mails) {

        for(Mail mail: mails) {
            connect(); // connect to the server

            LOG.log(Level.INFO, "### Start to send emails");

            // EHLO
            sendToServer("EHLO local", "250 ");

            // MAIL FROM
            sendToServer("MAIL FROM: " + mail.getFrom().getEmail(), "250 ");

            // MAIL TO
            for (Person p : mail.getTo()) {
                sendToServer("RCPT TO: " + p.getEmail(), "250 ");
            }

            // MAIL CC
            for (Person p : mail.getCc()) {
                sendToServer("RCPT TO: " + p.getEmail(), "250 ");
            }

            // DATA
            sendToServer("DATA", "354");

            // DATA content
            String s = new String();
            s += "Content-Type: text/plain; charset=utf-8\r\n";
            s += "From: " + mail.getFrom().getEmail() + "\r\n";
            s += "To: " + convertListPersonToString(mail.getTo()) + "\r\n";
            s += "Cc: " + convertListPersonToString(mail.getCc()) + "\r\n";
            s += "Subject: =?UTF-8?B?" + Base64.getEncoder().encodeToString(mail.getSubject().getBytes()) + "?=\r\n";

            Scanner scanner = new Scanner(mail.getContent());
            while (scanner.hasNext()) {
                s += scanner.nextLine() + "\r\n";
            }
            s += ".";
            sendToServer(s,"250 ");


            LOG.log(Level.INFO, "Mail send");

            sendToServer("quit","221 ");
            disconnect();
        }
        LOG.log(Level.INFO, "All mails send");
    }

    /**
     * Convert a list of Person into a String seperate with a comma (usefull to make the list of Person to send or cc
     * @param persons - the list of person to convert
     * @return A String with all the person email seperate by a comma
     */
    private String convertListPersonToString(LinkedList<Person> persons){
        String s = new String();
        for(Person p: persons){
            s += p.getEmail();

            if(!p.equals(persons.getLast())){
                s += ", ";
            }

        }
        return s;
    }

    /**
     * Skip and read the messages return from the server
     * @param codeOk - The code we want the server to return us to continue to the next step
     */
    private void skipServerMessage(String codeOk){
        try {
            String line;
            do {
                line = reader.readLine();
                LOG.log(Level.INFO, "Server message skipped: " + line);
                if(checkError(line)){
                    System.exit(-1);
                }
            } while(!line.contains(codeOk)); // Read all the message until the ok message
        } catch (IOException e){
            LOG.log(Level.SEVERE, "Unable to read the server datas: " + e);
        }
    }

    /**
     * Check if the server return an error (just check the knowed error)
     * @param s - return message from server
     * @return if the return message is an error
     */
    private boolean checkError(String s){
        if(s.contains("501")){
            LOG.log(Level.SEVERE, s);
            return true;
        } else  if(s.contains("421 ")){
            LOG.log(Level.SEVERE, s);
            return true;
        }
        return false;
    }

    /**
     * Function to send a command to the server and will skip the return message
     * @param s - the cmd to send to the server
     * @param returnMessageFromServer - the message we want from the server
     */
    private void sendToServer(String s, String returnMessageFromServer){
        writer.print(s + "\r\n");
        writer.flush();
        skipServerMessage(returnMessageFromServer);
        LOG.log(Level.INFO, s);
    }
}
