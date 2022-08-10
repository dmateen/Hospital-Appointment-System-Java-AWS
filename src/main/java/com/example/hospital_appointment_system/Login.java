package com.example.hospital_appointment_system;

import com.amazonaws.services.sqs.model.Message;
import com.example.hospital_appointment_system.Patient.Patient;
import com.example.hospital_appointment_system.Queue.sqsQueue;
import com.google.gson.Gson;

import java.io.*;
import java.util.List;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "login", value = "/login")
public class login extends HttpServlet {
    private String message;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        String docCode=request.getParameter("doctorCode");
        String password= request.getParameter("loginPassword");

        sqsQueue SQS=new sqsQueue();
        List<Message> messages=SQS.readAllMessage("doctor"+docCode);
        for (Message m : messages) {
            Patient patient=new Patient();
            patient=new Gson().fromJson(m.getBody(),Patient.class);
            out.println(patient.getId());
            out.println("<br>");


        }
//        response.setContentType("text/html");

        // Hello


//        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

}