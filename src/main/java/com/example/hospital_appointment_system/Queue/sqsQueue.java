package com.example.hospital_appointment_system.Queue;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;
import com.example.hospital_appointment_system.Patient.Patient;

import java.util.List;

public class sqsQueue {
    static AmazonSQS sqs= AmazonSQSClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566","us-east-2")).build();
    //static String queueUrl="http://localhost:4566/000000000000/doctorA";

    String getQueueUrl(String queueName)
    {
        /* Getting Queue Url to send message */
        String queueUrl= String.valueOf(sqs.getQueueUrl(queueName));
        System.out.println(queueUrl.substring(11,queueUrl.length()-1));
        queueUrl=queueUrl.substring(11,queueUrl.length()-1);

        return queueUrl;
    }
    public void sendMessage(String queueName, String patient)
    {
        SendMessageRequest sendMessageRequest=new SendMessageRequest();
        sendMessageRequest.withQueueUrl(getQueueUrl(queueName)).withMessageBody(patient);

        sqs.sendMessage(sendMessageRequest);

    }

    public void readMessage(String queueName) throws InterruptedException {

        //Creating Receive Message Request
        ReceiveMessageRequest receiveMessageRequest=new ReceiveMessageRequest().withQueueUrl(getQueueUrl(queueName)).withMaxNumberOfMessages(24);
        //Getting a list of messages
        List<Message> messages= sqs.receiveMessage(receiveMessageRequest).getMessages();
        //Printing the size of Queue
        System.out.println(messages.size());

        //Printing Messages
        for(Message m:messages)
        {
            System.out.println(m.getBody());
            System.out.println(m.toString());
        }

        //Deleting Messages
        for (Message m : messages){
            System.out.println("Deleting Message!");
            sqs.deleteMessage(getQueueUrl(queueName),m.getReceiptHandle());
            System.out.println(m.getReceiptHandle());
            System.out.println("Message Deleted Successfully!");
        }
        System.out.println(messages.size());

        }



}
