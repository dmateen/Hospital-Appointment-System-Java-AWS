package com.example.hospital_appointment_system.DAO;

import java.sql.*;

public class Doctor_DAO {
    // -------- Declaring JDBC Vars --------
    String url;
    Connection con;
    Statement st;
    // -------- //Declaring JDBC Vars --------

    // -------- Default Constructor --------
    public Doctor_DAO() throws Exception {

        // -------- JDBC Connection --------
        Class.forName("com.mysql.jdbc.Driver");
        url = "jdbc:mysql://127.0.0.1/hospital";
        con = DriverManager.getConnection(url, "root", "root");
        st = con.createStatement();
        // -------- //JDBC Connection --------

    }
    // -------- //Default Constructor --------

    public String getDoctorStatus(String docCode) throws SQLException {
        PreparedStatement preSt=con.prepareStatement("Select status from doctor where code =?");
        preSt.setString(1, docCode);

        ResultSet rs=preSt.executeQuery();

        String status = null;
        while(rs.next())
            status=rs.getString(1);

        System.out.println(status);
        return status;
    }

    public void setDoctorStatus(String readReceipt,String docCode) throws SQLException {
        PreparedStatement preSt=con.prepareStatement("Update doctor set status=? where code =?");

        preSt.setString(1, readReceipt);
        preSt.setString(2, docCode);

        preSt.executeUpdate();


    }
}
