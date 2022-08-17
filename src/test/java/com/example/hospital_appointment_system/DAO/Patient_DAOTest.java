package com.example.hospital_appointment_system.DAO;

import com.example.hospital_appointment_system.Appointment.appointment;
import com.example.hospital_appointment_system.Patient.Patient;
import com.example.hospital_appointment_system.Queue.sqsQueue;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Patient_DAOTest {

    @Test
    void AddingPatientAndDisplayingIt() throws Exception {

        Patient patient=new Patient();
        patient.setId("111");
        patient.setEmail("test@test.com");
        patient.setAge(12);
        patient.setName("TEST DATA");
        
        Patient_DAO patient_dao=new Patient_DAO();
        patient_dao.deleteTestData();
        patient_dao.addPatient(patient);
        Patient patientInfo = patient_dao.getPatientInfo("111");
        assertEquals(patientInfo.getName(),patient.getName());
        assertEquals(patientInfo.getId(),patient.getId());
        assertEquals(patientInfo.getEmail(),patient.getEmail());
        assertEquals(patientInfo.getAge(),patient.getAge());
        patient_dao.deleteTestData();
    }

    @Test
    void DeletingARecentlyAddedPatient() throws Exception {
        Patient patient=new Patient();
        patient.setId("111");
        patient.setEmail("test@test.com");
        patient.setAge(12);
        patient.setName("TEST DATA");

        Patient_DAO patient_dao=new Patient_DAO();
        patient_dao.addPatient(patient);

        assertEquals(patient_dao.deletePatient("111"),1);
    }

    @Test
    void ValidatingPresenceOfPatient() throws Exception {
        Patient patient=new Patient();
        patient.setId("111");
        patient.setEmail("test@test.com");
        patient.setAge(12);
        patient.setName("TEST DATA");

        Patient_DAO patient_dao=new Patient_DAO();
        patient_dao.addPatient(patient);

        assertEquals(patient_dao.validatePatientId("111"),true);

        patient_dao.deleteTestData();

    }

    @Test
    void IfPateintIsNotThere() throws Exception {
        Patient patient=new Patient();
        patient.setId("123");
        patient.setEmail("test@test.com");
        patient.setAge(12);
        patient.setName("TEST DATA");

        Patient_DAO patient_dao=new Patient_DAO();
        patient_dao.addPatient(patient);

        assertEquals(patient_dao.validatePatientId("111"),false);

        patient_dao.deleteTestData();

    }

    @Test
    void TestingWaitingTime() throws Exception {

        new Patient_DAO().deleteTestData();
        Patient patient=new Patient();
        patient.setId("123");
        patient.setEmail("test@test.com");
        patient.setAge(12);
        patient.setName("TEST DATA");

        appointment app=new appointment();
        app.setPatient_id("123");
        app.setDoc_code('A');
        app.setDate();
        app.setAppointment_id("111");
        app.setToken_number(100);

        Patient_DAO patient_dao=new Patient_DAO();
        patient_dao.addPatient(patient);
        new Appointment_DAO().addAppointment(app);

        System.out.println(patient_dao.getWaitingTime("123"));

        patient_dao.deleteTestData();
    }

    @Test
    void TestingWaitingTime1MinWait() throws Exception {
        new sqsQueue().deleteAllMessages("doctorA");


        Patient patient=new Patient();
        patient.setId("12345");
        patient.setEmail("test@test.com");
        patient.setAge(12);
        patient.setName("TEST DATA");

        appointment app=new appointment();
        app.setPatient_id("12345");
        app.setDoc_code('A');
        app.setDate();
        app.setAppointment_id("111");
        app.setToken_number(100);

        new Patient_DAO().deleteTestData();
        new Patient_DAO().addPatient(patient);
        new Appointment_DAO().addAppointment(app);

        new sqsQueue().sendMessage("doctorA",new Gson().toJson(app));

        assertEquals(new Patient_DAO().getWaitingTime("12345"),1);
        Thread.sleep(2000);
        new sqsQueue().deleteAllMessages("doctorA");
        new Patient_DAO().deleteTestData();

    }

    @Test
    void TestingWaitingTime15MinsWait() throws Exception {
        new sqsQueue().deleteAllMessages("doctorA");
        new Patient_DAO().deleteTestData();

        Patient patient=new Patient();
        patient.setId("123");
        patient.setEmail("test@test.com");
        patient.setAge(12);
        patient.setName("TEST DATA");

        Patient patient1=new Patient();
        patient1.setId("124");
        patient1.setEmail("test@test.com");
        patient1.setAge(12);
        patient1.setName("TEST DATA");

        appointment app=new appointment();
        app.setPatient_id("123");
        app.setDoc_code('A');
        app.setDate();
        app.setAppointment_id("111");
        app.setToken_number(100);

        appointment app1=new appointment();
        app1.setPatient_id("124");
        app1.setDoc_code('A');
        app1.setDate();
        app1.setAppointment_id("112");
        app1.setToken_number(100);

        new Patient_DAO().addPatient(patient);
        new Appointment_DAO().addAppointment(app);
        new Patient_DAO().addPatient(patient1);
        new Appointment_DAO().addAppointment(app1);

        new sqsQueue().sendMessage("doctorA",new Gson().toJson(app));
        new sqsQueue().sendMessage("doctorA",new Gson().toJson(app1));

        assertEquals(new Patient_DAO().getWaitingTime("124"),15);
        Thread.sleep(2000);
        new sqsQueue().deleteAllMessages("doctorA");
        new Patient_DAO().deleteTestData();

    }

    @Test
    void WaitingTimeWithDoctorBusy() throws Exception {
        new sqsQueue().deleteAllMessages("doctorA");

        Patient patient=new Patient();
        patient.setId("123");
        patient.setEmail("test@test.com");
        patient.setAge(12);
        patient.setName("TEST DATA");

        appointment app=new appointment();
        app.setPatient_id("123");
        app.setDoc_code('A');
        app.setDate();
        app.setAppointment_id("111");
        app.setToken_number(100);

        new Patient_DAO().addPatient(patient);
        new Appointment_DAO().addAppointment(app);

        new sqsQueue().sendMessage("doctorA",new Gson().toJson(app));

        new Doctor_DAO().setDoctorStatus("NOTFREE","A");
        assertEquals(new Patient_DAO().getWaitingTime("123"),15);
        Thread.sleep(2000);
        new Doctor_DAO().setDoctorStatus("FREE","A");
        new sqsQueue().deleteAllMessages("doctorA");
        new Patient_DAO().deleteTestData();

    }


}