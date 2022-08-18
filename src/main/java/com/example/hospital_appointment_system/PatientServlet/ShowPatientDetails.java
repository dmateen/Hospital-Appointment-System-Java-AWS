package com.example.hospital_appointment_system.PatientServlet;

import com.amazonaws.services.sqs.model.Message;
import com.example.hospital_appointment_system.DAO.Appointment_DAO;
import com.example.hospital_appointment_system.DAO.Patient_DAO;
import com.example.hospital_appointment_system.Patient.Patient;
import com.example.hospital_appointment_system.Queue.sqsQueue;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "ShowPatientDetails", value = "/ShowPatientDetails")
public class ShowPatientDetails extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        PrintWriter out = response.getWriter();
        String patient_id = request.getParameter("patientID");

        String docCode;
        Appointment_DAO appointment_dao;

        try {
            Patient_DAO patient_dao = new Patient_DAO();
            Patient patient=patient_dao.getPatientInfo(patient_id);
            int waiting_time=patient_dao.getWaitingTime(patient_id);
            out.println("<html><head><link rel=\"stylesheet\" href=\"css/doctor_login.css\"></head><body>");
            out.println("ID: "+patient.getId()+"<br>");
            out.println("Name: "+patient.getName()+"<br>");
            out.println("Age: "+patient.getAge()+"<br>");
            out.println("Email: "+patient.getEmail()+"<br>");
            out.println("Estimated Waiting Time: "+waiting_time+ " mins<br>");

            if(waiting_time!=0)
                out.println("Estimated Checkup Time: "+ LocalDateTime.now().plusMinutes(waiting_time).format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))+" <br>");

            out.println("<form action=\"index.html\">");
            out.println("<input type=\"submit\" name=\"reloadButton\" id=\"\" value=\"Main Menu\">");
            out.println("</form>");

            out.println("</body></html>");


            /**-- // Forwarding the request to another page  --**/

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }




}
