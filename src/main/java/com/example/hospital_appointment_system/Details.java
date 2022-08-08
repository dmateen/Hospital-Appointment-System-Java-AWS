package com.example.hospital_appointment_system;

public class Details {
    String patient_name;
    char doc_code;
    int age;
    String email;
    String reason;

    public String getPatient_name() {
        return patient_name;
    }

    public char getDoc_code() {
        return doc_code;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getReason() {
        return reason;
    }

    //----------- testing setters --------

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public void setDoc_code(char doc_code) {
        this.doc_code = doc_code;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
