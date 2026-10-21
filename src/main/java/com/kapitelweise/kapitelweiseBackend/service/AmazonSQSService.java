package com.kapitelweise.kapitelweiseBackend.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.kapitelweise.kapitelweiseBackend.dao.MemberDao;
import com.kapitelweise.kapitelweiseBackend.domain.MemberEvent;
import com.kapitelweise.kapitelweiseBackend.domain.SuggestionEvent;
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
    private static final String MEMBERSQUEUE_URL = "https://sqs.eu-north-1.amazonaws.com/608531435564/clubMembers.fifo";
    private static final String SUGGESTIONCHECK_URL = "https://sqs.eu-north-1.amazonaws.com/608531435564/suggestionCheck.fifo";

    public AmazonSQSService(AmazonSQS sqs, MemberDao memberDao, JsonMapper jsonMapper) {
        this.sqs = sqs;
        this.memberDao = memberDao;
        this.jsonMapper = jsonMapper;
    }

    public void sendSuggestionCheck(String type, Long suggestionId, Long bookClubId, String isbn){

        try {
            String body = jsonMapper.writeValueAsString(new SuggestionEvent(type, suggestionId, bookClubId, isbn));
            SendMessageRequest send = new SendMessageRequest()
                    .withQueueUrl(SUGGESTIONCHECK_URL)
                    .withMessageBody(body)
                    .withMessageGroupId(String.valueOf(suggestionId))
                    .withMessageDeduplicationId(String.valueOf(suggestionId));
            sqs.sendMessage(send);
        } catch (Exception e) {
            log.error("SuggestionEvent konnte nicht gesendet werden: " + e.getMessage() + suggestionId);
        }
    }


    @Scheduled(fixedDelay = 1000)
    public void pollNewMember(){
        ReceiveMessageRequest request = new ReceiveMessageRequest()
                .withQueueUrl(MEMBERSQUEUE_URL)
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
                sqs.deleteMessage(MEMBERSQUEUE_URL, message.getReceiptHandle());
            } catch (Exception e) {
                log.error("SQS-Nachricht konnte nicht verarbeitet werden: " + e.getMessage());
            }
        }
    }
}
