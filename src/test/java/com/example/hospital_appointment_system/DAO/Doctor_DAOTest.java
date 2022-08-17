package com.example.hospital_appointment_system.DAO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Doctor_DAOTest {

    @Test
    void TestingSetterGetters() throws Exception {
        Doctor_DAO doctor_dao=new Doctor_DAO();
        String initialStatus=doctor_dao.getDoctorStatus("A");

        doctor_dao.setDoctorStatus("FREE","A");
        assertEquals(doctor_dao.getDoctorStatus("A"),"FREE");
        doctor_dao.setDoctorStatus(initialStatus,"A");
    }


}