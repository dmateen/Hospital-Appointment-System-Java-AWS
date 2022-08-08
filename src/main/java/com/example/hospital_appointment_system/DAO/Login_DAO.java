package com.example.hospital_appointment_system.DAO;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

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

    public void hashPasswordMatch(String password)
    {
        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();
        String pbkdf2CryptedPassword = pbkdf2PasswordEncoder.encode(password);

        System.out.println(pbkdf2CryptedPassword);


    }
}
