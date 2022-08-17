package com.example.hospital_appointment_system.PatientServlet;

import com.example.hospital_appointment_system.Appointment.appointment;
import com.example.hospital_appointment_system.DAO.Appointment_DAO;
import com.example.hospital_appointment_system.DAO.Patient_DAO;
import com.example.hospital_appointment_system.Patient.Patient;
import com.example.hospital_appointment_system.Queue.sqsQueue;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import javax.script.ScriptContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShowPatientDetailsTest {

    @Test
    void TestShowDetailsServlet() throws Exception {
        new sqsQueue().deleteAllMessages("doctorA");

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

        new sqsQueue().sendMessage("doctorA", new Gson().toJson(app));

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(mockRequest.getParameter("patientID")).thenReturn("12345");

        StringWriter sw = new StringWriter();

        when(response.getWriter()).thenReturn(new PrintWriter(sw));
        when(mockRequest.getParameter("patientID")).thenReturn("12345");
        //mockRequest.setAttribute("patientID","12345");

        ShowPatientDetails showPatientDetails = new ShowPatientDetails();
        showPatientDetails.doPost(mockRequest, response);


        Thread.sleep(2000);
        new sqsQueue().deleteAllMessages("doctorA");
        new Patient_DAO().deleteTestData();

    }

//    @Test
//    void CheckException() throws IOException, ServletException {
//
//        StringWriter sw = new StringWriter();
//
//        try {
//            HttpServletResponse response=null;
//            HttpServletRequest mockRequest=null;
//            when(response.getWriter()).thenReturn(new PrintWriter(sw));
//            when(mockRequest.getParameter("patientID")).thenReturn("12345");
//
//        }
//        catch (Exception e) {
//            Assert.fail();
//        }
//
//    }

}