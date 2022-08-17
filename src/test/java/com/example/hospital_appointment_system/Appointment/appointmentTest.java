package com.example.hospital_appointment_system.Appointment;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class appointmentTest {

    @Test
    void TestingDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        appointment app=new appointment();
        app.setDate();
        assertEquals(app.getDate(),String.valueOf(dateFormat.format(date)));
    }

    @Test
    void TestingToken_number() {
        appointment app=new appointment();
        app.setToken_number(1);
        assertEquals(app.getToken_number(),1);
    }


    @Test
    void TestingAppointment_id() {
        appointment app=new appointment();
        app.setAppointment_id("APP-123");
        assertEquals(app.getAppointment_id(),"APP-123");
    }

    @Test
    void TestingPatient_id() {
        appointment app=new appointment();
        app.setPatient_id("PAT-001");
        assertEquals(app.getPatient_id(),"PAT-001");
    }


    @Test
    void TestingDoc_code() {
        appointment app=new appointment();
        app.setDoc_code('A');
        assertEquals(app.getDoc_code(),'A');
    }


}