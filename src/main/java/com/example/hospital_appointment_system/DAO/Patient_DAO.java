package com.example.hospital_appointment_system.DAO;

import com.amazonaws.services.sqs.model.Message;
import com.example.hospital_appointment_system.Appointment.appointment;
import com.example.hospital_appointment_system.Patient.Patient;
import com.example.hospital_appointment_system.Queue.sqsQueue;
import com.google.gson.Gson;

import java.sql.*;
import java.util.List;

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

        PreparedStatement preSt = con.prepareStatement("INSERT INTO patient values(?,?,?,?) ");

        preSt.setString(1, patient.getId());
        preSt.setString(2, patient.getName());
        preSt.setInt(3, patient.getAge());
        preSt.setString(4, patient.getEmail());

        int rs = preSt.executeUpdate();

        if (rs > 0)
            System.out.println("Data Update Successfully");
        else
            System.out.println("Insertion Failed");

    }

    // -------- //Add Patient to DB --------

    // -------- Delete Patient DAO Function --------
    public int deletePatient(String id) throws Exception {
        // -------- Query for Deleting Data --------
        String query = "DELETE FROM patient WHERE id='" + id + "'";
        // -------- Query for Deleting Data --------

        // -------- Query for Patient Data --------
        int rs = st.executeUpdate(query);

        return rs;

    }
    // -------- Delete Appointment DAO Function --------

    public Patient getPatientInfo(String id) throws SQLException {
        // -------- Query for Getting Patient Info --------
        String query = "SELECT * FROM patient WHERE id=\'" + id + "\'";
        // -------- Query for Getting Patient Info --------

        // -------- Query for Patient Data --------
        ResultSet rs = st.executeQuery(query);
        rs.next();
        Patient patient = new Patient();
        patient.setId(rs.getString(1));
        patient.setName(rs.getString(2));
        patient.setAge(rs.getInt(3));
        patient.setEmail(rs.getString(4));

        return patient;

    }

    public boolean validatePatientId(String patientID) throws SQLException {
        PreparedStatement preSt = con.prepareStatement("select id from patient where id=?;");
        preSt.setString(1, patientID);

        ResultSet rs = preSt.executeQuery();

        String id = null;
        boolean flag = false;
        while (rs.next()) {
            id = rs.getString(1);
            if (id.equals(patientID))
                flag = true;
        }
        return flag;
    }

    public int getWaitingTime(String patient_id) throws Exception {
        String doc_code = new Appointment_DAO().getDocCode(patient_id);

        List<Message> messages = new sqsQueue().readAllMessage("doctor" + doc_code);

        int count = 0;
        boolean flag = false;

        for (Message m : messages) {
            String json = m.getBody();
            appointment app = new Gson().fromJson(json, appointment.class);
            System.out.println("Comparing: "+patient_id+" and "+app.getPatient_id());
            if (patient_id.equals(app.getPatient_id())) {
                flag = true;
                break;
            }
            count++;
        }

        if(!flag)
            return 0;
        else {

            if(!new Doctor_DAO().getDoctorStatus(doc_code).equals("FREE"))
                return count*15+15;
            if(count==0)
                return 1;
            return count*15;
        }

    }


}


