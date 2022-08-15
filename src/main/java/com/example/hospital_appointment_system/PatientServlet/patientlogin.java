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

        String patient_id = request.getParameter("patientID");
        PrintWriter out = response.getWriter();


        try {
            Patient_DAO patient_dao = new Patient_DAO();

            if (!patient_dao.validatePatientId(patient_id)) {
                out.println("Not a Valid Patient ID");
            } else {
                request.setAttribute("PatientID",patient_id);
                RequestDispatcher requestDispatcher=request.getRequestDispatcher("/ShowPatientDetails");
                requestDispatcher.forward(request,response);
            }


            /**-- // Forwarding the request to another page  --**/

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
