package com.example.hospital_appointment_system.PatientServlet;

import com.amazonaws.services.xray.model.Http;
import com.example.hospital_appointment_system.Appointment.appointment;
import com.example.hospital_appointment_system.DAO.Appointment_DAO;
import com.example.hospital_appointment_system.DAO.Patient_DAO;
import com.example.hospital_appointment_system.Patient.Patient;
import com.example.hospital_appointment_system.Queue.sqsQueue;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static javax.ws.rs.sse.SseEventSource.target;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



class patientloginTest {


    @Test
    void TestdoPostWithInValidPatient() throws Exception {
//        new sqsQueue().deleteAllMessages("doctorA");

        Patient patient = new Patient();
        patient.setId("12345");
        patient.setEmail("test@test.com");
        patient.setAge(12);
        patient.setName("TEST DATA");

        appointment app = new appointment();
        app.setPatient_id("12345");
        app.setDoc_code('A');
        app.setDate();
        app.setAppointment_id("111");
        app.setToken_number(100);

        new Patient_DAO().deleteTestData();
        new Patient_DAO().addPatient(patient);
        new Appointment_DAO().addAppointment(app);

        //new sqsQueue().sendMessage("doctorA", new Gson().toJson(app));

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response=mock(HttpServletResponse.class);

        when(request.getParameter("patientID")).thenReturn("111");
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        patientlogin patientlogin =new patientlogin();
        patientlogin.doPost(request,response);

//        Thread.sleep(2000);
        new sqsQueue().deleteAllMessages("doctorA");
        new Patient_DAO().deleteTestData();

    }

//    @Test
//    void TestdoPostWithValidPatient() throws Exception {
////        new sqsQueue().deleteAllMessages("doctorA");
//
//        Patient patient = new Patient();
//        patient.setId("12345");
//        patient.setEmail("test@test.com");
//        patient.setAge(12);
//        patient.setName("TEST DATA");
//
//        appointment app = new appointment();
//        app.setPatient_id("12345");
//        app.setDoc_code('A');
//        app.setDate();
//        app.setAppointment_id("111");
//        app.setToken_number(100);
//
//        new Patient_DAO().deleteTestData();
//        new Patient_DAO().addPatient(patient);
//        new Appointment_DAO().addAppointment(app);
//
//        //new sqsQueue().sendMessage("doctorA", new Gson().toJson(app));
//
////        MockHttpServletRequest request = new MockHttpServletRequest();
////        MockHttpServletResponse response=new MockHttpServletResponse();
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        RequestDispatcher requestDispatcher=request.getRequestDispatcher("/ShowPatientDetails");
//
//        when(request.getParameter("patientID")).thenReturn("12345");
//        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
//        when(request.getRequestDispatcher("/ShowPatientDetails")).thenReturn(requestDispatcher);
//        when(requestDispatcher.forward(request,response)).thenReturn(requestDispatcher.forward(request,response));
//
//        patientlogin patientlogin =new patientlogin();
//        patientlogin.doPost(request,response);
//
////        Thread.sleep(2000);
////        new sqsQueue().deleteAllMessages("doctorA");
//        new Patient_DAO().deleteTestData();
//
//    }
}