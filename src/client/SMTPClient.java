package client;

import model.mail.Group;
import model.mail.Mail;

import java.net.Socket;
import java.util.LinkedList;

public class SMTPClient implements ISMTPClient{
    private Socket socket;

    public SMTPClient(String server){
        socket = new Socket();
    }

    @Override
    public void sendMail(Mail mail) {

    }
}
