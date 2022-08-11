package com.example.hospital_appointment_system.PatientServlet;

import com.amazonaws.services.sqs.model.Message;
import com.example.hospital_appointment_system.DAO.Appointment_DAO;
import com.example.hospital_appointment_system.DAO.Patient_DAO;
import com.example.hospital_appointment_system.Patient.Patient;
import com.example.hospital_appointment_system.Queue.sqsQueue;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "patientlogin", value = "/patientlogin")
public class patientlogin extends HttpServlet {
    private String message;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        PrintWriter out = response.getWriter();
        String patient_id = request.getParameter("patientID");

        //--
        //Code to Check if the patient id is valid
        //--

        out.println("<html><body>");
        String docCode;
        Appointment_DAO appointment_dao;

        try {
            appointment_dao = new Appointment_DAO();
            docCode = appointment_dao.getDocCode(patient_id);


            sqsQueue SQS = new sqsQueue();
            List<Message> messages = SQS.readAllMessage("doctor" + docCode);


            if(appointment_dao.getAppointmentStatus(patient_id).equals("COMPLETE")){
                Patient_DAO patient_dao=new Patient_DAO();
                Patient patient=patient_dao.getPatientInfo(patient_id);
                out.println("Id: "+patient.getId()+"</br>");
                out.println("Name: "+patient.getName()+"</br>");
                out.println("Age: "+patient.getAge()+"</br>");
                out.println("Email: "+patient.getEmail()+"</br>");
                out.println("Estimated Time Remaining: None </br>");
                out.println("Appointment Status: Checked </br>");
            }
            //
            //Code to check if login and password is Correct
            //


            /**-- // Forwarding the request to another page  --**/

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
