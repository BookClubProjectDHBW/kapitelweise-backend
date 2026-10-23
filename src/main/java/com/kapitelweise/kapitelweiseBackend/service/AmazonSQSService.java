package com.kapitelweise.kapitelweiseBackend.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.dao.MemberDao;
import com.kapitelweise.kapitelweiseBackend.domain.MemberEvent;
import com.kapitelweise.kapitelweiseBackend.domain.SuggestionEvent;
import com.kapitelweise.kapitelweiseBackend.domain.SuggestionResultEvent;
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
    private final BookclubDao bookclubDao;
    private final JsonMapper jsonMapper;
    private static final String MEMBERS_QUEUE_URL = "https://sqs.eu-north-1.amazonaws.com/608531435564/clubMembers.fifo";
    private static final String SUGGESTION_CHECK_URL = "https://sqs.eu-north-1.amazonaws.com/608531435564/suggestionCheck.fifo";
    private static final String SUGGESTION_RESULT_URL = "https://sqs.eu-north-1.amazonaws.com/608531435564/suggestionResult.fifo";

    public AmazonSQSService(AmazonSQS sqs, MemberDao memberDao, BookclubDao bookclubDao, JsonMapper jsonMapper) {
        this.sqs = sqs;
        this.memberDao = memberDao;
        this.bookclubDao = bookclubDao;
        this.jsonMapper = jsonMapper;
    }

    public void sendSuggestionCheck(String type, Long suggestionId, Long bookClubId, String isbn){

        try {
            String body = jsonMapper.writeValueAsString(new SuggestionEvent(type, suggestionId, bookClubId, isbn));
            SendMessageRequest send = new SendMessageRequest()
                    .withQueueUrl(SUGGESTION_CHECK_URL)
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
                .withQueueUrl(MEMBERS_QUEUE_URL)
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
                sqs.deleteMessage(MEMBERS_QUEUE_URL, message.getReceiptHandle());
            } catch (Exception e) {
                log.error("SQS-Nachricht konnte nicht verarbeitet werden: " + e.getMessage());
            }
        }
    }


    @Scheduled(fixedDelay = 1000)
    public void pollSuggestionResult(){
        ReceiveMessageRequest request = new ReceiveMessageRequest()
                .withQueueUrl(SUGGESTION_RESULT_URL)
                .withWaitTimeSeconds(10)
                .withMaxNumberOfMessages(5);

        List<Message> messages = sqs.receiveMessage(request).getMessages();
        for (Message message : messages) {
            log.info("SQS-Nachricht von SuggestionResultQueue empfangen: " + message.getBody());
            try {
                SuggestionResultEvent event = jsonMapper.readValue(message.getBody(), SuggestionResultEvent.class);

                if (event.type().equals("SUGGESTION_RESULT")) {
                    bookclubDao.updateSuggestion(event.suggestionId(), event.status(), event.title(), event.author(), event.pages(), event.firstPublishYear());
                }
                sqs.deleteMessage(SUGGESTION_RESULT_URL, message.getReceiptHandle());
            } catch (Exception e) {
                log.error("SQS-Nachricht konnte nicht verarbeitet werden: " + e.getMessage());
            }
        }
    }
}
