package com.kapitelweise.kapitelweiseBackend.dao;

import com.kapitelweise.kapitelweiseBackend.request.MeetingRequest;
import com.kapitelweise.kapitelweiseBackend.response.MeetingResponse;
import com.kapitelweise.kapitelweiseBackend.response.UserMeetingResponse;

import java.util.List;

public interface MeetingDao {

    MeetingResponse saveMeeting(Long clubId, MeetingRequest request);
    MeetingResponse getMeetingForMember(Long clubId, String username);
    String saveAcceptMeeting(Long clubId, Long meetingId, String username, String responseStatus);
    boolean meetingExists(Long clubId, Long meetingId);
    boolean meetingHasStarted(Long clubId, Long meetingId);
    List<UserMeetingResponse> getUserMeetingResponse(Long clubId, Long meetingId);
}
