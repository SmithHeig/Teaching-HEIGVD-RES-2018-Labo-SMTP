package client;

import model.mail.Mail;

public interface ISMTPClient {
    public void sendMail(Mail mail);
}
