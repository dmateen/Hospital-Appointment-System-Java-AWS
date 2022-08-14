package com.example.hospital_appointment_system.DAO;

import com.example.hospital_appointment_system.Appointment.appointment;
import com.example.hospital_appointment_system.Patient.Patient;

import java.sql.*;

public class Appointment_DAO {
    // -------- Declaring JDBC Vars --------
    String url;
    Connection con;
    Statement st;
    // -------- //Declaring JDBC Vars --------

    // -------- Default Constructor --------
    public Appointment_DAO() throws Exception {

        // -------- JDBC Connection --------
        Class.forName("com.mysql.jdbc.Driver");
        url = "jdbc:mysql://127.0.0.1/hospital";
        con = DriverManager.getConnection(url, "root", "root");
        st = con.createStatement();
        // -------- //JDBC Connection --------

    }
    // -------- //Default Constructor --------


    // -------- Return Token Number from DB --------
    public int returnToken() throws Exception {
        PreparedStatement presT = con.prepareStatement("SELECT COUNT(appointment_id) FROM appointments WHERE appointment_date = CURDATE() ");

        ResultSet rs = presT.executeQuery();
        int count = 0;
        while (rs.next())
            count = rs.getInt(1);
        return count;
    }
    // -------- Return token Number from DB --------

    // -------- Add Appointment to DB --------

    public void addAppointment(appointment app) throws Exception {

        PreparedStatement preSt = con.prepareStatement("INSERT INTO appointments values(?,?,?,?,?,?) ");

        preSt.setString(1, app.getAppointment_id());
        preSt.setString(2, app.getPatient_id());
        preSt.setString(3, String.valueOf(app.getDoc_code()));
//        preSt.setInt(4,app.getWaiting_time());
//        preSt.setString(5, app.getCheckup_time());
        preSt.setInt(4, app.getToken_number());
        preSt.setString(5, app.getDate());
        preSt.setString(6, "PENDING");


        int rs = preSt.executeUpdate();

        if (rs > 0)
            System.out.println("Data Update Successfully");
        else
            System.out.println("Insertion Failed");

    }

    // -------- //Add Patient to DB --------


    // -------- Delete Appointment DAO Function --------
    public int deleteAppointment(String appointmentID) throws Exception {
        // -------- Query for Deleting Data --------
        String query = "DELETE FROM appointments WHERE appointment_id='" + appointmentID + "'";
        // -------- Query for Deleting Data --------

        // -------- Query for Deleting Data --------
        int rs = st.executeUpdate(query);

        return rs;

    }
    // -------- Delete Appointment DAO Function --------

    // -------- Change Appointment DAO Function --------
    public int changeAppointmentStatus(String status, String app_id) throws Exception {
        // -------- Query for Updating Data --------
        String query = "UPDATE appointments SET appointment_status=\'" + status + "\' WHERE appointment_id=\'" + app_id + "\'";
        // -------- Query for Updating Data --------

        // -------- Query for Updating Data --------
        int rs = st.executeUpdate(query);

        return rs;

    }
    // -------- Change Appointment DAO Function --------


    // -------- Get Doctor Code DAO Function --------
    public String getDocCode(String id) throws Exception {
        // -------- Query for Getting Doc Code --------
        String query = "SELECT doc_code FROM appointments WHERE patient_id='" + id + "'";
        // -------- Query for Getting Doc Code --------

        // -------- Query for Patient Data --------
        ResultSet rs = st.executeQuery(query);


        return rs.getString(1);
    }
    // -------- Get Doctor Code DAO Function --------


    public String getAppointmentStatus(String id) throws SQLException {
        // -------- Query for Getting Appointment Status --------
        String query = "SELECT appointment_status FROM appointments WHERE patient_id='" + id + "'";
        // -------- Query for Getting Appointment Status --------

        // -------- Query for Patient Data --------
        ResultSet rs = st.executeQuery(query);


        return rs.getString(1);
    }


    public void changeFromBusy() throws SQLException {

        String query = "Update appointments set appointment_status=\'COMPLETE\' where appointment_status=\'BUSY\'";
        st.executeUpdate(query);

    }
}