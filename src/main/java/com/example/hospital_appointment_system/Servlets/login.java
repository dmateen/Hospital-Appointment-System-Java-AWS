package com.example.hospital_appointment_system.Servlets;

import com.amazonaws.services.ec2.model.LocalGatewayVirtualInterface;
import com.amazonaws.services.sqs.model.Message;
import com.example.hospital_appointment_system.DAO.Login_DAO;
import com.example.hospital_appointment_system.Patient.Patient;
import com.example.hospital_appointment_system.Queue.sqsQueue;
import com.google.gson.Gson;

import java.io.*;
import java.util.List;
import javax.mail.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "login", value = "/login")
public class login extends HttpServlet {
    private String message;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        PrintWriter out = response.getWriter();

        String docCode = request.getParameter("doctorCode");
        String password = request.getParameter("loginPassword");

        //
        //Code to check if login and password is Correct
        Login_DAO login_dao = null;
        boolean passwordFlag = false;
        try {
            login_dao = new Login_DAO();
            passwordFlag = login_dao.matchPassword(docCode,password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


//        HttpSession mySession = request.getSession();
//        mySession.setAttribute("docCode",docCode);
//
//        response.sendRedirect("/testServlet");




        /**-- Forwarding the request to another page  --**/
        if (passwordFlag) {
            request.setAttribute("docCode", docCode);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/doctorList");
            dispatcher.include(request, response);
        } else {
            out.println("Wrong Password");
        }

        /**-- // Forwarding the request to another page  --**/


    }

}