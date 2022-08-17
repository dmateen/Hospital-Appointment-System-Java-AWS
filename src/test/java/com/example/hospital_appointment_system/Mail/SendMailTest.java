package com.example.hospital_appointment_system.Mail;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.*;

class SendMailTest {

    @Test
    void TestingsendEmail() throws Exception {

        SendMail.sendEmail("test@test.com");


    }
}