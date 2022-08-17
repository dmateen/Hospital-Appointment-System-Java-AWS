package com.example.hospital_appointment_system;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DetailsTest {

    @Test
    void TestPatient_name() {
        Details details=new Details();
        details.setPatient_name("Rehman");
        assertEquals(details.getPatient_name(),"Rehman");
    }

    @Test
    void TestDoc_code() {
        Details details=new Details();
        details.setDoc_code('A');
        assertEquals(details.getDoc_code(),'A');
    }

    @Test
    void TestAge() {
        Details details=new Details();
        details.setAge(23);
        assertEquals(details.getAge(),23);
    }

    @Test
    void TestEmail() {
        Details details=new Details();
        details.setEmail("ali@gmail.com");
        assertEquals(details.getEmail(),"ali@gmail.com");
    }

    @Test
    void TestReason() {
        Details details=new Details();
        details.setReason("Any Reason");
        assertEquals(details.getReason(),"Any Reason");
    }
}