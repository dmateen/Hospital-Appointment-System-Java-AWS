package com.example.hospital_appointment_system.Servlets;

import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class loginTest {

    @Test
    void doPost() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("doctorCode")).thenReturn("A");
        when(request.getParameter("loginPassword")).thenReturn("abc1");
        StringWriter sw = new StringWriter();

        when(response.getWriter()).thenReturn(new PrintWriter(sw));

        login Login=new login();
        Login.doPost(request,response);
    }

    @Test
    void withCorrectPassword() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("doctorCode")).thenReturn("A");
        when(request.getParameter("loginPassword")).thenReturn("abc");
        StringWriter sw = new StringWriter();


        RequestDispatcher dispatcher =mock(RequestDispatcher.class);
                //(RequestDispatcher) mock(request.getRequestDispatcher("/mock/doctorList" ));
        when(response.getWriter()).thenReturn(new PrintWriter(sw));
        when(request.getRequestDispatcher("/doctorList")).thenReturn(dispatcher);

        login Login=new login();
        Login.doPost(request,response);


    }
}