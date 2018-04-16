package client;

import model.mail.Group;
import model.mail.Mail;
import model.mail.Person;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SMTPClient implements client.ISMTPClient {
    private final static Logger LOG = Logger.getLogger(SMTPClient.class.getName());

    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;


    private String serverAdress;
    private int serverPort;

    public SMTPClient(String serverAdress, int serverPort){
        this.serverAdress = serverAdress;
        this.serverPort = serverPort;

        LOG.log(Level.INFO, "SMTPClient created...");
    }

    private void connect(){
        try {
            LOG.log(Level.INFO, "Try to connect to server " + serverAdress + " on port " + serverPort);

            clientSocket = new Socket(serverAdress, serverPort);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            skipServerMessage(); // skip welcome messages

            LOG.log(Level.INFO, "Connected to the server");
        } catch(IOException e){
            LOG.log(Level.SEVERE,"Can't connect to server: " + e);
            System.exit(-1);
        }
    }

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

    @Override
    public void sendMail(Mail ... mails) {

        for(Mail mail: mails) {
            connect(); // connect to the server

            LOG.log(Level.INFO, "### Start to send emails");

            // EHLO
            sendToServer("EHLO local");

            // MAIL FROM
            sendToServer("MAIL FROM: " + mail.getFrom().getEmail());

            // MAIL TO
            for (Person p : mail.getTo()) {
                sendToServer("RCPT TO: " + p.getEmail());
            }

            // MAIL CC
            for (Person p : mail.getCc()) {
                sendToServer("RCPT TO: " + p.getEmail());
            }

            // DATA
            sendToServer("DATA");

            // DATA content

            String s = new String();
            s += "From: " + mail.getFrom().getEmail() + "\r\n";
            s += "To: " + convertListPersonToString(mail.getTo()) + "\r\n";
            s += "Cc: " + convertListPersonToString(mail.getCc()) + "\r\n";
            /*sendToServer("From: " + mail.getFrom().getEmail());
            sendToServer("To: " + convertListPersonToString(mail.getTo()));
            sendToServer("Cc: " );*/

            Scanner scanner = new Scanner(mail.getMessage());
            //LOG.log(Level.INFO,"----------------" + mail.getMessage());
            while (scanner.hasNext()) {
                s += scanner.nextLine() + "\r\n";
            }
            s += ".";
            sendToServer(s);


            LOG.log(Level.INFO, "Mail send");

            sendToServer("quit");
            disconnect();
        }
        LOG.log(Level.INFO, "All mails send");
    }


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
    private void skipServerMessage(){
        try {
            String line;
            line = reader.readLine();
            LOG.log(Level.INFO,"Server message skipped: " + line);
        } catch (IOException e){
            LOG.log(Level.SEVERE, "Unable to read the server datas: " + e);
        }
    }

    private void skipServerMessage(int nbSkip){
        for(int i = 0; i < nbSkip; ++i){
            skipServerMessage();
        }
    }

    private void sendToServer(String s){
        writer.print(s + "\r\n");
        writer.flush();
        waitServer();
        System.out.println(s);
    }

    private void waitServer(){
        try {
            Thread.sleep(10);
        }catch(InterruptedException e){

        }
    }
}
