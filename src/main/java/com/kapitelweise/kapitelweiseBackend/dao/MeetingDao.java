
package com.kapitelweise.kapitelweiseBackend.dao;

import com.kapitelweise.kapitelweiseBackend.request.MeetingRequest;
import com.kapitelweise.kapitelweiseBackend.response.MeetingResponse;

import java.util.List;

public interface MeetingDao {

    MeetingResponse saveMeeting(Long clubId, MeetingRequest request);

    List<MeetingResponse> getUpcomingMeetings(Long clubId);
}
