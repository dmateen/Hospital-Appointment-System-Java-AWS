package com.example.hospital_appointment_system;
import com.example.hospital_appointment_system.Patient.Patient;
import com.example.hospital_appointment_system.Queue.sqsQueue;
import com.example.hospital_appointment_system.Mail.SendMail;
import com.example.hospital_appointment_system.Receptionist.Receptionist;

public class Main {
    public static void main(String []args) throws Exception
    {
        sqsQueue SQS=new sqsQueue();
        Patient patient=new Patient();
        patient.setName("Test");
        Receptionist receptionist=new Receptionist();
        receptionist.addtoQueue(patient);
        //SQS.sendMessage("doctorA",patient);
        SQS.readMessage("doctorA");

//        String text="testing";
//        String recipient="m.ali.ather786@gmail.com";
//        SendMail.sendEmail(recipient,text);

    }
}
