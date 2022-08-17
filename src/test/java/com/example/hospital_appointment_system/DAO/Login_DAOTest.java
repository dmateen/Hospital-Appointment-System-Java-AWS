package com.example.hospital_appointment_system.DAO;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class Login_DAOTest {

    @Test
    void TestgetPassword() throws Exception {
        Login_DAO login_dao=new Login_DAO();
        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();
        assertEquals(pbkdf2PasswordEncoder.matches("abc",login_dao.getPassword("A")),true);
    }

    @Test
    void TestingHashPasswordMatch() throws Exception {
        Login_DAO login_dao=new Login_DAO();
        assertNotNull(login_dao.hashPasswordMatch("abc"));
    }

    @Test
    void TestMatchPassword() throws Exception {
        Login_DAO login_dao=new Login_DAO();
        assertEquals(login_dao.matchPassword("A","abc"),true);

    }
}