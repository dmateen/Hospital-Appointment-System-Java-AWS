package com.example.hospital_appointment_system.Receptionist;

import com.example.hospital_appointment_system.Details;
import com.example.hospital_appointment_system.Patient.Patient;
import com.example.hospital_appointment_system.Queue.sqsQueue;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.UUID;

public class Receptionist {
    Details patientdetails;

    public void newPatient(Details details){
        Patient patient=new Patient();

        patient.setId(generate_id());
        patient.setName(details.getPatient_name());
        patient.setAge(details.getAge());
        patient.setEmail(details.getEmail());
        patient.setDocCode(details.getDoc_code());
        patient.setWaitingTime(generate_waitingTime());
        patient.setToken_number(generate_token());
        patient.setCheckupTime(generate_checkuptime());
        addtoQueue(patient);
        addPatienttoDB();

        //Now add patient to queue
    }

    private void addPatienttoDB() {
    }

    public int generate_waitingTime()
    {
//        int min = 5;
//        int max = 10;
//        int wait = (int)Math.floor(Math.random()*(max-min+1)+min);
//        return wait;

        return 5;
    }
    String generate_id()
    {
        return "PAT-"+ UUID.randomUUID().toString().substring(0,6);
    }

    int generate_token()
    {
        //Go to DB and get the last patient
        return 0;
    }

    String generate_checkuptime()
    {
        //Check the last member in the queue and add the waiting time of this member to the last one and then add that to the  current time
        return "test";
    }

    public void addtoQueue(Patient patient)
    {
        sqsQueue sqs=new sqsQueue();

        String patientJSON =new Gson().toJson(patient);
        sqs.sendMessage("doctorA",patientJSON);



    }


}
