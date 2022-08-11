package com.example.hospital_appointment_system.Servlets;

import com.example.hospital_appointment_system.Appointment.appointment;
import com.example.hospital_appointment_system.DAO.Appointment_DAO;
import com.example.hospital_appointment_system.DAO.Patient_DAO;
import com.example.hospital_appointment_system.Queue.sqsQueue;
import com.google.gson.Gson;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "buzzer", value = "/buzzer")
public class buzzer extends HttpServlet {
    private String message;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String docCode = String.valueOf(request.getParameter("docCode"));
        System.out.println(docCode);
        sqsQueue SQS = new sqsQueue();
        appointment app = new Gson().fromJson(SQS.readMessage("doctor" + docCode), appointment.class);

        if (app != null) {
            try {
                Appointment_DAO appointment_dao = new Appointment_DAO();

                appointment_dao.deleteAppointment(app.getAppointment_id());

                Patient_DAO patient_dao = new Patient_DAO();
                patient_dao.deletePatient(app.getPatient_id());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        SQS.deleteMessage("doctor" + docCode, SQS.getReceiptHandle("doctor" + docCode));
        PrintWriter out = response.getWriter();
        out.println("<html><body>Im here after deleting the Message");

        out.println("<a href=\"/doctorList\"> Back to login </a></body></html>");
        /**-- Forwarding the request to another page  --**/
        request.setAttribute("docCode", docCode);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/doctorList");
        dispatcher.forward(request, response);
        /**-- // Forwarding the request to another page  --**/


    }


}