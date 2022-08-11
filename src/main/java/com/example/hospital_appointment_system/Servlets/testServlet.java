package com.example.hospital_appointment_system.Servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "testServlet", value = "/testServlet")
public class testServlet extends HttpServlet {


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        PrintWriter out = response.getWriter();

        HttpSession session=request.getSession(false);
        String n=(String)session.getAttribute("docCode");
        out.print("Hello "+n);


    }
}
