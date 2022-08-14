package com.example.hospital_appointment_system.Servlets;

import com.amazonaws.services.sqs.model.Message;
import com.example.hospital_appointment_system.Appointment.appointment;
import com.example.hospital_appointment_system.DAO.Doctor_DAO;
import com.example.hospital_appointment_system.Patient.Patient;
import com.example.hospital_appointment_system.Queue.sqsQueue;
import com.google.gson.Gson;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "doctorList", value = "/doctorList")
public class doctorList extends HttpServlet {
    private String message;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        /**-- Getting Data stored in the Request --**/
        String docCode= String.valueOf(request.getAttribute("docCode"));
        /**-- End of Getting Data stored in the Request --**/

        PrintWriter out=response.getWriter();
        out.println("<html>");
        out.println("<body>");


        String status;
        try {
             status=new Doctor_DAO().getDoctorStatus(docCode);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if(!status.equals("FREE"))
            out.println("Doctor Status: BUSY </br>");
        else
            out.println("<p>Doctor Status: "+status+"</p>" );

        sqsQueue SQS=new sqsQueue();
        List<Message> messages=SQS.readAllMessage("doctor"+docCode);

        for (Message m : messages) {
            appointment app=new appointment();
            app=new Gson().fromJson(m.getBody(),appointment.class);
            out.println(app.getPatient_id());
            out.println("<br>");
        }

        /*-- Buzzer ---*/

        out.println("<form action=\"buzzer\" name=\"buzzer\" method=\"Post\">");
        out.println(" <input type=\"hidden\"  name=\"docCode\" value=\""+docCode+"\">");
        out.println("<input type=\"submit\" name=\"buzzerButton\" id=\"\" value=\"Next Patient\">");
        out.println("</form>");

        out.println("</body></html>");

//        response.sendRedirect("/login");


    }



}