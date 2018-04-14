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
    }

    private void connect(){
        try {
            clientSocket = new Socket(serverAdress, serverPort);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            skipServerMessage(4); // skip welcome messages
            LOG.log(Level.INFO, "Connected to the server");
        } catch(IOException e){
            LOG.log(Level.SEVERE,"Can't connect to server: " + e);
        }
    }

    @Override
    public void sendMail(Mail mail) {
        connect(); // connect to the server

        LOG.log(Level.INFO, "### Start to send an email");

        // EHLO
        sendToServer("EHLO local");
        skipServerMessage(7); // skip cmd

        // MAIL FROM
        sendToServer("MAIL FROM: " + mail.getFrom().getEmail());
        skipServerMessage();

        // MAIL TO
        for(Person p: mail.getTo()){
            sendToServer("RCPT TO: " + p.getEmail());
            skipServerMessage();
        }

        // MAIL CC
        for(Person p: mail.getCc()){
            sendToServer("RCPT TO: " + p.getEmail());
            skipServerMessage();
        }

        // DATA
        sendToServer("DATA");
        skipServerMessage();

        // DATA content
        Scanner scanner = new Scanner(mail.getContent());
        while(scanner.hasNext()){
            sendToServer(scanner.nextLine());
        }
        sendToServer("."); // end of content

        LOG.log(Level.INFO, "Mail send");
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
        writer.println(s);
        writer.flush();
        LOG.log(Level.INFO,"Message send to server: " + s);
    }
}
