package model.prank;

import client.SMTPClient;
import config.ConfigurationManager;
import model.mail.Group;
import model.mail.Mail;

import java.util.LinkedList;

public class Prank {
    Mail[] mails;
    SMTPClient smtp;

    public Prank(SMTPClient smtp, Mail ... mails){
        this.mails = mails;
        this.smtp = smtp;
    }

    public void run(){
        smtp.sendMail(mails);
    }
}
