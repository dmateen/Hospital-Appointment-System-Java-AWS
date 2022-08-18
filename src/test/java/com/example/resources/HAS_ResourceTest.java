package com.example.resources;

import com.example.hospital_appointment_system.DAO.Appointment_DAO;
import com.example.hospital_appointment_system.DAO.Patient_DAO;
import com.example.hospital_appointment_system.Details;
import com.example.hospital_appointment_system.Queue.sqsQueue;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class HAS_ResourceTest {

    @Test
    void TestingEntryEndPointWithDocA() throws Exception {
        HAS_Resource has=new HAS_Resource();

        //Creating Details Object
        Details details=new Details();
        details.setReason("TEST DATA");
        details.setDoc_code('A');
        details.setPatient_name("TEST DATA");
        details.setEmail("TEST DATA");
        details.setAge(11);

        String json= new Gson().toJson(details);

        assertEquals(has.entry(json).getEntityTag(),Response.ok().build().getEntityTag());

//        assertNotNull(has.entry(json));
        String readRecipt=new sqsQueue().getReceiptHandle("doctorA");
        new sqsQueue().deleteMessage("doctorA",readRecipt);
        new Patient_DAO().deleteTestData();

    }

    @Test
    void TestingEntryEndPointWithDocB() throws Exception {
        HAS_Resource has=new HAS_Resource();

        Details details=new Details();
        details.setReason("TEST DATA");
        details.setDoc_code('B');
        details.setPatient_name("TEST DATA");
        details.setEmail("TEST DATA");
        details.setAge(11);

        String json= new Gson().toJson(details);

        assertEquals(has.entry(json).getEntityTag(),Response.ok().build().getEntityTag());

//        assertNotNull(has.entry(json));
        String readRecipt=new sqsQueue().getReceiptHandle("doctorB");
        new sqsQueue().deleteMessage("doctorB",readRecipt);
        new Patient_DAO().deleteTestData();

    }

    @Test
    void TestingEntryEndPointWithDocC() throws Exception {
        HAS_Resource has=new HAS_Resource();

        Details details=new Details();
        details.setReason("TEST DATA");
        details.setDoc_code('C');
        details.setPatient_name("TEST DATA");
        details.setEmail("TEST DATA");
        details.setAge(11);

        String json= new Gson().toJson(details);

        assertEquals(has.entry(json).getEntityTag(),Response.ok().build().getEntityTag());

//        assertNotNull(has.entry(json));
        String readRecipt=new sqsQueue().getReceiptHandle("doctorC");
        new sqsQueue().deleteMessage("doctorC",readRecipt);
        new Patient_DAO().deleteTestData();

    }

    @Test
    void TestingEntryEndPointWithException() throws Exception {
        HAS_Resource has=new HAS_Resource();

        Details details=new Details();
        details.setReason("TEST DATA");
        details.setDoc_code('C');
        details.setPatient_name("TEST DATA");
        details.setEmail("TEST DATA");
//        details.setAge(11);

        String json= new Gson().toJson(details);


        assertEquals(has.entry(json).getEntityTag(),Response.status(Response.Status.BAD_REQUEST).entity("One or Many Fields are empty or Misspelled").build().getEntityTag());

        new sqsQueue().deleteAllMessages("doctorC");
        new Patient_DAO().deleteTestData();

    }
}