package com.example.hospital_appointment_system.Mail;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
    public static void sendEmail(String recipient) throws Exception {
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


        Message message=prepareMessage(session,myAccount,recipient);

        Transport.send(message);
        System.out.println("Message Sent Successfully");
    }

    private static Message prepareMessage(Session session,String myAccountEmail, String recipient) throws Exception {


            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
            message.setSubject("Checkup Turn Reminder-ABC Hospital");
            message.setText("Patient,\n\nThis is to inform you that now it is your turn for the checkup. Kindly head directly towards your doctor so that he can start your checkup accordingly.\n\nRegards,\nXYZ Hospital|123 ABC,Lahore");
            return message;



    }
}
