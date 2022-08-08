package com.example.resources;

import com.example.hospital_appointment_system.Details;
import com.example.hospital_appointment_system.Receptionist.Receptionist;
import com.google.gson.Gson;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/HAS_Resource")
public class HAS_Resource {

    /** --- Checking Connection ----- **/
    @GET
    @Path("/connectionCheck")
    public String connectionCheck() throws Exception{
        return "I am Connected";
    }

    @POST
    @Path("/entry")
    public Response entry(String payload) throws Exception{

        /**---- Transferring Data from JSON to Class -----**/
        System.out.println(payload);
        Details details=new Gson().fromJson(payload,Details.class);

        /**---- Sending Patient Details to the Receptionist -----**/
        Receptionist receptionist=new Receptionist();
        receptionist.newPatient(details);
        /**---- Checking if the data is Null or Misspelled  -----**/
        try{
            if(details.getPatient_name()==null || details.getReason()==null ||details.getEmail()==null||details.getAge()==0 || details.getDoc_code()==(char)0) {
                throw new Exception();
            }
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("One or Many Fields are empty or Misspelled").build();
        }
        System.out.println(details.getPatient_name() +"  " +details.getAge()+"  "+details.getEmail()+"  "+details.getReason()+"  "+details.getDoc_code());

        return Response.ok().build();
    }


}
