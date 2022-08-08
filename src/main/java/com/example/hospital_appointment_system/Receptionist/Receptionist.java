package com.example.hospital_appointment_system.Receptionist;

import com.example.hospital_appointment_system.Appointment.appointment;
import com.example.hospital_appointment_system.DAO.Appointment_DAO;
import com.example.hospital_appointment_system.DAO.Patient_DAO;
import com.example.hospital_appointment_system.Details;
import com.example.hospital_appointment_system.Patient.Patient;
import com.example.hospital_appointment_system.Queue.sqsQueue;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.UUID;

public class Receptionist {
    Details patientdetails;
    int avgWaitingtime=15;

    public void newPatient(Details details) throws Exception{
        Patient patient=new Patient();

        patient.setId(generate_id());
        patient.setName(details.getPatient_name());
        patient.setAge(details.getAge());
        patient.setEmail(details.getEmail());

        System.out.println(patient.getId()+patient.getName()+patient.getAge()+patient.getEmail()+details.getDoc_code());


        /**Appointment**/
        appointment new_appointment=new appointment();

        new_appointment.setAppointment_id(generate_appointmentId());
        new_appointment.setPatient_id(patient.getId());
        new_appointment.setDoc_code(details.getDoc_code());
        new_appointment.setWaiting_time(generate_waitingTime("doctor"+String.valueOf(details.getDoc_code())));
        new_appointment.setCheckup_time(generate_checkuptime());
        new_appointment.setToken_number(generate_token());
        new_appointment.setDate();


        addPatienttoDB(patient);
        addAppointmentToDB(new_appointment);
        addPatienttoQueue(patient);

        //Now add patient to queue
    }

    private void addAppointmentToDB(appointment new_appointment) throws Exception {
        Appointment_DAO appointment_dao  =new Appointment_DAO();
        appointment_dao.addAppointment(new_appointment);
    }

    private String generate_appointmentId() {
        return "APP-"+ UUID.randomUUID().toString().substring(0,6);
    }

    private void addPatienttoDB(Patient patient) throws Exception {

        Patient_DAO patient_dao=new Patient_DAO();
        patient_dao.addPatient(patient);
    }

    public int generate_waitingTime(String queueName)
    {
        sqsQueue sqs=new sqsQueue();
        return avgWaitingtime*sqs.getQueueSize(queueName);
    }
    String generate_id()
    {
        return "PAT-"+ UUID.randomUUID().toString().substring(0,6);
    }

    int generate_token() throws Exception {
        Appointment_DAO appointment_dao=new Appointment_DAO();
        return appointment_dao.returnToken();
    }

    String generate_checkuptime()
    {
        //Goto DB to get the checkup time of the last person and then add the average waiting time to it
        return "Test Checkup Time";
    }

    public void addPatienttoQueue(Patient patient)
    {
        sqsQueue sqs=new sqsQueue();
        String patientJSON =new Gson().toJson(patient);
        System.out.println(patientJSON);
        sqs.sendMessage("doctorA",patientJSON);
    }

    public void addAppointmentToQueue(appointment app)
    {
        sqsQueue sqs=new sqsQueue();
        String appJSON =new Gson().toJson(app);
        System.out.println(appJSON);
        if(app.getDoc_code()=='A')
            sqs.sendMessage("doctorA",appJSON);
        else if(app.getDoc_code()=='B')
            sqs.sendMessage("doctorB",appJSON);
        else
            sqs.sendMessage("doctorC",appJSON);
    }


}
