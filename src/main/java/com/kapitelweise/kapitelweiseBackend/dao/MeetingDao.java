package com.kapitelweise.kapitelweiseBackend.dao;

import com.kapitelweise.kapitelweiseBackend.request.MeetingRequest;
import com.kapitelweise.kapitelweiseBackend.response.MeetingResponse;

public interface MeetingDao {

    MeetingResponse saveMeeting(Long clubId, MeetingRequest request);
    MeetingResponse getMeetingForMember(Long clubId);
}
