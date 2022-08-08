package com.example.hospital_appointment_system.Appointment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class appointment {
    String appointment_id;
    String patient_id;
    char doc_code;
    int waiting_time;
    String checkup_time;

    int token_number;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println("Today Date & Time at Now :"+dateFormat.format(date));

        this.date=String.valueOf(dateFormat.format(date));

    }

    public int getToken_number() {
        return token_number;
    }

    public void setToken_number(int token_number) {
        this.token_number = token_number;
    }

    public String getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(String appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public char getDoc_code() {
        return doc_code;
    }

    public void setDoc_code(char doc_code) {
        this.doc_code = doc_code;
    }

    public int getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(int waiting_time) {
        this.waiting_time = waiting_time;
    }

    public String getCheckup_time() {
        return checkup_time;
    }

    public void setCheckup_time(String checkup_time) {
        this.checkup_time = checkup_time;
    }
}
