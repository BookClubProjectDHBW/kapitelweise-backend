package com.kapitelweise.kapitelweiseBackend.dao;

import com.kapitelweise.kapitelweiseBackend.request.MeetingRequest;
import com.kapitelweise.kapitelweiseBackend.response.MeetingResponse;
import com.kapitelweise.kapitelweiseBackend.response.UserMeetingResponse;

import java.util.List;

public interface MeetingDao {

    MeetingResponse saveMeeting(Long clubId, MeetingRequest request);
    MeetingResponse getMeetingForMember(Long clubId);
    UserMeetingResponse saveAcceptMeeting(Long clubId, Long meetingId, String username, String responseStatus);
    List<UserMeetingResponse> getUserMeetingResponse(Long clubId);
}
