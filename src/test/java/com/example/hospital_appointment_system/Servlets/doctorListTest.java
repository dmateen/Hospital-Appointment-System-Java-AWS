package com.example.hospital_appointment_system.Servlets;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.xray.model.Http;
import com.example.hospital_appointment_system.Appointment.appointment;
import com.example.hospital_appointment_system.DAO.Appointment_DAO;
import com.example.hospital_appointment_system.DAO.Patient_DAO;
import com.example.hospital_appointment_system.Patient.Patient;
import com.example.hospital_appointment_system.Queue.sqsQueue;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class doctorListTest {

    @Test
    void doctorListServletTesting() throws Exception {

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
        new sqsQueue().sendMessage("doctorA",new Gson().toJson(app));


        HttpServletRequest request=mock(HttpServletRequest.class);
        HttpServletResponse response=mock(HttpServletResponse.class);

        request.setAttribute("docCode","A");


        when(request.getAttributeNames()).thenReturn(new Enumeration<String>() {
            @Override
            public boolean hasMoreElements() {
                return false;
            }

            @Override
            public String nextElement() {
                return null;
            }
        });

        when(request.getParameter("docCode")).thenReturn("A");
        when(String.valueOf(request.getAttribute("docCode"))).thenReturn("A");
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        doctorList DoctorList=new doctorList();
        DoctorList.doPost(request,response);



        new Patient_DAO().deleteTestData();
        new sqsQueue().deleteAllMessages("doctorA");
    }


}