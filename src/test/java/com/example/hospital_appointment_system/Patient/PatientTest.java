package com.example.hospital_appointment_system.Patient;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    @Test
    void getId() {
        Patient patient=new Patient();
        patient.setId("abc-123");
        assertEquals(patient.getId(),"abc-123");
    }

    @Test
    void setId() {
        Patient patient=new Patient();
        patient.setId("abc-123");
        assertEquals(patient.getId(),"abc-123");
    }

    @Test
    void getName() {
        Patient patient=new Patient();
        patient.setName("Daniyal");
        assertEquals(patient.getName(),"Daniyal");
    }

    @Test
    void setName() {
        Patient patient=new Patient();
        patient.setName("Ali");
        assertEquals(patient.getName(),"Ali");
    }

    @Test
    void getAge() {
        Patient patient=new Patient();
        patient.setAge(1);
        assertEquals(patient.getAge(),1);
    }

    @Test
    void setAge() {
        Patient patient=new Patient();
        patient.setAge(23);
        assertEquals(patient.getAge(),23);
    }

    @Test
    void getEmail() {
        Patient patient=new Patient();
        patient.setEmail("daniyal@gmail.com");
        assertEquals(patient.getEmail(),"daniyal@gmail.com");
    }

    @Test
    void setEmail() {
        Patient patient=new Patient();
        patient.setEmail("daniyal@gmail.com");
        assertEquals(patient.getEmail(),"daniyal@gmail.com");
    }
}