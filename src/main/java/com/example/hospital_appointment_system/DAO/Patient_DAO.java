package com.example.hospital_appointment_system.DAO;

import com.example.hospital_appointment_system.Patient.Patient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Patient_DAO {
    // -------- Declaring JDBC Vars --------
    String url;
    Connection con;
    Statement st;
    // -------- //Declaring JDBC Vars --------

    // -------- Default Constructor --------
    public Patient_DAO() throws Exception {

        // -------- JDBC Connection --------
        Class.forName("com.mysql.jdbc.Driver");
        url = "jdbc:mysql://127.0.0.1/hospital";
        con = DriverManager.getConnection(url, "root", "root");
        st = con.createStatement();
        // -------- //JDBC Connection --------

    }
    // -------- //Default Constructor --------

    // -------- Add Patient to DB --------
    public void addPatient(Patient patient) throws Exception {

        PreparedStatement preSt=con.prepareStatement("INSERT INTO patient values(?,?,?,?) ");

        preSt.setString(1, patient.getId());
        preSt.setString(2,patient.getName());
        preSt.setInt(3,patient.getAge());
        preSt.setString(4, patient.getEmail());

        int rs=preSt.executeUpdate();

        if(rs>0)
            System.out.println("Data Update Successfully");
        else
            System.out.println("Insertion Failed");

    }

    // -------- //Add Patient to DB --------

}
