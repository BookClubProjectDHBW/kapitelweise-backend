package com.kapitelweise.kapitelweiseBackend.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.kapitelweise.kapitelweiseBackend.dao.MemberDao;
import com.kapitelweise.kapitelweiseBackend.domain.MemberEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

@Service
public class AmazonSQSService {

    private static final Logger log = LoggerFactory.getLogger(AmazonSQSService.class);


    private final AmazonSQS sqs;
    private final MemberDao memberDao;
    private final JsonMapper jsonMapper;
    private static final String QUEUE_URL = "https://sqs.eu-north-1.amazonaws.com/608531435564/clubMembers.fifo";

    public AmazonSQSService(AmazonSQS sqs, MemberDao memberDao, JsonMapper jsonMapper) {
        this.sqs = sqs;
        this.memberDao = memberDao;
        this.jsonMapper = jsonMapper;
    }

    @Scheduled(fixedDelay = 1000)
    public void pollNewMember(){
        ReceiveMessageRequest request = new ReceiveMessageRequest()
                .withQueueUrl(QUEUE_URL)
                .withWaitTimeSeconds(10)
                .withMaxNumberOfMessages(5);

        List<Message> messages = sqs.receiveMessage(request).getMessages();
        for (Message message : messages) {
            log.info("SQS-Nachricht empfangen: " + message.getBody());
            try {
                MemberEvent event = jsonMapper.readValue(message.getBody(), MemberEvent.class);

                if (event.type().equals("MEMBER_JOINED")) {
                    memberDao.upsertMember(event.userId(), event.username(), event.bookclubId(), event.role());
                }
                sqs.deleteMessage(QUEUE_URL, message.getReceiptHandle());
            } catch (Exception e) {
                log.error("SQS-Nachricht konnte nicht verarbeitet werden: " + e.getMessage());
            }
        }
    }
}
