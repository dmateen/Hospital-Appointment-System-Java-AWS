package com.example.hospital_appointment_system.Patient;

public class Patient {
    String id;
    String name;
    int age;
    String email;
    char docCode;
    int waitingTime;
    int token_number;
    String checkupTime;

    public int getToken_number() {
        return token_number;
    }

    public void setToken_number(int token_number) {
        this.token_number = token_number;
    }

    public String getCheckupTime() {
        return checkupTime;
    }

    public void setCheckupTime(String checkupTime) {
        this.checkupTime = checkupTime;
    }

    public char getDocCode() {
        return docCode;
    }

    public void setDocCode(char docCode) {
        this.docCode = docCode;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
