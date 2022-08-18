package com.example.hospital_appointment_system.Servlets;

import com.example.hospital_appointment_system.Appointment.appointment;
import com.example.hospital_appointment_system.DAO.Appointment_DAO;
import com.example.hospital_appointment_system.DAO.Doctor_DAO;
import com.example.hospital_appointment_system.DAO.Patient_DAO;
import com.example.hospital_appointment_system.Patient.Patient;
import com.example.hospital_appointment_system.Queue.sqsQueue;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Any;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class buzzerTest {

    @Test
    void TestingBuzzerServlet() throws Exception {
        sqsQueue SQS=new sqsQueue();
        SQS.deleteAllMessages("doctorA");

        //---------- Creating Patient --------
        Patient patient = new Patient();
        patient.setId("12345");
        patient.setEmail("test@test.com");
        patient.setAge(12);
        patient.setName("TEST DATA");

        //--------- Creating Appointment -----------
        appointment app = new appointment();
        app.setPatient_id("12345");
        app.setDoc_code('A');
        app.setDate();
        app.setAppointment_id("111");
        app.setToken_number(100);



        Patient_DAO patient_dao=new Patient_DAO();
        Appointment_DAO appointment_dao=new Appointment_DAO();

        patient_dao.deleteTestData();
        patient_dao.addPatient(patient);
        appointment_dao.addAppointment(app);


        SQS.sendMessage("doctorA",new Gson().toJson(app));
        String receipt=SQS.getReceiptHandle("doctorA");



        HttpServletRequest request=mock(HttpServletRequest.class);
        HttpServletResponse response=mock(HttpServletResponse.class);


        when(request.getRequestDispatcher("/doctorList")).thenReturn(mock(RequestDispatcher.class));
        when(request.getParameter("docCode")).thenReturn("A");
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        buzzer Buzzer=new buzzer();
        Buzzer.doPost(request,response);

        patient_dao.deleteTestData();
        SQS.deleteMessage("doctorA",receipt);

        SQS.deleteAllMessages("doctorA");
        new Doctor_DAO().setDoctorStatus("FREE", "A");
    }

//    @Test
//    void TestingBuzzerServletWithPatientBusy() throws Exception {
//        sqsQueue SQS=new sqsQueue();
//        SQS.deleteAllMessages("doctorA");
//
//        //---------- Creating Patient --------
//        Patient patient = new Patient();
//        patient.setId("12345");
//        patient.setEmail("test@test.com");
//        patient.setAge(12);
//        patient.setName("TEST DATA");
//
//        //--------- Creating Appointment -----------
//        appointment app = new appointment();
//        app.setPatient_id("12345");
//        app.setDoc_code('A');
//        app.setDate();
//        app.setAppointment_id("111");
//        app.setToken_number(100);
//
//        //---------- Creating Patient --------
//        Patient patient1 = new Patient();
//        patient1.setId("123456");
//        patient1.setEmail("test@test.com");
//        patient1.setAge(12);
//        patient1.setName("TEST DATA");
//
//        //--------- Creating Appointment -----------
//        appointment app1 = new appointment();
//        app1.setPatient_id("123456");
//        app1.setDoc_code('A');
//        app1.setDate();
//        app1.setAppointment_id("1112");
//        app1.setToken_number(100);
//
//        Patient_DAO patient_dao=new Patient_DAO();
//        Appointment_DAO appointment_dao=new Appointment_DAO();
//
//        patient_dao.deleteTestData();
//        patient_dao.addPatient(patient);
//        appointment_dao.addAppointment(app);
//        patient_dao.addPatient(patient1);
//        appointment_dao.addAppointment(app1);
//
//        SQS.sendMessage("doctorA",new Gson().toJson(app));
//        String receipt=SQS.getReceiptHandle("doctorA");
//
//        SQS.sendMessage("doctorA",new Gson().toJson(app1));
//        String receipt1=SQS.getReceiptHandle("doctorA");
//
//
//
//        HttpServletRequest request=mock(HttpServletRequest.class);
//        HttpServletResponse response=mock(HttpServletResponse.class);
//
//
//        Doctor_DAO MockedDoctorDAO=mock(Doctor_DAO.class);
//        when(MockedDoctorDAO.getDoctorStatus("A")).thenReturn(receipt);
//
//        when(request.getRequestDispatcher("/doctorList")).thenReturn(mock(RequestDispatcher.class));
//        when(request.getParameter("docCode")).thenReturn("A");
//        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
//        buzzer Buzzer=new buzzer();
//        Buzzer.doPost(request,response);
//
//        patient_dao.deleteTestData();
//        SQS.deleteMessage("doctorA",receipt);
//        SQS.deleteMessage("doctorA",receipt1);
//        SQS.deleteAllMessages("doctorA");
//        new Doctor_DAO().setDoctorStatus("FREE", "A");
//    }

}