package com.example.hospital_appointment_system.Receptionist;

import com.example.hospital_appointment_system.Patient.Patient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReceptionistTest {

    @Test
    void generate_id() {
        Receptionist receptionist=new Receptionist();
        assertEquals(10,receptionist.generate_id().length());
        assertEquals("PAT-",receptionist.generate_id().substring(0,4));
    }

    @Test
    void generate_token() throws Exception {
        Receptionist receptionist=new Receptionist();
        assertInstanceOf(Integer.class,receptionist.generate_token("A"));
    }

}