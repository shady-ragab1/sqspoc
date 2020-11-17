package com.shady.sqspoc.sqspoc;

import com.amazonaws.services.sns.model.SubscribeRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping(value = "/sqs")
@Slf4j
public class TestController {

    private static final String QUEUE = "MyQueue-standard";
    private static final String TOPIC = "MyTopic-standard";


    @PostConstruct
    public void subscribe(){
        final SubscribeRequest subscribeRequest = null;

    }

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    private NotificationMessagingTemplate notificationMessagingTemplate;

    @PostMapping(value = "/send")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void sendMessageToSqs(@RequestBody final Message message) {
        log.info("Sending the message to the Amazon sqs.");
        queueMessagingTemplate.convertAndSend(QUEUE, message);
        log.info("Message sent successfully to the Amazon sqs.");

        log.info("Sending the message to the Amazon sns.");
        notificationMessagingTemplate.sendNotification(TOPIC,message,"test");
        log.info("Message sent successfully to the Amazon sns.");
    }

    @SqsListener(value = QUEUE, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void getMessageFromSqs( Message message, @Header("MessageId") String messageId) {
        log.info("Received message= {} with messageId= {}", message, messageId);
    }

}
