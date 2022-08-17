package com.example.hospital_appointment_system.Queue;

import com.amazonaws.services.sqs.model.Message;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class sqsQueueTest {

    @Test
    void getQueueUrl() {
        assertEquals(new sqsQueue().getQueueUrl("test"),"http://localhost:4566/000000000000/test");
        new sqsQueue().deleteAllMessages("test");
    }

    @Test
    void CheckingNumberOfMessagesDelayed() throws InterruptedException {
        sqsQueue SQS=new sqsQueue();
        SQS.deleteAllMessages("test");
        SQS.sendMessage("test","Message");
        String receipt=SQS.getReceiptHandle("test");
        SQS.readMessage("test");

        assertEquals(SQS.getDelayedMessageCount("test"),1);
        SQS.deleteMessage("test",receipt);
    }
    @Test
    void testingQueueSize(){
        sqsQueue SQS=new sqsQueue();
        SQS.deleteAllMessages("test");
        SQS.sendMessage("test","Hello");
        SQS.sendMessage("test","Hello1");
        assertEquals(SQS.getQueueSize("test"),2);
        SQS.deleteAllMessages("test");
    }

    @Test
    void testingGetReadReceipt() throws InterruptedException {
        sqsQueue SQS=new sqsQueue();
        SQS.deleteAllMessages("test");
        SQS.sendMessage("test","Hello");
        String receiptHandle = SQS.getReceiptHandle("test");
        assertNotNull(receiptHandle);
        SQS.deleteAllMessages("test");
    }

    @Test
    void TestingDeleteMessage() throws InterruptedException {
        sqsQueue SQS=new sqsQueue();
        SQS.deleteAllMessages("test");
        SQS.sendMessage("test","Sample Message");
        SQS.sendMessage("test","Sample Message1");
        SQS.deleteMessage("test",SQS.getReceiptHandle("test"));

        assertEquals(SQS.getQueueSize("test"),1);
        SQS.deleteAllMessages("test");
    }



    @Test
    void PeekQueueWithMessages() {
        sqsQueue SQS=new sqsQueue();
        SQS.deleteAllMessages("test");

        SQS.sendMessage("test","Message");
        String message=SQS.peekQueue("test");
        assertNotNull(message);
        SQS.deleteAllMessages("test");
    }

    @Test
    void PeekQueueWithOutMessages() {
        sqsQueue SQS=new sqsQueue();
        SQS.deleteAllMessages("test");
        String message=SQS.peekQueue("test");
        assertNull(message);


    }

    @Test
    void TestingNullReceiptHandle() throws InterruptedException {
        sqsQueue SQS=new sqsQueue();
        SQS.deleteAllMessages("test");
        assertNull(SQS.getReceiptHandle("test"));

    }

    @Test
    void ReadingQueueWithNoMessage()  {
        sqsQueue SQS=new sqsQueue();
        SQS.deleteAllMessages("test");
        String message=SQS.readMessage("test");
        assertNull(message);

    }

    @Test
    void ReadingAllMessagesFromQueue() throws InterruptedException {
        sqsQueue SQS=new sqsQueue();
        SQS.deleteAllMessages("test");
        SQS.sendMessage("test","Message");
        SQS.sendMessage("test","Message");
        SQS.sendMessage("test","Message");
        List<Message> messageList=SQS.readAllMessage("test");
        assertEquals(messageList.size(),3);
        Thread.sleep(2000);
        SQS.deleteAllMessages("test");
    }

    @Test
    void SendingNullReceiptHandle() throws InterruptedException {
        sqsQueue SQS=new sqsQueue();
        SQS.deleteAllMessages("test");

        String receipt=SQS.getReceiptHandle("test");
        SQS.deleteMessage("test,",receipt);
        assertNull(receipt);
        SQS.deleteAllMessages("test");
    }
}