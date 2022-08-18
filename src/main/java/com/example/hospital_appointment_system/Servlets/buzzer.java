package com.example.hospital_appointment_system.Servlets;

import com.example.hospital_appointment_system.Appointment.appointment;
import com.example.hospital_appointment_system.DAO.Appointment_DAO;
import com.example.hospital_appointment_system.DAO.Doctor_DAO;
import com.example.hospital_appointment_system.DAO.Patient_DAO;
import com.example.hospital_appointment_system.Mail.SendMail;
import com.example.hospital_appointment_system.Patient.Patient;
import com.example.hospital_appointment_system.Queue.sqsQueue;
import com.google.gson.Gson;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "buzzer", value = "/buzzer")
public class buzzer extends HttpServlet {
    private String message;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {
            PrintWriter out = response.getWriter();
            String docCode = String.valueOf(request.getParameter("docCode"));


            Doctor_DAO doctor_dao = new Doctor_DAO();
            String status = doctor_dao.getDoctorStatus(docCode);

            sqsQueue SQS = new sqsQueue();

            if (status.equals("FREE")) {
                if (SQS.getQueueSize("doctor" + docCode) != 0) {
                    String readReceipt = SQS.getReceiptHandle("doctor" + docCode);
                    //Code For sending Email
                    appointment appointment=new Gson().fromJson(SQS.peekQueue("doctor"+docCode), appointment.class);
                    SendMail.sendEmail(new Patient_DAO().getPatientInfo(appointment.getPatient_id()).getEmail());
                    //End of Code for Sending Email
                    SQS.readMessage("doctor" + docCode);
                    doctor_dao.setDoctorStatus(readReceipt, docCode);
                }
            } else {
                SQS.deleteMessage("doctor" + docCode, status);
                if (SQS.getQueueSize("doctor" + docCode) != 0) {
                    String readReceipt = SQS.getReceiptHandle("doctor" + docCode);
                    //Code For sending Email
                    appointment appointment=new Gson().fromJson(SQS.peekQueue("doctor"+docCode), appointment.class);
                    SendMail.sendEmail(new Patient_DAO().getPatientInfo(appointment.getPatient_id()).getEmail());
                    //End of Code for Sending Email
                    SQS.readMessage("doctor" + docCode);
                    doctor_dao.setDoctorStatus(readReceipt, docCode);
                } else {
                    doctor_dao.setDoctorStatus("FREE", docCode);
                }
            }

            //-- Forwarding Request --

            out.println("<html><body>");
            out.println("<a href=\"/doctorList\"> Back to login </a></body></html>");
            /**-- Forwarding the request to another page  --**/
            request.setAttribute("docCode", docCode);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/doctorList");
            dispatcher.forward(request, response);
            /**-- // Forwarding the request to another page  --**/
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}