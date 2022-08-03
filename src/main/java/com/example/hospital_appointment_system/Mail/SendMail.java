package com.example.hospital_appointment_system.Mail;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
    public static void sendEmail(String recipient, String text) throws MessagingException {
        System.out.println("Preparing to send email...");
        Properties properties=new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String myAccount="shopifytestacc2022@gmail.com";
        String password="prewyepwtsmolmfn";


        Session session= Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccount,password);
            }
        });


        Message message=prepareMessage(session,myAccount,recipient,text);

        Transport.send(message);
        System.out.println("Message Sent Successfully");
    }

    private static Message prepareMessage(Session session,String myAccountEmail, String recipient, String text) {

        try {
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
            message.setSubject("First Email from Java");
            message.setText(text);
            return message;
        } catch (Exception e) {
            throw new RuntimeException(e);

        }


    }
}
