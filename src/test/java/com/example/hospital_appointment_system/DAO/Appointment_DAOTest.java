package com.example.hospital_appointment_system.DAO;

import com.example.hospital_appointment_system.Appointment.appointment;
import com.example.hospital_appointment_system.Patient.Patient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Appointment_DAOTest {

    @Test
    void addingAndDeletingAppointment() throws Exception {

        new Patient_DAO().deleteTestData();
        //Creating Patient
        Patient patient=new Patient();
        patient.setId("123");
        patient.setEmail("test@test.com");
        patient.setAge(12);
        patient.setName("TEST DATA");

        //Creating Appointment
        appointment app=new appointment();
        app.setPatient_id("123");
        app.setDoc_code('A');
        app.setDate();
        app.setAppointment_id("111");
        app.setToken_number(100);

        Appointment_DAO appointment_dao=new Appointment_DAO();
        new Patient_DAO().addPatient(patient);
        appointment_dao.addAppointment(app);

        assertEquals(appointment_dao.deleteAppointment("111"),1);



    }
}