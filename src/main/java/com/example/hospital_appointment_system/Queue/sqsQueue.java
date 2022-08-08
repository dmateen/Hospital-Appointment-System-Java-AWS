package com.example.hospital_appointment_system.Queue;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;

import java.util.List;

public class sqsQueue {
    static AmazonSQS sqs = AmazonSQSClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-east-2")).build();
    //static String queueUrl="http://localhost:4566/000000000000/doctorA";

    public String getQueueUrl(String queueName) {
        /* Getting Queue Url to send message */
        String queueUrl = String.valueOf(sqs.getQueueUrl(queueName));
        System.out.println(queueUrl.substring(11, queueUrl.length() - 1));
        queueUrl = queueUrl.substring(11, queueUrl.length() - 1);

        return queueUrl;
    }

    public void sendMessage(String queueName, String patient) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.withQueueUrl(getQueueUrl(queueName)).withMessageBody(patient);

        sqs.sendMessage(sendMessageRequest);

    }

    public String readMessage(String queueName) {

        //Creating Receive Message Request
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest().withQueueUrl(getQueueUrl(queueName)).withMaxNumberOfMessages(1).withVisibilityTimeout(10);
        //Getting a list of messages
        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
        //Printing the size of Queue
        System.out.println(messages.size());

        if(messages.size()!=0) {
            //Printing Message
            System.out.println(messages.get(0).getBody());
            System.out.println(messages.get(0).toString());
            return messages.get(0).getReceiptHandle();
        }
        else
        {
            System.out.println("Error; Cant read the top most message as the queue is empty");
        }

        return null;

    }

    public int getQueueSize(String queueName)
    {
        //Creating Receive Message Request
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest().withQueueUrl(getQueueUrl(queueName)).withMaxNumberOfMessages(30).withVisibilityTimeout(1);
        //Getting a list of messages
        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
        //Printing the size of Queue
        return messages.size();
    }
    public void readAllMessage(String queueName) {

        //Creating Receive Message Request
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest().withQueueUrl(getQueueUrl(queueName)).withMaxNumberOfMessages(30).withVisibilityTimeout(1);
        //Getting a list of messages
        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
        //Printing the size of Queue
        System.out.println(messages.size());

        //Printing Messages
        for (Message m : messages) {
            System.out.println(m.getBody());
            System.out.println(m.toString());
        }

        System.out.println(messages.size());

    }

    public void deleteMessage(String queueName,String receiptHandle) {

        if(receiptHandle==null)
            System.out.println("Error; Cant delete message as the queue is empty");
        else {
            System.out.println("Deleting Message!");
            sqs.deleteMessage(getQueueUrl(queueName), receiptHandle);
            System.out.println(receiptHandle);
            System.out.println("Message Deleted Successfully!");
        }

    }
    public void deleteAllMessages(String queueName) {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest().withQueueUrl(getQueueUrl(queueName)).withMaxNumberOfMessages(1);
        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
        //Deleting Messages
        if(messages.size()!=0) {
            for (Message m : messages) {
                System.out.println("Deleting Message!");
                sqs.deleteMessage(getQueueUrl(queueName), m.getReceiptHandle());
                System.out.println(m.getReceiptHandle());
                System.out.println("Message Deleted Successfully!");
            }
        }
        else
        {
            System.out.println("Error; Cannot delete all messages as the queue is empty");

        }
    }


}
