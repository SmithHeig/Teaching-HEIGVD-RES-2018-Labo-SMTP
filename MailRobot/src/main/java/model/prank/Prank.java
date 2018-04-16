package model.prank;

import client.SMTPClient;
import model.mail.Mail;

import java.util.LinkedList;

public class Prank {
    private Mail[] mails;
    private SMTPClient smtp;

    public Prank(SMTPClient smtp, Mail ... mails){
        this.mails = mails;
        this.smtp = smtp;
    }

    public void run(){
        smtp.sendMail(mails);
    }
}
