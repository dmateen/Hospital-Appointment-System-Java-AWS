package com.example.hospital_appointment_system.DAO;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.sql.*;

public class Login_DAO {
    // -------- Declaring JDBC Vars --------
    String url;
    Connection con;
    Statement st;
    // -------- //Declaring JDBC Vars --------

    // -------- Default Constructor --------
    public Login_DAO() throws Exception {

        // -------- JDBC Connection --------
        Class.forName("com.mysql.jdbc.Driver");
        url = "jdbc:mysql://127.0.0.1/hospital";
        con = DriverManager.getConnection(url, "root", "root");
        st = con.createStatement();
        // -------- //JDBC Connection --------

    }
    // -------- //Default Constructor --------

    String getPassword(String docCode) throws SQLException {
        PreparedStatement preSt = con.prepareStatement("Select password from login where doc_code=? ");
        preSt.setString(1, docCode);

        ResultSet rs=preSt.executeQuery();
        String password ="abc";
        while(rs.next())
            password=rs.getString(1);

        return password;
    }
    public String hashPasswordMatch(String password)
    {
        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();
        String pbkdf2CryptedPassword = pbkdf2PasswordEncoder.encode(password);
        return pbkdf2CryptedPassword;
    }

    public boolean matchPassword(String docCode,String password) throws SQLException {
        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();
        return pbkdf2PasswordEncoder.matches(password,getPassword(docCode));

    }
}
